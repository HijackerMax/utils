package com.hijackermax.utils.lang;

import java.util.List;
import java.util.Objects;

/**
 * Set of utility methods that can help to work with booleans
 */
public final class BooleanUtils {
    private static final List<String> TRUE_EXPRESSIONS = List.of(
            "true",
            "y",
            "yes",
            "t",
            "on",
            "1"
    );

    private static final List<String> FALSE_EXPRESSIONS = List.of(
            "false",
            "n",
            "no",
            "f",
            "off",
            "0"
    );

    private BooleanUtils() {
    }

    /**
     * Checks if primitive value of provided {@link Boolean} is false
     *
     * @param value {@link Boolean} that should be checked
     * @return true if provided {@link Boolean} has false value, false otherwise
     * @since 0.0.4
     */
    public static boolean isFalse(Boolean value) {
        return Objects.nonNull(value) && !value;
    }

    /**
     * Checks if value of provided {@link String} corresponds to group of false expressions
     *
     * @param value {@link String} that should be evaluated
     * @return true if provided {@link String} has value that corresponds to group of false expressions, false otherwise
     * @since 0.0.4
     */
    public static boolean isFalseString(String value) {
        return Boolean.FALSE.equals(parseBoolean(value));
    }

    /**
     * Checks if primitive value of provided {@link Boolean} is true
     *
     * @param value {@link Boolean} that should be checked
     * @return true if provided {@link Boolean} has true value, false otherwise
     * @since 0.0.4
     */
    public static boolean isTrue(Boolean value) {
        return Objects.nonNull(value) && value;
    }

    /**
     * Checks if value of provided {@link String} corresponds to group of true expressions
     *
     * @param value {@link String} that should be evaluated
     * @return true if provided {@link String} has value that corresponds to group of true expressions, false otherwise
     * @since 0.0.4
     */
    public static boolean isTrueString(String value) {
        return Boolean.TRUE.equals(parseBoolean(value));
    }

    /**
     * Checks if primitive value of provided {@link Boolean} is not false
     *
     * @param value {@link Boolean} that should be checked
     * @return true if provided {@link Boolean} has non-false value, false otherwise
     * @since 0.0.4
     */
    public static boolean isNotFalse(Boolean value) {
        return !isFalse(value);
    }

    /**
     * Checks if primitive value of provided {@link Boolean} is not true
     *
     * @param value {@link Boolean} that should be checked
     * @return true if provided {@link Boolean} has non-true value, false otherwise
     * @since 0.0.4
     */
    public static boolean isNotTrue(Boolean value) {
        return !isTrue(value);
    }

    /**
     * Checks if value of provided {@link String} corresponds to group of true or false expressions
     *
     * @param value {@link String} that should be evaluated
     * @return {@code Boolean.TRUE} if provided {@link String} has value that corresponds to group of true expressions,
     * {@code Boolean.FALSE} if provided {@link String} has value that corresponds to group of false expressions,
     * otherwise null
     * @since 0.0.4
     */
    public static Boolean parseBoolean(String value) {
        if (StringUtils.isNotBlank(value)) {
            String trimmedValue = StringUtils.trimToEmpty(value).toLowerCase();

            if (TRUE_EXPRESSIONS.stream().anyMatch(trimmedValue::equals)) {
                return Boolean.TRUE;
            }

            if (FALSE_EXPRESSIONS.stream().anyMatch(trimmedValue::equals)) {
                return Boolean.FALSE;
            }
        }
        return null;
    }

}
