package com.hijackermax.utils.encoders;

import com.hijackermax.utils.lang.StringUtils;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Objects;

/**
 * Binary-to-text encoding
 *
 * @since 0.0.6
 */
public final class Base58 {
    private static final char[] CHARSET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final BigInteger FIFTY_EIGHT = BigInteger.valueOf(58L);

    private Base58() {
    }

    /**
     * Encodes provided byte array to Base58 ASCII {@link String}
     *
     * @param source byte array
     * @return encoded ASCII {@link String}
     * @since 0.0.6
     */
    public static String encode(byte[] source) {
        int dataLength = Objects.requireNonNull(source).length;
        if (0 == dataLength) {
            return StringUtils.EMPTY;
        }
        ByteArrayOutputStream bufferStream = new ByteArrayOutputStream(calculateEncodeBufferSize(dataLength));
        BigInteger reminder = new BigInteger(1, source);
        while (reminder.compareTo(BigInteger.ZERO) > 0) {
            int idx = reminder.mod(FIFTY_EIGHT).intValue();
            reminder = reminder.divide(FIFTY_EIGHT);
            bufferStream.write(CHARSET[idx]);
        }
        byte[] buffer = bufferStream.toByteArray();
        StringBuilder result = new StringBuilder();
        boolean isLeadingZero = true;
        for (int idx = buffer.length - 1; idx >= 0; idx--) {
            byte nextByte = buffer[idx];
            if (isLeadingZero) {
                boolean isByteZero = 0 == nextByte;
                if (isByteZero) {
                    result.insert(0, (char) 0x49);
                }
                isLeadingZero = isByteZero;
            }
            result.append((char) nextByte);
        }
        return result.toString();
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
        BigInteger buffer = BigInteger.ZERO;
        boolean isLeadingOne = true;
        for (char nextChar : source.toCharArray()) {
            int idx = Arrays.binarySearch(CHARSET, nextChar);
            if (idx < 0) {
                throw new IllegalArgumentException("Provided source string is not Base58 encoded");
            }
            if (isLeadingOne) {
                boolean isCharOne = 0x49 == nextChar;
                if (isCharOne) {
                    result.write(0x00);
                }
                isLeadingOne = isCharOne;
            }
            buffer = buffer.multiply(FIFTY_EIGHT).add(BigInteger.valueOf(idx));
        }
        byte[] bufferBytes = buffer.toByteArray();
        int offset = 0x00 == bufferBytes[0] ? 1 : 0;
        result.write(bufferBytes, offset, bufferBytes.length - offset);
        return result.toByteArray();
    }

    private static int calculateEncodeBufferSize(int byteArrayLength) {
        return byteArrayLength * 7 / 5;
    }

    private static int calculateDecodedBufferSize(int stringLength) {
        return stringLength * 5 / 7;
    }
}
