package com.hijackermax.utils.lang;

import com.hijackermax.utils.encoders.Base122;
import com.hijackermax.utils.entities.Single;
import com.hijackermax.utils.entities.Tuple;
import com.hijackermax.utils.enums.ComparisonOperators;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void testNotBlankOrElseGet() {
        assertEquals("Hello", StringUtils.notBlankOrElseGet("Hello", () -> "Foo"));
        assertEquals("Hello", StringUtils.notBlankOrElseGet("Hello", () -> null));
        assertEquals("Foo", StringUtils.notBlankOrElseGet(null, () -> "Foo"));
        assertEquals("Foo", StringUtils.notBlankOrElseGet("", () -> "Foo"));
        assertEquals("Foo", StringUtils.notBlankOrElseGet("  ", () -> "Foo"));
        assertNull(StringUtils.notBlankOrElseGet("  ", () -> null));
    }

    @Test
    void testRemoveDigits() {
        assertEquals(StringUtils.EMPTY, StringUtils.removeDigits(null));
        assertEquals("xcvbnm,", StringUtils.removeDigits("x1c2v3b4n5m6,7"));
        assertEquals("       xc v b n m ,  ", StringUtils.removeDigits("    0   x1c 2v3 b4 n5 m6 ,7  "));
    }

    @Test
    void testReplace() {
        Map<CharSequence, CharSequence> replacements = Map.of("Hello", "Foo", "World", "Bar", "Red", "Green");
        assertEquals(StringUtils.EMPTY, StringUtils.replace(null, replacements));
        assertEquals("Foo Bar Test Value", StringUtils.replace("Hello World Test Value", replacements));
        assertEquals("Green Apple", StringUtils.replace("Red Apple", replacements));
    }

    @Test
    void testReplaceUnaryOperator() {
        Map<CharSequence, CharSequence> replacements = Map.of("Hello", "Foo", "World", "Bar", "Red", "Green");
        assertEquals(StringUtils.EMPTY, StringUtils.replace(replacements).apply(null));
        assertEquals("Foo Bar Test Value", StringUtils.replace(replacements).apply("Hello World Test Value"));
        assertEquals("Green Apple", StringUtils.replace(replacements).apply("Red Apple"));
    }

    @Test
    void testReplaceIgnoreCase() {
        assertEquals(StringUtils.EMPTY, StringUtils.replaceIgnoreCase(null, "Hello", "Foo"));
        assertEquals("Red Apple", StringUtils.replaceIgnoreCase("Red Apple", null, null));
        assertEquals("Foo TEST Value", StringUtils.replaceIgnoreCase("heLLo TEST Value", "Hello", "Foo"));
        assertEquals("Green Apple", StringUtils.replaceIgnoreCase("RED Apple", "Red", "Green"));
    }

    @Test
    void testStartsWith() {
        List<String> stringList = List.of("", "1Foo", "1Bar");
        assertEquals(2L, stringList.stream().filter(StringUtils.startsWith("1")).count());
        assertEquals(0L, stringList.stream().filter(StringUtils.startsWith("")).count());
        List<String> filteredList = stringList.stream()
                .filter(StringUtils.startsWith("1"))
                .collect(Collectors.toList());
        assertLinesMatch(List.of("1Foo", "1Bar"), filteredList);
    }

    @Test
    void testEndsWith() {
        List<String> stringList = List.of("", "Foo1", "Bar1");
        assertEquals(2L, stringList.stream().filter(StringUtils.endsWith("1")).count());
        assertEquals(0L, stringList.stream().filter(StringUtils.endsWith("")).count());
        List<String> filteredList = stringList.stream()
                .filter(StringUtils.endsWith("1"))
                .collect(Collectors.toList());
        assertLinesMatch(List.of("Foo1", "Bar1"), filteredList);
    }

    @Test
    void testContains() {
        List<String> stringList = List.of("", "Fo1o", "Ba1r");
        assertEquals(2L, stringList.stream().filter(StringUtils.contains("1")).count());
        assertEquals(0L, stringList.stream().filter(StringUtils.contains("")).count());
        List<String> filteredList = stringList.stream()
                .filter(StringUtils.contains("1"))
                .collect(Collectors.toList());
        assertLinesMatch(List.of("Fo1o", "Ba1r"), filteredList);
    }

    @Test
    void testHasLength() {
        List<String> stringList = List.of("Foo", "Bar", "Test", "Hello", "Hi");
        assertEquals(2L, stringList.stream().filter(StringUtils.hasLength(ComparisonOperators.GT, 3)).count());
        assertEquals(1L, stringList.stream().filter(StringUtils.hasLength(ComparisonOperators.GT, 4)).count());
        assertEquals(4L, stringList.stream().filter(StringUtils.hasLength(ComparisonOperators.LT, 5)).count());
        assertEquals(4L, stringList.stream().filter(StringUtils.hasLength(ComparisonOperators.NEQ, 5)).count());
        assertEquals(3L, stringList.stream().filter(StringUtils.hasLength(ComparisonOperators.LTE, 3)).count());
        List<String> filteredList = stringList.stream()
                .filter(StringUtils.hasLength(ComparisonOperators.EQ, 3))
                .collect(Collectors.toList());
        assertLinesMatch(List.of("Foo", "Bar"), filteredList);
    }

    @Test
    void testIfNotEmpty() {
        Single<String> valueHolder = new Single<>("Original");
        StringUtils.ifNotEmpty(null, valueHolder::setValue);
        assertEquals("Original", valueHolder.getValue());
        StringUtils.ifNotEmpty(StringUtils.EMPTY, valueHolder::setValue);
        assertEquals("Original", valueHolder.getValue());
        StringUtils.ifNotEmpty(StringUtils.BLANK, valueHolder::setValue);
        assertEquals(StringUtils.BLANK, valueHolder.getValue());
        StringUtils.ifNotEmpty("Foo", valueHolder::setValue);
        assertEquals("Foo", valueHolder.getValue());
    }

    @Test
    void testIfNotBlank() {
        Single<String> valueHolder = new Single<>("Original");
        StringUtils.ifNotBlank(null, valueHolder::setValue);
        assertEquals("Original", valueHolder.getValue());
        StringUtils.ifNotBlank(StringUtils.EMPTY, valueHolder::setValue);
        assertEquals("Original", valueHolder.getValue());
        StringUtils.ifNotBlank(StringUtils.BLANK, valueHolder::setValue);
        assertEquals("Original", valueHolder.getValue());
        StringUtils.ifNotBlank("Foo", valueHolder::setValue);
        assertEquals("Foo", valueHolder.getValue());
    }

    @Test
    void testCapitalizeDefault() {
        assertEquals(StringUtils.EMPTY, StringUtils.capitalize(null));
        assertEquals("Foo Bar", StringUtils.capitalize("foo bar"));
        assertEquals("Foo Bar", StringUtils.capitalize("FOO BAR"));
        assertEquals("Foo Bar", StringUtils.capitalize("fOO bAR"));
        assertEquals("Foo Bar", StringUtils.capitalize("fOo bAr"));
    }

    @Test
    void testCapitalize() {
        assertEquals(StringUtils.EMPTY, StringUtils.capitalize(null, ' '));
        assertEquals("Foo@Bar", StringUtils.capitalize("foo@bar", ' ', '@'));
        assertEquals("Foo Bar-Test", StringUtils.capitalize("FOO BAR-TEST", ' ', '-'));
        assertEquals("Foo B$Ar", StringUtils.capitalize("fOO b$AR", ' ', '$'));
        assertEquals("Foo Ba$R", StringUtils.capitalize("fOo bA$r", ' ', '$'));
    }

    @Test
    void testSafeJoin() {
        assertEquals(StringUtils.EMPTY, StringUtils.safeJoin(null, ","));
        assertEquals(StringUtils.EMPTY, StringUtils.safeJoin(Collections.emptyList(), ","));
        assertEquals("Foo", StringUtils.safeJoin(List.of("Foo"), ","));
        assertEquals("Foo,Bar", StringUtils.safeJoin(List.of("Foo", "Bar"), ","));
        assertEquals("Foo$Bar$Test", StringUtils.safeJoin(List.of("Foo", "Bar", "Test"), "$"));
    }

    @Test
    void testIfBothNotEmpty() {
        Tuple<String, String> valueHolder = new Tuple<>("OriginalL", "OriginalR");
        StringUtils.ifBothNotEmpty(null, null, valueHolder::setPair);
        assertEquals("OriginalL", valueHolder.getKey());
        assertEquals("OriginalR", valueHolder.getValue());
        StringUtils.ifBothNotEmpty(StringUtils.EMPTY, StringUtils.EMPTY, valueHolder::setPair);
        assertEquals("OriginalL", valueHolder.getKey());
        assertEquals("OriginalR", valueHolder.getValue());
        StringUtils.ifBothNotEmpty(StringUtils.BLANK, StringUtils.BLANK, valueHolder::setPair);
        assertEquals(StringUtils.BLANK, valueHolder.getKey());
        assertEquals(StringUtils.BLANK, valueHolder.getValue());
        StringUtils.ifBothNotEmpty("Foo", "Bar", valueHolder::setPair);
        assertEquals("Foo", valueHolder.getKey());
        assertEquals("Bar", valueHolder.getValue());
    }

    @Test
    void testIfBothNotBlank() {
        Tuple<String, String> valueHolder = new Tuple<>("OriginalL", "OriginalR");
        StringUtils.ifBothNotBlank(null, null, valueHolder::setPair);
        assertEquals("OriginalL", valueHolder.getKey());
        assertEquals("OriginalR", valueHolder.getValue());
        StringUtils.ifBothNotBlank(StringUtils.EMPTY, StringUtils.EMPTY, valueHolder::setPair);
        assertEquals("OriginalL", valueHolder.getKey());
        assertEquals("OriginalR", valueHolder.getValue());
        StringUtils.ifBothNotBlank(StringUtils.BLANK, StringUtils.BLANK, valueHolder::setPair);
        assertEquals("OriginalL", valueHolder.getKey());
        assertEquals("OriginalR", valueHolder.getValue());
        StringUtils.ifBothNotBlank("Foo", "Bar", valueHolder::setPair);
        assertEquals("Foo", valueHolder.getKey());
        assertEquals("Bar", valueHolder.getValue());
    }

    @Test
    void testNotEmptyOrElse() {
        assertEquals("Hello", StringUtils.notEmptyOrElse("Hello", "Foo"));
        assertEquals("Hello", StringUtils.notEmptyOrElse("Hello", null));
        assertEquals("Foo", StringUtils.notEmptyOrElse(null, "Foo"));
        assertEquals("Foo", StringUtils.notEmptyOrElse("", "Foo"));
        assertEquals("  ", StringUtils.notEmptyOrElse("  ", "Foo"));
    }

    @Test
    void testNamedFormat() {
        assertEquals(StringUtils.EMPTY, StringUtils.namedFormat(null, null));
        assertEquals("Test", StringUtils.namedFormat("Test", null));
        assertEquals(StringUtils.EMPTY, StringUtils.namedFormat(null, Map.of()));
        assertEquals("Test", StringUtils.namedFormat("Test", Map.of()));
        assertEquals(StringUtils.BLANK, StringUtils.namedFormat(StringUtils.BLANK, Map.of("Test", "Test")));

        String template = "Foo ${text_default?`bar`}: ${numeric%x} = ${numeric} ${numeric_default?`128`%x} % ${text}";
        Map<String, Object> values = Map.of(
                "numeric", 255,
                "text", "Test"
        );
        assertEquals("Foo bar: ff = 255 128 % Test", StringUtils.namedFormat(template, values));

        String spacesInDefault = "Foo ${text_default?`<? \\b-a-r/ ?>`}: ${numeric%x} = ${numeric} ${numeric_default?`@[  128]$^`%x} % ${text} ${missing}";
        Map<String, Object> values2 = Map.of(
                "numeric", 255,
                "text", "Test"
        );
        assertEquals("Foo <? \\b-a-r/ ?>: ff = 255 @[  128]$^ % Test ${missing}", StringUtils.namedFormat(spacesInDefault, values2));
    }

    @Test
    void testTransferValues() {
        Pattern pattern = Pattern.compile("(?iu)(?:(\\$\\{(?<value1>\\w+)?\\})|(#\\{(?<value2>\\w+)?\\})|(%\\{(?<value3>\\w+)?\\}))");
        assertEquals(StringUtils.EMPTY, StringUtils.transferValues(null, null, null));
        assertEquals(StringUtils.EMPTY, StringUtils.transferValues(pattern, StringUtils.BLANK, StringUtils.BLANK));
        assertEquals("Foo ${value1} bar ${value2} ${value3} Test Test", StringUtils.transferValues(Pattern.compile(""), "Foo ${value_one} %{value_three}", "Foo ${value1} bar ${value2} ${value3} ${value4?`Test Test`}"));
        assertEquals("Foo ${value1} bar ${value2} ${value3} -", StringUtils.transferValues(Pattern.compile(""), "Foo ${value_one} %{value_three}", "Foo ${value1} bar ${value2} ${value3} ${value4?`-`}"));
        assertEquals("Foo  bar ${value2} ${value3} -", StringUtils.transferValues(Pattern.compile(""), "Foo ${value_one} %{value_three}", "Foo ${value1?``} bar ${value2} ${value3} ${value4?`-`}"));
        assertEquals("Foo value_one bar value_two default", StringUtils.transferValues(pattern, "Foo ${value_one} #{value_two}", "Foo ${value1} bar ${value2} ${value4?`default`}"));
        assertEquals("Foo value_one bar ${value2} value_three Test Test", StringUtils.transferValues(pattern, "Foo ${value_one} %{value_three}", "Foo ${value1} bar ${value2} ${value3} ${value4?`Test Test`}"));
    }
}
