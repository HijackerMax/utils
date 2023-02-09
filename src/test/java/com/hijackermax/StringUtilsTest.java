package com.hijackermax;

import com.hijackermax.utils.lang.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void testSafeEndsWith() {
        assertFalse(StringUtils.safeEndsWith(null, null));
        assertFalse(StringUtils.safeEndsWith("Foo", null));
        assertTrue(StringUtils.safeEndsWith("Foo", "oo"));
        assertFalse(StringUtils.safeEndsWith("Bar", "rr"));
    }

    @Test
    void testIsEmpty() {
        assertTrue(StringUtils.isEmpty(null));
        assertTrue(StringUtils.isEmpty(""));
        assertFalse(StringUtils.isEmpty("Test"));
        assertFalse(StringUtils.isEmpty(" "));
    }

    @Test
    void testIsNotEmpty() {
        assertFalse(StringUtils.isNotEmpty(null));
        assertFalse(StringUtils.isNotEmpty(""));
        assertTrue(StringUtils.isNotEmpty("Test"));
        assertTrue(StringUtils.isNotEmpty(" "));
    }

    @Test
    void testIsBlank() {
        assertTrue(StringUtils.isBlank(null));
        assertTrue(StringUtils.isBlank(""));
        assertTrue(StringUtils.isBlank(" "));
        assertTrue(StringUtils.isBlank("  "));
        assertFalse(StringUtils.isBlank("Test"));
    }

    @Test
    void testIsNotBlank() {
        assertFalse(StringUtils.isNotBlank(null));
        assertFalse(StringUtils.isNotBlank(""));
        assertFalse(StringUtils.isNotBlank(" "));
        assertFalse(StringUtils.isNotBlank("  "));
        assertTrue(StringUtils.isNotBlank("Test"));
    }

    @Test
    void testEquals() {
        assertTrue(StringUtils.equals(null, null));
        assertTrue(StringUtils.equalsIgnoreCase("", ""));
        assertFalse(StringUtils.equals("Foo", null));
        assertFalse(StringUtils.equals(null, "Bar"));
        assertFalse(StringUtils.equals("Foo", "Bar"));
        assertFalse(StringUtils.equals("FOO", "foo"));
        assertTrue(StringUtils.equals("FOO", "FOO"));
        assertTrue(StringUtils.equals("foo", "foo"));
        assertTrue(StringUtils.equals("foO", "foO"));
    }

    @Test
    void testEqualsIgnoreCase() {
        assertTrue(StringUtils.equalsIgnoreCase(null, null));
        assertTrue(StringUtils.equalsIgnoreCase("", ""));
        assertFalse(StringUtils.equalsIgnoreCase("Foo", null));
        assertFalse(StringUtils.equalsIgnoreCase(null, "Bar"));
        assertFalse(StringUtils.equalsIgnoreCase("Foo", "Bar"));
        assertTrue(StringUtils.equalsIgnoreCase("FOO", "foo"));
        assertTrue(StringUtils.equalsIgnoreCase("foo", "FOO"));
        assertTrue(StringUtils.equalsIgnoreCase("FOO", "FOO"));
        assertTrue(StringUtils.equalsIgnoreCase("foo", "foo"));
        assertTrue(StringUtils.equalsIgnoreCase("fOO", "FoO"));
    }

    @Test
    void testTrim() {
        assertNull(StringUtils.trim(null));
        assertEquals(StringUtils.EMPTY, StringUtils.trim(""));
        assertEquals("Foo", StringUtils.trim("Foo"));
        assertEquals("Foo", StringUtils.trim(" Foo"));
        assertEquals("Foo", StringUtils.trim("Foo "));
        assertEquals("Foo", StringUtils.trim("       Foo     "));
    }

    @Test
    void testTrimToEmpty() {
        assertEquals(StringUtils.EMPTY, StringUtils.trimToEmpty(null));
        assertEquals(StringUtils.EMPTY, StringUtils.trimToEmpty(""));
        assertEquals("Foo", StringUtils.trimToEmpty("Foo"));
        assertEquals("Foo", StringUtils.trimToEmpty(" Foo"));
        assertEquals("Foo", StringUtils.trimToEmpty("Foo "));
        assertEquals("Foo", StringUtils.trimToEmpty("       Foo     "));
    }

    @Test
    void testRemoveNonDigits() {
        assertEquals(StringUtils.EMPTY, StringUtils.removeNonDigits(null));
        assertEquals("1234567", StringUtils.removeNonDigits("x1c2v3b4n5m6,7"));
    }

    @Test
    void testJoin() {
        List<Integer> sourceList = new ArrayList<>(List.of(1, 0, 46, 7, 22, 55, 33));
        sourceList.add(3, null);
        assertEquals(StringUtils.EMPTY, StringUtils.join(null, ", ", String::valueOf));
        assertEquals(StringUtils.EMPTY, StringUtils.join(Collections.emptyList(), ", ", String::valueOf));
        assertEquals("1, 0, 46, 7, 22, 55, 33", StringUtils.join(sourceList, ", ", String::valueOf));
        assertEquals("1 $ 0 $ 46 $ 7 $ 22 $ 55 $ 33", StringUtils.join(sourceList, " $ ", String::valueOf));
    }

    @Test
    void testJoinFunction() {
        List<Integer> sourceList = new ArrayList<>(List.of(1, 0, 46, 7, 22, 55, 33));
        sourceList.add(3, null);
        assertEquals(StringUtils.EMPTY, StringUtils.join(", ", String::valueOf).apply(null));
        assertEquals(StringUtils.EMPTY, StringUtils.join(", ", String::valueOf).apply(Collections.emptyList()));
        assertEquals(
                "1, 0, 46, 7, 22, 55, 33",
                of(sourceList).map(StringUtils.join(", ", String::valueOf)).orElseThrow()
        );
        assertEquals(
                "1 $ 0 $ 46 $ 7 $ 22 $ 55 $ 33",
                of(sourceList).map(StringUtils.join(" $ ", String::valueOf)).orElseThrow()
        );
    }
}
