package com.hijackermax.utils;

import com.hijackermax.utils.lang.BooleanUtils;
import com.hijackermax.utils.lang.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
}
