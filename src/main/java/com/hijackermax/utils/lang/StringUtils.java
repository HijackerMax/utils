package com.hijackermax.utils.lang;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Set of utility methods that can help to work with Strings
 */
public final class StringUtils {
    /**
     * Empty {@link String} constant
     */
    public static final String EMPTY = "";

    /**
     * Blank {@link String} constant
     */
    public static final String BLANK = " ";

    /**
     * At {@link String} constant
     */
    public static final String AT = "@";

    /**
     * Semicolon {@link String} constant
     */
    public static final String SEMICOLON = ";";

    /**
     * Comma {@link String} constant
     */
    public static final String COMMA = ",";

    /**
     * Period {@link String} constant
     */
    public static final String PERIOD = ".";

    /**
     * Dash {@link String} constant
     */
    public static final String DASH = "-";

    /**
     * Underscore {@link String} constant
     */
    public static final String UNDERSCORE = "_";

    private StringUtils() {
    }

    /**
     * Checks if {@link String} is empty or null
     *
     * @param value {@link String} to check
     * @return true if {@link String} is empty or null, otherwise false
     * @since 0.0.1
     */
    public static boolean isEmpty(String value) {
        return Objects.isNull(value) || value.isEmpty();
    }

    /**
     * Checks if {@link String} is not empty or null
     *
     * @param value {@link String} to check
     * @return true if {@link String} is not empty or null, otherwise false
     * @since 0.0.1
     */
    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    /**
     * Checks if {@link String} is blank, empty or null
     *
     * @param value {@link String} to check
     * @return true if {@link String} is blank, empty or null, otherwise false
     * @since 0.0.1
     */
    public static boolean isBlank(String value) {
        return Objects.isNull(value) || value.isBlank();
    }

    /**
     * Checks if {@link String} is not blank, empty or null
     *
     * @param value {@link String} to check
     * @return true if {@link String} is not blank, empty or null, otherwise false
     * @since 0.0.1
     */
    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }

    /**
     * Checks if {@link String} is not empty and ends with provided suffix
     *
     * @param source {@link String} to check
     * @param suffix {@link String} suffix
     * @return true if {@link String} is not empty and ends with provided suffix, otherwise false
     * @since 0.0.1
     */
    public static boolean safeEndsWith(String source, String suffix) {
        return StringUtils.isNotEmpty(source) && StringUtils.isNotEmpty(suffix) && source.endsWith(suffix);
    }

    /**
     * Checks if {@link String} is not empty and contains provided char sequence
     *
     * @param source {@link String} to check
     * @param value  {@link CharSequence} searchable char sequence
     * @return true if {@link String} is not empty and contains provided {@link CharSequence}, otherwise false
     * @since 0.0.2
     */
    public static boolean safeContains(String source, CharSequence value) {
        return StringUtils.isNotEmpty(source) && Objects.nonNull(value) && source.contains(value);
    }

    /**
     * Checks if left input {@link String} equals to right input {@link String} ignoring case
     *
     * @param left  first {@link String} to check
     * @param right second {@link String} to check
     * @return true if first input {@link String} equals to second input {@link String} ignoring case, otherwise false
     * @since 0.0.1
     */
    public static boolean equalsIgnoreCase(String left, String right) {
        return Objects.nonNull(left) ? left.equalsIgnoreCase(right) : Objects.isNull(right);
    }

    /**
     * Checks if left input {@link String} equals to right input {@link String}
     *
     * @param left  first {@link String} to check
     * @param right second {@link String} to check
     * @return true if first input {@link String} equals to second input {@link String}, otherwise false
     * @since 0.0.1
     */
    public static boolean equals(String left, String right) {
        return Objects.nonNull(left) ? left.equals(right) : Objects.isNull(right);
    }

    /**
     * Trims string if non-null {@link String} is provided, otherwise returns null
     *
     * @param source {@link String} to trim
     * @return trimmed {@link String} or null if input string is null
     * @since 0.0.1
     */
    public static String trim(String source) {
        return Objects.isNull(source) ? null : source.trim();
    }

    /**
     * Returns {@link Function} that accepts {@link Collection} and returns a {@link String}
     * built from non-null collection values, separated by provided delimiter
     *
     * @param delimiter         {@link String} which should separate values in resulting {@link String}
     * @param toStringConverter {@link Function} which converts {@link Collection} to {@link String}
     * @param <T>               {@link Collection} elements type
     * @return {@link String} built from on-null collection values,
     * separated by provided delimiter, or empty {@link String} if collection is empty or null
     * @since 0.0.1
     */
    public static <T> Function<Collection<T>, String> join(String delimiter, Function<T, String> toStringConverter) {
        return collection -> join(collection, delimiter, toStringConverter);

    }

    /**
     * Returns a {@link String} built from non-null {@link Collection} values, separated by provided delimiter
     *
     * @param collection        {@link Collection} of elements that should be joined
     * @param delimiter         {@link String} which should separate values in resulting {@link String}
     * @param toStringConverter {@link Function} which converts {@link Collection} to {@link String}
     * @param <T>               {@link Collection} elements type
     * @return {@link String} built from on-null collection values,
     * separated by provided delimiter, or empty {@link String} if collection is empty or null
     * @since 0.0.1
     */
    public static <T> String join(Collection<? extends T> collection,
                                  String delimiter,
                                  Function<? super T, String> toStringConverter) {
        return CollectionUtils.safeStreamOf(collection)
                .filter(Objects::nonNull)
                .map(toStringConverter)
                .map(StringUtils::trimToEmpty)
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(delimiter));
    }

    /**
     * Removes all non-numeric chars if non-null {@link String} is provided, otherwise returns empty string
     *
     * @param source {@link String} that should be processed
     * @return {@link String} with numeric chars only or empty string if input string is blank or null
     * @since 0.0.1
     */
    public static String removeNonDigits(String source) {
        return isBlank(source) ? EMPTY : StringUtils.trimToEmpty(source).replaceAll("[^\\d.]", EMPTY);
    }

    /**
     * Trims string if non-null {@link String} is provided, otherwise returns empty string
     *
     * @param source {@link String} to trim
     * @return trimmed {@link String} or empty string if input string is null
     * @since 0.0.1
     */
    public static String trimToEmpty(String source) {
        return isEmpty(source) ? EMPTY : source.trim();
    }

    /**
     * Pads left provided source {@link String} with provided filler {@link String} provided amount of times
     * if source {@link String} is shorter than required
     *
     * @param source         {@link String} to be padded
     * @param requiredLength {@link Integer} that represents required length of resulting {@link String}
     * @param filler         {@link String} which can be used to extend source {@link String}
     * @return padded {@link String} with provided filler {@link String} provided amount of times
     * if source {@link String} is shorter than required,
     * if source {@link String} is longer or has same length as required returns source {@link String} without changes,
     * if source {@link String} or filler {@link String} is null returns null
     * @since 0.0.2
     */
    public static String padLeft(String source, int requiredLength, String filler) {
        return pad(source, requiredLength, filler, false);
    }

    /**
     * Pads right provided source {@link String} with provided filler {@link String} provided amount of times
     * if source {@link String} is shorter than required
     *
     * @param source         {@link String} to be padded
     * @param requiredLength {@link Integer} that represents required length of resulting {@link String}
     * @param filler         {@link String} which can be used to extend source {@link String}
     * @return padded {@link String} with provided filler {@link String} provided amount of times
     * if source {@link String} is shorter than required,
     * if source {@link String} is longer or has same length as required returns source {@link String} without changes,
     * if source {@link String} or filler {@link String} is null returns null
     * @since 0.0.2
     */
    public static String padRight(String source, int requiredLength, String filler) {
        return pad(source, requiredLength, filler, true);
    }

    private static String pad(String source, int requiredLength, String filler, boolean appendToEnd) {
        if (Objects.isNull(source) || Objects.isNull(filler)) {
            return null;
        }
        int sourceLength = source.length();
        int fillerLength = filler.length();
        int diff = requiredLength - sourceLength;
        if (diff <= 0) {
            return source;
        }
        char[] missingChars = new char[diff];
        char[] fillerChars = filler.toCharArray();
        for (int i = 0; i < diff; ++i) {
            missingChars[i] = fillerChars[i % fillerLength];
        }
        String missingStringPart = new String(missingChars);
        return appendToEnd ? source.concat(missingStringPart) : missingStringPart.concat(source);
    }

    /**
     * Compresses source {@link String} using GZIP and encodes it with Base64
     *
     * @param source {@link String} that needs to be compressed
     * @return compressed with GZIP and encoded with Base64 {@link String} or empty {@link String} if source is empty or null
     * @throws IOException in case of problems during source {@link String} compression
     * @since 0.0.3
     */
    public static String compress(String source) throws IOException {
        if (isEmpty(source)) {
            return EMPTY;
        }
        try (var outputStream = new ByteArrayOutputStream(); var gzOutputStream = new GZIPOutputStream(outputStream)) {
            gzOutputStream.write(source.getBytes(StandardCharsets.UTF_8));
            gzOutputStream.finish();
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        }
    }

    /**
     * Decodes source {@link String} from Base64 and decompresses it with GZIP
     *
     * @param source {@link String} that needs to be decompressed
     * @return decoded from Base64 and decompressed with GZIP {@link String} or empty {@link String} if source is empty or null
     * @throws IOException in case of problems during source {@link String} decompression
     * @since 0.0.3
     */
    public static String decompress(String source) throws IOException {
        if (isEmpty(source)) {
            return EMPTY;
        }
        byte[] bytes = Base64.getDecoder().decode(source);
        try (var byteArrayInputStream = new ByteArrayInputStream(bytes);
             var inputStream = new GZIPInputStream(byteArrayInputStream)) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    /**
     * Returs provided value {@link String} or default {@link String} if value is null, empty or blank
     *
     * @param value        that can be null, blank or empty
     * @param defaultValue fallback value
     * @return value if it is not null, blank or empty, otherwise defaultValue
     * @since 0.0.5
     */
    public static String notBlankOrElse(String value, String defaultValue) {
        return isBlank(value) ? defaultValue : value;
    }
}
