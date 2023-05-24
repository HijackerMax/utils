package com.hijackermax.utils.lang;

import com.hijackermax.utils.enums.ComparisonOperators;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberUtilsTest {
    @Test
    void testCompare() {
        assertTrue(NumberUtils.compare(10, 20, ComparisonOperators.LT));
        assertFalse(NumberUtils.compare(100, 20, ComparisonOperators.LT));
        assertFalse(NumberUtils.compare(20, 20, ComparisonOperators.LT));
        assertTrue(NumberUtils.compare(100, 20, ComparisonOperators.GT));
        assertFalse(NumberUtils.compare(10, 20, ComparisonOperators.GT));
        assertFalse(NumberUtils.compare(20, 20, ComparisonOperators.GT));
        assertTrue(NumberUtils.compare(10, 20, ComparisonOperators.LTE));
        assertFalse(NumberUtils.compare(100, 20, ComparisonOperators.LTE));
        assertTrue(NumberUtils.compare(20, 20, ComparisonOperators.LTE));
        assertTrue(NumberUtils.compare(100, 20, ComparisonOperators.GTE));
        assertFalse(NumberUtils.compare(10, 20, ComparisonOperators.GTE));
        assertTrue(NumberUtils.compare(20, 20, ComparisonOperators.GTE));
        assertFalse(NumberUtils.compare(100, 20, ComparisonOperators.EQ));
        assertFalse(NumberUtils.compare(10, 20, ComparisonOperators.EQ));
        assertTrue(NumberUtils.compare(20, 20, ComparisonOperators.EQ));
        assertTrue(NumberUtils.compare(100, 20, ComparisonOperators.NEQ));
        assertTrue(NumberUtils.compare(10, 20, ComparisonOperators.NEQ));
        assertFalse(NumberUtils.compare(20, 20, ComparisonOperators.NEQ));
        assertTrue(NumberUtils.compare(100, -20, ComparisonOperators.GT));
        assertTrue(NumberUtils.compare(10, 0, ComparisonOperators.GT));
    }

    @Test
    void testRequireGreaterThanZero() {
        assertThrows(IllegalArgumentException.class, () -> NumberUtils.requireGreaterThanZero(0));
        assertThrows(IllegalArgumentException.class, () -> NumberUtils.requireGreaterThanZero(-1));
        assertThrows(IllegalArgumentException.class, () -> NumberUtils.requireGreaterThanZero(Integer.MIN_VALUE));
        assertDoesNotThrow(() -> NumberUtils.requireGreaterThanZero(1));
        assertDoesNotThrow(() -> NumberUtils.requireGreaterThanZero(Integer.MAX_VALUE));
    }
}
