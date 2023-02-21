package com.hijackermax.utils.lang;

import java.util.Objects;

/**
 * Set of utility methods that can help to work with objects
 */
public final class ObjectUtils {
    private ObjectUtils() {
    }

    /**
     * Returs provided value or default if value is null. There is no non-null limitation for defaultValue.
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
}
