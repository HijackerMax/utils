package com.hijackermax.utils.encoders;

import com.hijackermax.utils.entities.Tuple;
import com.hijackermax.utils.lang.ObjectUtils;
import com.hijackermax.utils.lang.StringUtils;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static com.hijackermax.utils.lang.MathUtils.add;
import static com.hijackermax.utils.lang.MathUtils.subtract;

/**
 * Binary-to-text encoding based on variant of Douglas Crockford, without check
 *
 * @since 0.0.7
 */
public final class Base32 {
    private static final char[] CHARSET = "0123456789ABCDEFGHJKMNPQRSTVWXYZ".toCharArray();
    private static final byte[] IGNORED_CHARS = {0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x20, 0x2D};
    private static final Map<Character, Integer> ALIASES = Map.of('O', 0, 'I', 1, 'L', 1);
    private static final byte STOP_BYTE = (byte) 0x5B;

    private Base32() {
    }

    /**
     * Encodes provided byte array to Base32 String {@link String}
     *
     * @param source byte array
     * @return encoded {@link String}
     * @since 0.0.7
     */
    public static String encode(byte[] source) {
        int dataLength = Objects.requireNonNull(source).length;
        StringBuilder result = new StringBuilder(calculateEncodeBufferSize(dataLength));
        Tuple<Integer, Integer> pointers = new Tuple<>(0, 0);
        for (byte partition = nextPartition(source, pointers); STOP_BYTE != partition; partition = nextPartition(source, pointers)) {
            result.append(CHARSET[partition]);
        }
        return result.toString();
    }


    /**
     * Decodes provided {@link String} to byte array
     *
     * @param source {@link String}
     * @return decoded byte array
     * @throws IllegalArgumentException if provided string is not base32 encoded
     * @since 0.0.7
     */
    public static byte[] decode(String source) {
        if (StringUtils.isBlank(source)) {
            return new byte[0];
        }
        ByteArrayOutputStream result = new ByteArrayOutputStream(calculateDecodedBufferSize(source.length()));
        Tuple<Integer, Integer> dataHolder = new Tuple<>(0, 0);
        for (char nextChar : source.toCharArray()) {
            if (0 <= Arrays.binarySearch(IGNORED_CHARS, (byte) nextChar)) {
                continue;
            }
            char upperCaseChar = Character.toUpperCase(nextChar);
            int idx = Arrays.binarySearch(CHARSET, upperCaseChar);
            int partition = idx >= 0 ? idx : ObjectUtils.valueOrDefault(ALIASES.get(upperCaseChar), -1);
            if (0 > partition) {
                throw new IllegalArgumentException("Provided source string is not Base32 encoded");
            }
            pushPartition(partition << 3, dataHolder, result::write);
        }
        return result.toByteArray();
    }

    private static byte nextPartition(byte[] data, Tuple<Integer, Integer> pointers) {
        if (pointers.getKey() >= data.length) {
            return STOP_BYTE;
        }
        int firstPart = (((0xFE >>> pointers.getValue()) & (data[pointers.getKey()] & 0xFF))
                << pointers.getValue()) >>> 3;
        pointers.modifyValue(add(5));
        if (pointers.getValue() < 8) {
            return (byte) firstPart;
        }
        pointers.modifyValue(subtract(8));
        pointers.modifyKey(add(1));
        if (pointers.getKey() >= data.length) {
            return (byte) firstPart;
        }
        int secondPart = (((0xFF00 >>> pointers.getValue()) & (data[pointers.getKey()] & 0xFF)) & 0xFF)
                >>> 8 - pointers.getValue();
        return (byte) (firstPart | secondPart);
    }

    private static void pushPartition(int nextValue, Tuple<Integer, Integer> dataHolder, Consumer<Byte> valueConsumer) {
        dataHolder.modifyValue(k -> k | (nextValue >> dataHolder.getKey()));
        dataHolder.modifyKey(add(5));
        if (dataHolder.getKey() >= 8) {
            valueConsumer.accept((byte) (dataHolder.getValue() & 0xFF));
            dataHolder.modifyKey(subtract(8));
            dataHolder.setValue(nextValue << (5 - dataHolder.getKey()) & 0xFF);
        }
    }

    private static int calculateEncodeBufferSize(int byteArrayLength) {
        return ((byteArrayLength * 8) / 5) + 7;
    }

    private static int calculateDecodedBufferSize(int stringLength) {
        return (stringLength * 5) / 8;
    }
}
