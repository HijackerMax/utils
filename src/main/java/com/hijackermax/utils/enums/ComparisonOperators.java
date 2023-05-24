package com.hijackermax.utils.enums;

/**
 * Comparison operators
 */
public enum ComparisonOperators {
    /**
     * Equal value operator
     */
    EQ("=="),
    /**
     * Non-equal value operator
     */
    NEQ("!="),
    /**
     * Less than value operator
     */
    LT("<"),
    /**
     * Greater than value operator
     */
    GT(">"),
    /**
     * Less than or equal value operator
     */
    LTE("<="),
    /**
     * Greater than or equal value operator
     */
    GTE(">=");

    private final String value;

    ComparisonOperators(String value) {
        this.value = value;
    }

    /**
     * Provides symbolic string value of operator
     *
     * @return symbolic string value of operator
     */
    public String getValue() {
        return value;
    }
}
