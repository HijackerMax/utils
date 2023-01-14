package com.hijackermax;

import com.hijackermax.utils.lang.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {
    @Test
    void testOfEmpty() {
        assertTrue(StringUtils.ofEmpty(null).isEmpty());
        assertTrue(StringUtils.ofEmpty("").isEmpty());
        assertTrue(StringUtils.ofEmpty("Test").isPresent());
    }

    @Test
    void testRemoveNonDigits() {
        assertEquals(StringUtils.EMPTY, StringUtils.removeNonDigits(null));
        assertEquals("1234567", StringUtils.removeNonDigits("x1c2v3b4n5m6,7"));
    }

    @Test
    void testSafeEndsWith() {
        assertFalse(StringUtils.safeEndsWith(null, null));
        assertFalse(StringUtils.safeEndsWith("Foo", null));
        assertTrue(StringUtils.safeEndsWith("Foo", "oo"));
        assertFalse(StringUtils.safeEndsWith("Bar", "rr"));
    }

}
