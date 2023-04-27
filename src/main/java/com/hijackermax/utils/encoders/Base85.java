package com.hijackermax.utils.encoders;

import com.hijackermax.utils.lang.StringUtils;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

/**
 * Binary-to-text encoding that is more space-efficient than base64
 *
 * @since 0.0.6
 */
public final class Base85 {
    private static final int CHAR_START_IDX = 0x21;
    private static final int[] POWERS_85 = {52200625, 614125, 7225, 85, 1};
    private static final byte[] IGNORED_CHARS = {0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x20};

    private Base85() {
    }

    /**
     * Encodes provided byte array to Base85 ASCII {@link String}
     *
     * @param source byte array
     * @return encoded ASCII {@link String}
     * @since 0.0.6
     */
    public static String encode(byte[] source) {
        int dataLength = Objects.requireNonNull(source).length;
        ByteArrayOutputStream result = new ByteArrayOutputStream(calculateEncodeBufferSize(dataLength));
        for (int idx = 0; idx < dataLength; idx += 4) {
            byte[] pagination = new byte[4];
            int partitionSize = Math.min(dataLength - idx, 4);
            System.arraycopy(source, idx, pagination, 0, partitionSize);
            result.writeBytes(encodePartition(pagination, partitionSize));
        }
        return result.toString(StandardCharsets.US_ASCII);
    }


    /**
     * Decodes provided ASCII {@link String} to byte array
     *
     * @param source ASCII {@link String}
     * @return decoded byte array
     * @throws IllegalArgumentException if provided string is not base85 encoded
     * @since 0.0.6
     */
    public static byte[] decode(String source) {
        if (StringUtils.isBlank(source)) {
            return new byte[0];
        }
        ByteArrayOutputStream result = new ByteArrayOutputStream(calculateDecodedBufferSize(source.length()));
        byte[] sourceBytes = source.getBytes(StandardCharsets.US_ASCII);
        byte[] pagination = new byte[5];
        int paginationIdx = 0;
        for (int idx = 0; idx < sourceBytes.length; idx++) {
            byte nextByte = sourceBytes[idx];
            if (0 <= Arrays.binarySearch(IGNORED_CHARS, nextByte)) {
                continue;
            }
            boolean isZeroPlaceholder = 0x7A == nextByte;
            boolean isInvalidInput = (isZeroPlaceholder && 0 != paginationIdx)
                    || (!isZeroPlaceholder && (nextByte < 0x21 || nextByte > 0x75));
            if (isInvalidInput) {
                throw new IllegalArgumentException("Provided source string is not Base85 encoded");
            }
            if (isZeroPlaceholder) {
                while (paginationIdx < 5) {
                    pagination[paginationIdx++] = CHAR_START_IDX;
                }
            } else {
                pagination[paginationIdx++] = nextByte;
            }
            boolean lastByte = idx == sourceBytes.length - 1;
            boolean isReadyToDecode = (pagination.length == paginationIdx) || (lastByte && 0 != paginationIdx);
            if (isReadyToDecode) {
                if (lastByte) {
                    Arrays.fill(pagination, paginationIdx, pagination.length, (byte) 0x75);
                }
                byte[] decodedPartition = decodePartition(pagination);
                int writeLength = decodedPartition.length - (pagination.length - paginationIdx);
                result.write(decodedPartition, 0, writeLength);
                paginationIdx = 0;
                Arrays.fill(pagination, (byte) 0x00);
            }
        }
        return result.toByteArray();
    }

    private static byte[] encodePartition(byte[] pagination, int actualBytes) {
        long unsignedInt = ((long) (pagination[0] & 0xFF) << 24)
                | ((pagination[1] & 0xFF) << 16)
                | ((pagination[2] & 0xFF) << 8)
                | (pagination[3] & 0xFF);

        if (0 == unsignedInt && 4 == actualBytes) {
            return new byte[]{(char) 0x7A};
        }
        int encodedSize = actualBytes + 1;
        byte[] encodedPartition = new byte[encodedSize];
        for (int idx = 0; idx < encodedSize; idx++) {
            encodedPartition[idx] = (byte) ((unsignedInt / POWERS_85[idx]) + CHAR_START_IDX);
            unsignedInt %= POWERS_85[idx];
        }
        return encodedPartition;
    }

    private static byte[] decodePartition(byte[] partition) {
        int value = 0;
        for (int idx = 0; idx < 5; idx++) {
            value += (partition[idx] - CHAR_START_IDX) * POWERS_85[idx];
        }
        return new byte[]{(byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value};
    }

    private static int calculateEncodeBufferSize(int byteArrayLength) {
        return byteArrayLength * 5 / 4;
    }

    private static int calculateDecodedBufferSize(int stringLength) {
        return stringLength * 4 / 5;
    }
}
