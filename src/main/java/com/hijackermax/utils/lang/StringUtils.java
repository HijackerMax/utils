package com.hijackermax.utils.lang;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class StringUtils {
    public static final String EMPTY = "";

    private StringUtils() {
    }

    public static boolean isEmpty(String value) {
        return Objects.isNull(value) || value.isEmpty();
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static boolean isBlank(String value) {
        return Objects.isNull(value) || value.isBlank();
    }

    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }

    public static String trim(String value) {
        return Objects.isNull(value) ? null : value.trim();
    }

    public static Optional<String> ofEmpty(String value) {
        return StringUtils.isEmpty(value) ? Optional.empty() : Optional.of(value);
    }

    public static Optional<String> ofBlank(String value) {
        return StringUtils.isBlank(value) ? Optional.empty() : Optional.of(value);
    }

    public static <T> Function<Collection<T>, String> join(String delimiter, Function<T, String> toStringConverter) {
        return collection -> CollectionUtils.safeStreamOf(collection)
                .filter(Objects::nonNull)
                .map(toStringConverter)
                .map(StringUtils::trim)
                .collect(Collectors.joining(delimiter));

    }

    public static boolean equalsIgnoreCase(String left, String right) {
        return Objects.nonNull(left) ? left.equalsIgnoreCase(right) : Objects.isNull(right);
    }

    public static boolean equals(String left, String right) {
        return Objects.nonNull(left) ? left.equals(right) : Objects.isNull(right);
    }

    public static String removeNonDigits(String source) {
        return StringUtils.trimToEmpty(source).replaceAll("[^\\d.]", EMPTY);
    }

    public static boolean safeEndsWith(String source, String suffix) {
        return StringUtils.isNotEmpty(source) && StringUtils.isNotEmpty(suffix) && source.endsWith(suffix);
    }

    public static String trimToEmpty(String source) {
        return Objects.isNull(source) ? EMPTY : source.trim();
    }
}
