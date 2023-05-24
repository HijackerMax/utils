package com.hijackermax.utils.lang;

import com.hijackermax.utils.enums.ComparisonOperators;

import java.util.Objects;

/**
 * Set of utility methods that can help with number operations
 */
public final class NumberUtils {
    private NumberUtils() {
    }

    /**
     * Provides comparison of two ints with provided {@link ComparisonOperators}
     *
     * @param left     the left value in expression
     * @param right    the right value in expression
     * @param operator the operator
     * @return comparison result
     * @since 0.0.8
     */
    public static boolean compare(int left, int right, ComparisonOperators operator) {
        switch (Objects.requireNonNull(operator)) {
            case EQ:
                return right == left;
            case NEQ:
                return right != left;
            case LT:
                return right > left;
            case GT:
                return right < left;
            case GTE:
                return right <= left;
            case LTE:
                return right >= left;
            default:
                return false;
        }
    }

    /**
     * Conducts validation if provided value is greater than zero
     *
     * @param value the value to check
     * @return provided value if greater than zero
     * @throws IllegalArgumentException if provided value is equal to zero or negative
     * @since 0.0.8
     */
    public static int requireGreaterThanZero(int value) {
        if (value > 0) {
            return value;
        }
        throw new IllegalArgumentException("Value should be greater than zero");
    }
}
