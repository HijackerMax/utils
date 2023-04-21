package com.hijackermax.utils;

import com.hijackermax.utils.encoders.Base122;
import com.hijackermax.utils.lang.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    void testSafeContains() {
        assertFalse(StringUtils.safeContains(null, null));
        assertFalse(StringUtils.safeContains("Foo", null));
        assertTrue(StringUtils.safeContains("Foo", "oo"));
        assertTrue(StringUtils.safeContains("Foo", "o"));
        assertTrue(StringUtils.safeContains("Foo", "Foo"));
        assertFalse(StringUtils.safeContains("Bar", "rr"));
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
        assertEquals("01234567", StringUtils.removeNonDigits("    0   x1c 2v3 b4 n5 m6 ,7  "));
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

    @Test
    void testPadLeft() {
        assertNull(StringUtils.padLeft(null, 10, "Test"));
        assertNull(StringUtils.padLeft("Test", 10, null));
        assertNull(StringUtils.padLeft(null, 10, null));
        assertEquals("Hello", StringUtils.padLeft("Hello", 2, "#$@"));
        assertEquals("Hello", StringUtils.padLeft("Hello", -20, "#$@"));
        assertEquals("#$@#$Hello", StringUtils.padLeft("Hello", 10, "#$@"));
        assertEquals("#Hello", StringUtils.padLeft("Hello", 6, "#$@"));
        assertEquals("  Hello", StringUtils.padLeft("Hello", 7, " "));
        assertEquals("       ", StringUtils.padLeft(StringUtils.EMPTY, 7, " "));
    }

    @Test
    void testPadRight() {
        assertNull(StringUtils.padRight(null, 10, "Test"));
        assertNull(StringUtils.padRight("Test", 10, null));
        assertNull(StringUtils.padRight(null, 10, null));
        assertEquals("Hello", StringUtils.padRight("Hello", 2, "#$@"));
        assertEquals("Hello", StringUtils.padRight("Hello", -20, "#$@"));
        assertEquals("Hello#$@#$", StringUtils.padRight("Hello", 10, "#$@"));
        assertEquals("Hello#", StringUtils.padRight("Hello", 6, "#$@"));
        assertEquals("Hello  ", StringUtils.padRight("Hello", 7, " "));
        assertEquals("       ", StringUtils.padRight(StringUtils.EMPTY, 7, " "));
    }

    @Test
    void testCompressDecompress() throws IOException {
        String testString = IntStream.range(0, 5000)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
        String compressedString = StringUtils.compress(testString);
        assertTrue(compressedString.length() < testString.length());
        String decompressedString = StringUtils.decompress(compressedString);
        assertEquals(testString, decompressedString);
    }

    @Test
    void testNotBlankOrElse() {
        assertEquals("Hello", StringUtils.notBlankOrElse("Hello", "Foo"));
        assertEquals("Hello", StringUtils.notBlankOrElse("Hello", null));
        assertEquals("Foo", StringUtils.notBlankOrElse(null, "Foo"));
        assertEquals("Foo", StringUtils.notBlankOrElse("", "Foo"));
        assertEquals("Foo", StringUtils.notBlankOrElse("  ", "Foo"));
        assertNull(StringUtils.notBlankOrElse("  ", null));
    }

    @Test
    void testCompressDecompressCustom() throws IOException {
        String testString = IntStream.range(0, 5000)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
        String compressedString = StringUtils.compress(testString, Base122::encode);
        assertTrue(compressedString.length() < testString.length());
        String decompressedString = StringUtils.decompress(compressedString, Base122::decode);
        assertEquals(testString, decompressedString);
    }

    @Test
    void testCompressDecompressSizeDifference() throws IOException {
        String testString = IntStream.range(0, 5000)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
        String compressedStringBase122 = StringUtils.compress(testString, Base122::encode);
        String compressedStringBase64 = StringUtils.compress(testString);
        int sourceStringBytesCount = testString.getBytes(StandardCharsets.UTF_8).length;
        int compressedBytesCountBase122 = compressedStringBase122.getBytes(StandardCharsets.UTF_8).length;
        int compressedBytesCountBase64 = compressedStringBase64.getBytes(StandardCharsets.UTF_8).length;
        assertTrue(compressedBytesCountBase122 < sourceStringBytesCount);
        assertTrue(compressedBytesCountBase122 < compressedBytesCountBase64);
        String decompressedStringBase122 = StringUtils.decompress(compressedStringBase122, Base122::decode);
        String decompressedStringBase64 = StringUtils.decompress(compressedStringBase64);
        assertEquals(decompressedStringBase64, decompressedStringBase122);
        assertEquals(testString, decompressedStringBase122);
    }

    @Test
    void testRemoveWhitespaces() {
        assertEquals(StringUtils.EMPTY, StringUtils.removeWhitespaces(null));
        assertEquals(StringUtils.EMPTY, StringUtils.removeWhitespaces(StringUtils.EMPTY));
        assertEquals(StringUtils.EMPTY, StringUtils.removeWhitespaces(StringUtils.BLANK));
        assertEquals("Foo", StringUtils.removeWhitespaces("F o o"));
        assertEquals("Foo", StringUtils.removeWhitespaces(" F o o "));
    }
}
