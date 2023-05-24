package com.hijackermax.utils.lang;

import com.hijackermax.utils.aux.Colors;
import com.hijackermax.utils.entities.Tuple;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CollectorUtilsTest {
    @Test
    void testGroupingByWithNull() {
        List<Tuple<String, String>> input = List.of(
                new Tuple<>("Foo", "Bar"),
                new Tuple<>(null, "Test"),
                new Tuple<>("Hello", "World")
        );
        Map<String, List<Tuple<String, String>>> nullKeyMap = input.stream()
                .collect(CollectorUtils.groupingByWithNull(Tuple::getKey));

        assertEquals(3, nullKeyMap.size());
        assertTrue(nullKeyMap.containsKey("Foo"));
        assertTrue(nullKeyMap.containsKey("Hello"));
        assertTrue(nullKeyMap.containsKey(null));
    }

    @Test
    void testGroupingByWithNullValuesExtraction() {
        List<Tuple<String, String>> input = List.of(
                new Tuple<>("Foo", "Bar"),
                new Tuple<>(null, "Test"),
                new Tuple<>("Hello", "World")
        );
        Map<String, List<String>> nullKeyMap = input.stream()
                .collect(CollectorUtils.groupingByWithNull(Tuple::getKey, Tuple::getValue));

        assertEquals(3, nullKeyMap.size());
        assertTrue(nullKeyMap.containsKey("Foo"));
        assertTrue(nullKeyMap.containsKey("Hello"));
        assertTrue(nullKeyMap.containsKey(null));
        assertEquals(List.of("Test"), nullKeyMap.get(null));
    }

    @Test
    void testToStringCollector() {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        String result = IntStream.range(0, chars.length)
                .mapToObj(idx -> chars[idx])
                .collect(CollectorUtils.toStringCollector());
        assertEquals(chars.length, result.length());
    }

    @Test
    void testToEnumMapCollectorWithDefaultMergeFunction() {
        List<Tuple<Colors, String>> colorsWithDuplicates = List.of(
                new Tuple<>(Colors.GREEN, "00FF00"),
                new Tuple<>(Colors.GREEN, "00FA00"),
                new Tuple<>(Colors.RED, "FF0000"),
                new Tuple<>(Colors.BLUE, "0000FF")
        );

        assertThrows(
                IllegalStateException.class,
                () -> colorsWithDuplicates.stream().collect(CollectorUtils.toEnumMap(Tuple::getKey, Tuple::getValue, Colors.class))
        );

        List<Tuple<Colors, String>> uniqueColors = List.of(
                new Tuple<>(Colors.GREEN, "00FF00"),
                new Tuple<>(Colors.RED, "FF0000"),
                new Tuple<>(Colors.BLUE, "0000FF")
        );

        assertDoesNotThrow(
                () -> uniqueColors.stream().collect(CollectorUtils.toEnumMap(Tuple::getKey, Tuple::getValue, Colors.class))
        );
        Map<Colors, String> colorsStringMap = uniqueColors.stream()
                .collect(CollectorUtils.toEnumMap(Tuple::getKey, Tuple::getValue, Colors.class));
        assertTrue(colorsStringMap instanceof EnumMap);
        assertEquals(3, colorsStringMap.size());
        assertEquals("00FF00", colorsStringMap.get(Colors.GREEN));
        assertEquals("FF0000", colorsStringMap.get(Colors.RED));
        assertEquals("0000FF", colorsStringMap.get(Colors.BLUE));
    }

    @Test
    void testToEnumMapCollectorWithCustomMergeFunction() {
        List<Tuple<Colors, String>> colorsWithDuplicates = List.of(
                new Tuple<>(Colors.GREEN, "00FF00"),
                new Tuple<>(Colors.GREEN, "00FA00"),
                new Tuple<>(Colors.GREEN, "001100"),
                new Tuple<>(Colors.RED, "FF0000"),
                new Tuple<>(Colors.BLUE, "0000FF")
        );

        assertDoesNotThrow(
                () -> colorsWithDuplicates.stream().collect(CollectorUtils.toEnumMap(Tuple::getKey, Tuple::getValue, (l, r) -> r, Colors.class))
        );
        Map<Colors, String> colorsStringMap = colorsWithDuplicates.stream()
                .sorted(Comparator.comparing(Tuple::getValue))
                .collect(CollectorUtils.toEnumMap(Tuple::getKey, Tuple::getValue, (l, r) -> r, Colors.class));
        assertTrue(colorsStringMap instanceof EnumMap);
        assertEquals(3, colorsStringMap.size());
        assertEquals("00FF00", colorsStringMap.get(Colors.GREEN));
        assertEquals("FF0000", colorsStringMap.get(Colors.RED));
        assertEquals("0000FF", colorsStringMap.get(Colors.BLUE));
    }
}
