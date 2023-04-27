package com.hijackermax.utils.encoders;

import com.hijackermax.utils.entities.Tuple;
import com.hijackermax.utils.lang.StringUtils;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

import static com.hijackermax.utils.lang.MathUtils.add;
import static com.hijackermax.utils.lang.MathUtils.subtract;

/**
 * Binary-to-text encoding that is more space-efficient than base64, based on idea of Kevin Albertson
 *
 * @since 0.0.6
 */
public final class Base122 {
    private static final byte[] INVALID_CHARS = {0x00, 0x0A, 0x0D, 0x22, 0x26, 0x5C};
    private static final byte K_SHORTENED = 0x07;
    private static final byte STOP_BYTE = (byte) 0x80;

    private Base122() {
    }

    /**
     * Encodes provided byte array to Base122 UTF-8 {@link String}
     *
     * @param source byte array
     * @return encoded UTF-8 {@link String}
     * @since 0.0.6
     */
    public static String encode(byte[] source) {
        int dataLength = Objects.requireNonNull(source).length;
        ByteArrayOutputStream result = new ByteArrayOutputStream(calculateEncodeBufferSize(dataLength));
        Tuple<Integer, Integer> dataPointers = new Tuple<>(0, 0);
        for (byte partition = nextPartition(source, dataLength, dataPointers); STOP_BYTE != partition; partition = nextPartition(source, dataLength, dataPointers)) {
            int illegalIndex = Arrays.binarySearch(INVALID_CHARS, partition);
            if (0 > illegalIndex) {
                result.write(partition);
                continue;
            }
            byte nextSevenBits = nextPartition(source, dataLength, dataPointers);
            byte firstByte = (byte) (0xC2 | (0x07 & (STOP_BYTE == nextSevenBits ? K_SHORTENED : illegalIndex)) << 2);
            if (STOP_BYTE == nextSevenBits) {
                nextSevenBits = partition;
            }
            result.write(firstByte | ((nextSevenBits & 0x40) > 0 ? 1 : 0));
            result.write(0x80 | (nextSevenBits & 0x3F));
        }
        return result.toString(StandardCharsets.UTF_8);
    }

    /**
     * Decodes provided UTF-8 {@link String} to byte array
     *
     * @param source UTF-8 {@link String}
     * @return decoded byte array
     * @since 0.0.6
     */
    public static byte[] decode(String source) {
        if (StringUtils.isBlank(source)) {
            return new byte[0];
        }
        ByteArrayOutputStream result = new ByteArrayOutputStream(calculateDecodedBufferSize(source.length()));
        Tuple<Integer, Integer> dataPointers = new Tuple<>(0, 0);
        for (char nextChar : source.toCharArray()) {
            if (nextChar > 0x7F) {
                int illegalIndex = (nextChar >>> 8) & 0x07;
                if (illegalIndex != K_SHORTENED) {
                    pushPartition(INVALID_CHARS[illegalIndex] << 1, dataPointers, result::write);
                }
                pushPartition((nextChar & 0x7F) << 1, dataPointers, result::write);
            } else {
                pushPartition(nextChar << 1, dataPointers, result::write);
            }
        }
        return result.toByteArray();
    }

    private static byte nextPartition(byte[] data, int length, Tuple<Integer, Integer> dataPointers) {
        if (dataPointers.getKey() >= length) {
            return STOP_BYTE;
        }
        int firstPart = (((0xFE >>> dataPointers.getValue()) & (data[dataPointers.getKey()] & 0xFF))
                << dataPointers.getValue()) >>> 1;
        dataPointers.modifyValue(add(7));
        if (dataPointers.getValue() < 8) {
            return (byte) firstPart;
        }
        dataPointers.modifyValue(subtract(8));
        dataPointers.modifyKey(add(1));
        if (dataPointers.getKey() >= length) {
            return (byte) firstPart;
        }
        int secondPart = (((0xFF00 >>> dataPointers.getValue()) & (data[dataPointers.getKey()] & 0xFF)) & 0xFF)
                >>> 8 - dataPointers.getValue();
        return (byte) (firstPart | secondPart);
    }

    private static void pushPartition(int nextValue, Tuple<Integer, Integer> dataPointers, Consumer<Byte> valueConsumer) {
        dataPointers.modifyKey(k -> k | (nextValue >>> dataPointers.getValue()));
        dataPointers.modifyValue(add(7));
        if (dataPointers.getValue() >= 8) {
            valueConsumer.accept((byte) (dataPointers.getKey() & 0xFF));
            dataPointers.modifyValue(subtract(8));
            dataPointers.setKey(nextValue << (7 - dataPointers.getValue()) & 0xFF);
        }
    }

    private static int calculateEncodeBufferSize(int byteArrayLength) {
        return byteArrayLength + (byteArrayLength / 7);
    }

    private static int calculateDecodedBufferSize(int stringLength) {
        return (stringLength * 7) >> 3;
    }
}
