package com.hijackermax.utils.lang;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import static com.hijackermax.utils.lang.StringUtils.isEmpty;
import static java.util.Optional.ofNullable;

/**
 * Set of utility methods that can help to work with objects
 */
public final class ObjectUtils {
    private ObjectUtils() {
    }

    /**
     * Returns provided value or default if value is null. There is no non-null limitation for defaultValue.
     *
     * @param value        that can be null
     * @param defaultValue fallback value
     * @param <T>          value type
     * @return value if it is not null or defaultValue
     * @since 0.0.3
     */
    public static <T> T valueOrDefault(T value, T defaultValue) {
        return Objects.isNull(value) ? defaultValue : value;
    }

    /**
     * Maps provided source value with provided {@link Function} or default if value is null
     *
     * @param source       value that can be null
     * @param processor    {@link Function} that maps source value type to result value type
     * @param defaultValue fallback value
     * @param <T>          source value type
     * @param <R>          result value type
     * @return mapped source value or default if source value is null
     * @since 0.0.6
     */
    public static <T, R> R mapValueOrDefault(T source, Function<T, R> processor, R defaultValue) {
        return ofNullable(source)
                .map(processor)
                .orElse(defaultValue);
    }

    /**
     * Compresses source byte array using GZIP and encodes it with Base64
     *
     * @param source byte array that needs to be compressed
     * @return compressed with GZIP and encoded with Base64 byte array or empty {@link String} if source is empty or null
     * @throws IOException in case of problems during source byte array compression
     * @since 0.0.6
     */
    public static String compress(byte[] source) throws IOException {
        return compress(source, Base64.getEncoder()::encodeToString);
    }

    /**
     * Compresses source byte array using GZIP and encodes it with provided encoder
     *
     * @param source  byte array that needs to be compressed
     * @param encoder byte array to {@link String} encoder
     * @return compressed with GZIP and encoded with provided encoder byte array or empty {@link String} if source is empty or null
     * @throws IOException in case of problems during source byte array compression
     * @since 0.0.6
     */
    public static String compress(byte[] source, Function<byte[], String> encoder) throws IOException {
        if (Objects.isNull(source)) {
            return StringUtils.EMPTY;
        }
        try (var outputStream = new ByteArrayOutputStream(); var gzOutputStream = new GZIPOutputStream(outputStream)) {
            gzOutputStream.write(source);
            gzOutputStream.finish();
            return encoder.apply(outputStream.toByteArray());
        }
    }

    /**
     * Decodes source {@link String} that represents byte array from Base64 and decompresses it with GZIP
     *
     * @param source {@link String} that needs to be decompressed
     * @return decoded from Base64 and decompressed with GZIP byte array or empty byte array if source is empty or null
     * @throws IOException in case of problems during source {@link String} decompression
     * @since 0.0.6
     */
    public static byte[] decompress(String source) throws IOException {
        return decompress(source, Base64.getDecoder()::decode);
    }

    /**
     * Decodes source {@link String} that represents byte array using provided decoder and decompresses it with GZIP
     *
     * @param source  {@link String} that needs to be decompressed
     * @param decoder {@link String} to byte array decoder
     * @return decoded with provided decoder and decompressed with GZIP byte array or empty byte array if source is empty or null
     * @throws IOException in case of problems during source byte array decompression
     * @since 0.0.6
     */
    public static byte[] decompress(String source, Function<String, byte[]> decoder) throws IOException {
        if (isEmpty(source)) {
            return new byte[0];
        }
        byte[] bytes = decoder.apply(source);
        try (var byteArrayInputStream = new ByteArrayInputStream(bytes);
             var inputStream = new GZIPInputStream(byteArrayInputStream)) {
            return inputStream.readAllBytes();
        }
    }

    /**
     * Returns provided value or invokes default value {@link Supplier} if value is null
     *
     * @param value                that can be null
     * @param defaultValueSupplier fallback value {@link Supplier}
     * @param <T>                  value type
     * @return value if it is not null or default value {@link Supplier} invoke result
     * @since 0.0.8
     */
    public static <T> T valueOrGetDefault(T value, Supplier<T> defaultValueSupplier) {
        return Objects.isNull(value) ? defaultValueSupplier.get() : value;
    }

    /**
     * Passes provided object if it is not null to the provided {@link Consumer}
     *
     * @param value         the value to check
     * @param valueConsumer non-null value consumer
     * @param <T>           input value type
     * @since 0.1.0
     */
    public static <T> void ifNotNull(T value, Consumer<T> valueConsumer) {
        if (Objects.nonNull(value)) {
            valueConsumer.accept(value);
        }
    }
}
