package com.hijackermax.utils.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BooleanUtilsTest {

    @Test
    void testIsFalse() {
        assertFalse(BooleanUtils.isFalse(null));
        assertFalse(BooleanUtils.isFalse(true));
        assertTrue(BooleanUtils.isFalse(false));
    }

    @Test
    void testIsFalseString() {
        assertFalse(BooleanUtils.isFalseString(null));
        assertFalse(BooleanUtils.isFalseString("true"));
        assertFalse(BooleanUtils.isFalseString("yes"));
        assertTrue(BooleanUtils.isFalseString("false"));
        assertTrue(BooleanUtils.isFalseString("no"));
    }

    @Test
    void testIsTrue() {
        assertFalse(BooleanUtils.isTrue(null));
        assertFalse(BooleanUtils.isTrue(false));
        assertTrue(BooleanUtils.isTrue(true));
    }

    @Test
    void testIsTrueString() {
        assertFalse(BooleanUtils.isTrueString(null));
        assertFalse(BooleanUtils.isTrueString("false"));
        assertFalse(BooleanUtils.isTrueString("no"));
        assertTrue(BooleanUtils.isTrueString("true"));
        assertTrue(BooleanUtils.isTrueString("yes"));
    }


    @Test
    void testIsNotFalse() {
        assertTrue(BooleanUtils.isNotFalse(null));
        assertTrue(BooleanUtils.isNotFalse(true));
        assertFalse(BooleanUtils.isNotFalse(false));
    }

    @Test
    void testIsNotTrue() {
        assertTrue(BooleanUtils.isNotTrue(null));
        assertTrue(BooleanUtils.isNotTrue(false));
        assertFalse(BooleanUtils.isNotTrue(true));
    }

    @Test
    void testParseBoolean() {
        assertNull(BooleanUtils.parseBoolean(StringUtils.BLANK));
        assertNull(BooleanUtils.parseBoolean("Foo"));

        assertEquals(Boolean.TRUE, BooleanUtils.parseBoolean("true"));
        assertEquals(Boolean.TRUE, BooleanUtils.parseBoolean("t"));
        assertEquals(Boolean.TRUE, BooleanUtils.parseBoolean("y"));
        assertEquals(Boolean.TRUE, BooleanUtils.parseBoolean("yes"));
        assertEquals(Boolean.TRUE, BooleanUtils.parseBoolean("1"));
        assertEquals(Boolean.TRUE, BooleanUtils.parseBoolean("on"));

        assertEquals(Boolean.FALSE, BooleanUtils.parseBoolean("false"));
        assertEquals(Boolean.FALSE, BooleanUtils.parseBoolean("f"));
        assertEquals(Boolean.FALSE, BooleanUtils.parseBoolean("n"));
        assertEquals(Boolean.FALSE, BooleanUtils.parseBoolean("no"));
        assertEquals(Boolean.FALSE, BooleanUtils.parseBoolean("0"));
        assertEquals(Boolean.FALSE, BooleanUtils.parseBoolean("off"));
    }

    @Test
    void testIsBoolean() {
        assertFalse(BooleanUtils.isBoolean(null));
        assertFalse(BooleanUtils.isBoolean(StringUtils.EMPTY));
        assertFalse(BooleanUtils.isBoolean(StringUtils.BLANK));
        assertFalse(BooleanUtils.isBoolean("Foo"));
        assertFalse(BooleanUtils.isBoolean("Bar"));
        assertFalse(BooleanUtils.isBoolean("tru"));
        assertFalse(BooleanUtils.isBoolean("yep"));
        assertFalse(BooleanUtils.isBoolean("true 1"));
        assertFalse(BooleanUtils.isBoolean(",true"));

        assertTrue(BooleanUtils.isBoolean("true"));
        assertTrue(BooleanUtils.isBoolean("t"));
        assertTrue(BooleanUtils.isBoolean("y"));
        assertTrue(BooleanUtils.isBoolean("yes"));
        assertTrue(BooleanUtils.isBoolean("1"));
        assertTrue(BooleanUtils.isBoolean("on"));
        assertTrue(BooleanUtils.isBoolean("false"));
        assertTrue(BooleanUtils.isBoolean("f"));
        assertTrue(BooleanUtils.isBoolean("n"));
        assertTrue(BooleanUtils.isBoolean("no"));
        assertTrue(BooleanUtils.isBoolean("0"));
        assertTrue(BooleanUtils.isBoolean("off"));
        assertTrue(BooleanUtils.isBoolean("true "));
    }

    @Test
    void testIsNotBoolean() {
        assertFalse(BooleanUtils.isNotBoolean("true"));
        assertFalse(BooleanUtils.isNotBoolean("t"));
        assertFalse(BooleanUtils.isNotBoolean("y"));
        assertFalse(BooleanUtils.isNotBoolean("yes"));
        assertFalse(BooleanUtils.isNotBoolean("1"));
        assertFalse(BooleanUtils.isNotBoolean("on"));
        assertFalse(BooleanUtils.isNotBoolean("false"));
        assertFalse(BooleanUtils.isNotBoolean("f"));
        assertFalse(BooleanUtils.isNotBoolean("n"));
        assertFalse(BooleanUtils.isNotBoolean("no"));
        assertFalse(BooleanUtils.isNotBoolean("0"));
        assertFalse(BooleanUtils.isNotBoolean("off"));
        assertFalse(BooleanUtils.isNotBoolean("true "));

        assertTrue(BooleanUtils.isNotBoolean(null));
        assertTrue(BooleanUtils.isNotBoolean(StringUtils.EMPTY));
        assertTrue(BooleanUtils.isNotBoolean(StringUtils.BLANK));
        assertTrue(BooleanUtils.isNotBoolean("Foo"));
        assertTrue(BooleanUtils.isNotBoolean("Bar"));
        assertTrue(BooleanUtils.isNotBoolean("tru"));
        assertTrue(BooleanUtils.isNotBoolean("yep"));
        assertTrue(BooleanUtils.isNotBoolean("true 1"));
        assertTrue(BooleanUtils.isNotBoolean(",true"));
    }
}
