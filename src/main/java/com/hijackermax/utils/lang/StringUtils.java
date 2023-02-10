package com.hijackermax.utils.lang;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Set of utility methods that can help to work with Strings
 */
public final class StringUtils {
    /**
     * Empty {@link String} constant
     */
    public static final String EMPTY = "";

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
}
