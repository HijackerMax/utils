package com.hijackermax.utils;

import com.hijackermax.utils.entities.Single;
import com.hijackermax.utils.entities.Tuple;
import com.hijackermax.utils.lang.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class CollectionUtilsTest {
    @Test
    void testIsEmpty() {
        assertTrue(CollectionUtils.isEmpty((List<?>) null));
        assertTrue(CollectionUtils.isEmpty(Collections.emptyList()));
        assertFalse(CollectionUtils.isEmpty(Collections.singletonList(true)));
    }

    @Test
    void testIsEmptyMap() {
        assertTrue(CollectionUtils.isEmpty((Map<?, ?>) null));
        assertTrue(CollectionUtils.isEmpty(Collections.emptyMap()));
        assertFalse(CollectionUtils.isEmpty(Collections.singletonMap(false, false)));
    }

    @Test
    void testIsNotEmpty() {
        assertFalse(CollectionUtils.isNotEmpty((List<?>) null));
        assertFalse(CollectionUtils.isNotEmpty(Collections.emptyList()));
        assertTrue(CollectionUtils.isNotEmpty(Collections.singletonList(true)));
    }

    @Test
    void testIsNotEmptyMap() {
        assertFalse(CollectionUtils.isNotEmpty((Map<?, ?>) null));
        assertFalse(CollectionUtils.isNotEmpty(Collections.emptyMap()));
        assertTrue(CollectionUtils.isNotEmpty(Collections.singletonMap(false, false)));
    }

    @Test
    void testSafeStreamOfCollection() {
        assertEquals(0L, CollectionUtils.safeStreamOf((List<?>) null).count());
        assertEquals(0L, CollectionUtils.safeStreamOf(Collections.emptyList()).count());
        assertEquals(1L, CollectionUtils.safeStreamOf(Collections.singletonList(true)).count());
    }

    @Test
    void testSafeParallelStreamOfCollection() {
        assertEquals(0L, CollectionUtils.safeParallelStreamOf((List<?>) null).count());
        assertTrue(CollectionUtils.safeParallelStreamOf(Collections.singletonList(true)).isParallel());
    }

    @Test
    void testSafeStreamOfArray() {
        assertEquals(0L, CollectionUtils.safeStreamOf((Long[]) null).count());
        assertEquals(0L, CollectionUtils.safeStreamOf(new Long[]{}).count());
        assertEquals(1L, CollectionUtils.safeStreamOf(new Long[1]).count());
    }

    @Test
    void testSafeStreamOfMap() {
        assertEquals(0L, CollectionUtils.safeStreamOf((Map<?, ?>) null).count());
        assertEquals(0L, CollectionUtils.safeStreamOf(Collections.emptyMap()).count());
        assertEquals(1L, CollectionUtils.safeStreamOf(Map.of(1, true)).count());
    }

    @Test
    void testSafeFilteredStreamOf() {
        assertEquals(4L, CollectionUtils.safeFilteredStreamOf(Arrays.asList(0, 1, 2, 3, 4, 5, 6), v -> v < 4).count());
        assertEquals(0L, CollectionUtils.safeFilteredStreamOf(null, Objects::nonNull).count());
    }

    @Test
    void testFilter() {
        assertEquals(4L, CollectionUtils.filter(Arrays.asList(0, 1, 2, 3, 4, 5, 6), v -> v < 4).size());
        assertTrue(CollectionUtils.filter(null, Objects::nonNull).isEmpty());
    }

    @Test
    void testSafeForEachCollection() {
        Tuple<Integer, Boolean> integerBooleanTuple = new Tuple<>(-10, false);
        CollectionUtils.safeForEach(null, integerBooleanTuple::setPair);
        assertEquals(-10, integerBooleanTuple.getKey());
        assertEquals(false, integerBooleanTuple.getValue());
        CollectionUtils.safeForEach(Map.of(22, true), integerBooleanTuple::setPair);
        assertEquals(22, integerBooleanTuple.getKey());
        assertEquals(true, integerBooleanTuple.getValue());
    }

    @Test
    void testSafeForEachMap() {
        Single<Integer> singleInteger = new Single<>(-10);
        CollectionUtils.safeForEach(null, singleInteger::setValue);
        assertEquals(-10, singleInteger.getValue());
        CollectionUtils.safeForEach(Collections.singletonList(11), singleInteger::setValue);
        assertEquals(11, singleInteger.getValue());
    }

    @Test
    void testConvert() {
        List<String> mappedList = CollectionUtils.map(List.of(1, 2, 3, 4, 5), String::valueOf);
        assertEquals(5, mappedList.size());
        assertEquals("1", mappedList.get(0));
        assertEquals("5", mappedList.get(4));
        assertTrue(CollectionUtils.map(null, String::valueOf).isEmpty());
    }

    @Test
    void testConvertSorted() {
        List<Integer> mappedSorted = CollectionUtils.mapSorted(
                List.of("22", "1", "11", "7", "0", "3", "5", "4"),
                Integer::valueOf,
                Integer::compareTo
        );
        assertEquals(8, mappedSorted.size());
        assertEquals(0, mappedSorted.get(0));
        assertEquals(22, mappedSorted.get(7));
        assertTrue(CollectionUtils.mapSorted(null, String::valueOf, String::compareTo).isEmpty());
    }


    @Test
    void testMapConsumer() {
        List<String> mappedList = new ArrayList<>();
        CollectionUtils.map(List.of(1, 2, 3, 4, 5, 6, 11), String::valueOf, mappedList::addAll);
        assertEquals(7, mappedList.size());
        assertEquals("1", mappedList.get(0));
        assertEquals("11", mappedList.get(6));
        CollectionUtils.map(null, String::valueOf, mappedList::addAll);
        assertEquals(7, mappedList.size());
    }

    @Test
    void testFilterAndMap() {
        List<String> filteredAndMapped = CollectionUtils.filterAndMap(
                Arrays.asList(0, 1, 3, 2, 4, 5, 6, 9),
                v -> v < 4,
                String::valueOf
        );
        assertEquals(4, filteredAndMapped.size());
        assertEquals("0", filteredAndMapped.get(0));
        assertEquals("2", filteredAndMapped.get(3));
    }

    @Test
    void testMapFunction() {
        List<String> mappedList = CollectionUtils.map(String::valueOf).apply(List.of(1, 2, 3, 4, 5, 6, 11));
        assertEquals(7, mappedList.size());
        assertEquals("1", mappedList.get(0));
        assertEquals("11", mappedList.get(6));
    }

    @Test
    void testSafeSizeOf() {
        assertEquals(0, CollectionUtils.safeSize(null));
        assertEquals(0, CollectionUtils.safeSize(Collections.emptyList()));
        assertEquals(1, CollectionUtils.safeSize(Collections.singletonList(null)));
    }


    @Test
    void testSafeContainsAll() {
        assertTrue(CollectionUtils.safeContainsAll(null, null));
        assertFalse(CollectionUtils.safeContainsAll(Collections.emptyList(), null));
        assertFalse(CollectionUtils.safeContainsAll(null, Collections.emptyList()));
        assertTrue(CollectionUtils.safeContainsAll(Collections.emptyList(), Collections.emptyList()));
        assertTrue(CollectionUtils.safeContainsAll(List.of(1, 2, 3, 4, 5), List.of(5, 4, 3, 2, 1)));
        assertTrue(CollectionUtils.safeContainsAll(List.of("foo", "bar"), List.of("bar", "foo")));
        assertFalse(CollectionUtils.safeContainsAll(List.of(1, 2, 3, 4, 5), List.of(5, 4, 3, 2, 0)));
    }


    @Test
    void testUnion() {
        assertEquals(2, CollectionUtils.union(Collections.singletonList(1), Collections.singletonList(2)).size());
        assertEquals(1, CollectionUtils.union(Collections.singletonList(1), null).size());
        List<Integer> unionList = CollectionUtils.union(Collections.singletonList(2), List.of(1, 3, 4, 5, 5));
        assertEquals(6, unionList.size());
        assertTrue(unionList.containsAll(List.of(1, 2, 3, 4, 5)));
    }

    @Test
    void testDistinctUnion() {
        assertEquals(2, CollectionUtils.distinctUnion(Collections.singletonList(1), Collections.singletonList(2)).size());
        assertEquals(1, CollectionUtils.distinctUnion(Collections.singletonList(1), null).size());
        List<Integer> unionList = CollectionUtils.distinctUnion(Collections.singletonList(2), List.of(1, 3, 4, 5, 5));
        assertEquals(5, unionList.size());
        assertTrue(unionList.containsAll(List.of(1, 2, 3, 4, 5)));
    }

    @Test
    void testNestedUnion() {
        assertEquals(0, CollectionUtils.union(null).size());
        List<Integer> union = CollectionUtils.union(Set.of(List.of(1, 2, 4), List.of(3), Set.of(5, 21)));
        assertEquals(6, union.size());
        assertTrue(union.containsAll(List.of(1, 2, 3, 4, 5, 21)));
    }

    @Test
    void testNestedDistinctUnion() {
        assertEquals(0, CollectionUtils.distinctUnion(null).size());
        List<Integer> union = CollectionUtils.distinctUnion(List.of(List.of(1, 2, 4), List.of(3), Set.of(5, 21, 1, 3)));
        assertEquals(6, union.size());
        assertTrue(union.containsAll(List.of(1, 2, 3, 4, 5, 21)));
    }

    @Test
    void testToMap() {
        List<Integer> list = List.of(1, 1, 2, 4, 5, 6, 7, 7);
        Map<Integer, Integer> resultMap = CollectionUtils.toMap(list, Function.identity(), (a, b) -> a);
        assertEquals(6, resultMap.size());
        assertTrue(resultMap.containsKey(1));
        assertTrue(resultMap.containsKey(2));
        assertTrue(resultMap.containsKey(7));
        assertEquals(7, resultMap.get(7));
    }

    @Test
    void testToMapNoMerge() {
        List<Integer> list = List.of(1, 1, 2, 4, 5, 6, 7, 7);
        Map<Integer, Integer> resultMap = CollectionUtils.toMap(list, Function.identity());
        assertEquals(6, resultMap.size());
        assertTrue(resultMap.containsKey(1));
        assertTrue(resultMap.containsKey(2));
        assertTrue(resultMap.containsKey(7));
        assertEquals(7, resultMap.get(7));
    }

    @Test
    void testToMapValueExtractor() {
        List<String> list = List.of("Test", "Value", "Foo", "Bar");
        Map<String, Integer> resultMap = CollectionUtils.toMap(list, Function.identity(), String::length);
        assertEquals(4, resultMap.size());
        assertTrue(resultMap.containsKey("Test"));
        assertEquals(4, resultMap.get("Test"));
        assertTrue(resultMap.containsKey("Value"));
        assertEquals(5, resultMap.get("Value"));
    }

    @Test
    void testToMultiMap() {
        List<String> list = List.of("Test", "Value", "Foo", "Bar", "Hello", "World");
        Map<Integer, List<String>> resultMap = CollectionUtils.toMultiMap(list, String::length);
        assertEquals(3, resultMap.size());
        assertTrue(resultMap.containsKey(3));
        assertTrue(resultMap.get(3).containsAll(List.of("Foo", "Bar")));
        assertTrue(resultMap.containsKey(5));
        assertTrue(resultMap.get(5).containsAll(List.of("Value", "Hello", "World")));
    }

    @Test
    void testReverseMap() {
        Map<String, String> sourceMap = Map.of("Foo", "Bar", "Hello", "World");
        Map<String, String> resultMap = CollectionUtils.reverseMap(sourceMap);
        assertEquals(2, resultMap.size());
        assertTrue(resultMap.containsKey("Bar"));
        assertTrue(resultMap.containsKey("World"));
        assertFalse(resultMap.containsKey("Foo"));
        assertFalse(resultMap.containsKey("Hello"));
        assertTrue(CollectionUtils.reverseMap(null).isEmpty());
    }

    @Test
    void testReverseMapFiltered() {
        Map<String, String> sourceMap = Map.of(
                "Foo", "Bar",
                "Foo1", "Bar1",
                "Hello", "World",
                "Hello1", "World1"
        );
        Map<String, String> resultMap = CollectionUtils.reverseMap(sourceMap, v -> !v.endsWith("1"));
        assertEquals(2, resultMap.size());
        assertTrue(resultMap.containsKey("Bar"));
        assertTrue(resultMap.containsKey("World"));
        assertFalse(resultMap.containsKey("Bar1"));
        assertFalse(resultMap.containsKey("World1"));
        assertTrue(CollectionUtils.reverseMap(null).isEmpty());
    }

    @Test
    void testFirst() {
        assertEquals(3, CollectionUtils.first(List.of(3, 1, 2)));
        assertEquals(10, CollectionUtils.first(List.of(10, 2, 3)));
        assertNull(CollectionUtils.first(null));
    }

    @Test
    void testSubtract() {
        List<String> left = List.of("25", "1", "2", "3", "11");
        List<String> right = List.of("0", "3", "4", "5", "22");
        List<String> subtract = CollectionUtils.subtract(left, right);
        assertEquals(4, subtract.size());
        assertTrue(subtract.containsAll(List.of("1", "2", "11", "25")));

        assertTrue(CollectionUtils.subtract(left, null).containsAll(left));
        assertTrue(CollectionUtils.subtract(null, right).isEmpty());
    }

    @Test
    void testHaveSameElements() {
        assertFalse(CollectionUtils.haveSameElements(null, null));
        assertFalse(CollectionUtils.haveSameElements(null, List.of("25", "1", "2", "3", "11")));
        assertFalse(CollectionUtils.haveSameElements(List.of("25", "1", "2", "3", "11"), null));
        assertFalse(CollectionUtils.haveSameElements(List.of("25", "1", "2", "3", "11"), List.of("25", "1", "2", "3")));
        assertTrue(CollectionUtils.haveSameElements(List.of("25", "1", "2", "3", "11"), List.of("25", "1", "2", "3", "11")));
        assertTrue(CollectionUtils.haveSameElements(Collections.emptyList(), Collections.emptyList()));
    }

    @Test
    void testIntersection() {
        List<String> left = List.of("25", "1", "2", "3", "11", "22");
        List<String> right = List.of("0", "3", "4", "5", "22", "27");
        List<String> intersection = CollectionUtils.intersection(left, right);
        assertEquals(2, intersection.size());
        assertTrue(intersection.containsAll(List.of("3", "22")));

        assertTrue(CollectionUtils.intersection(left, null).isEmpty());
        assertTrue(CollectionUtils.intersection(null, right).isEmpty());
        assertTrue(CollectionUtils.intersection(null, null).isEmpty());
    }

    @Test
    void testGetDifferences() {
        List<String> left = List.of("25", "1", "2", "3", "11", "21");
        List<String> right = List.of("0", "3", "4", "5", "22", "77");
        CollectionUtils.getDifferences(left, right, (leftDiff, rightDiff) -> {
            assertEquals(5, leftDiff.size());
            assertEquals(5, rightDiff.size());

            assertTrue(left.containsAll(List.of("1", "2", "11", "25", "21")));
            assertTrue(rightDiff.containsAll(List.of("0", "4", "5", "22", "77")));
        });
    }

    @Test
    void testGroupingByWithNull() {
        List<Tuple<String, String>> input = List.of(
                new Tuple<>("Foo", "Bar"),
                new Tuple<>(null, "Test"),
                new Tuple<>("Hello", "World")
        );
        Map<String, List<Tuple<String, String>>> nullKeyMap = input.stream()
                .collect(CollectionUtils.groupingByWithNull(Tuple::getKey));

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
                .collect(CollectionUtils.groupingByWithNull(Tuple::getKey, Tuple::getValue));

        assertEquals(3, nullKeyMap.size());
        assertTrue(nullKeyMap.containsKey("Foo"));
        assertTrue(nullKeyMap.containsKey("Hello"));
        assertTrue(nullKeyMap.containsKey(null));
        assertEquals(List.of("Test"), nullKeyMap.get(null));
    }

    @Test
    void testSafeRemoveIf() {
        assertFalse(CollectionUtils.safeRemoveIf(null, Objects::nonNull));
        assertFalse(CollectionUtils.safeRemoveIf(new ArrayList<>(), Objects::nonNull));
        assertFalse(CollectionUtils.safeRemoveIf(new ArrayList<>(List.of(1, 2, 3, 4, 5)), v -> v > 5));
        ArrayList<Integer> integers = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        boolean isRemoved = CollectionUtils.safeRemoveIf(integers, v -> v < 5);
        assertTrue(isRemoved);
        assertEquals(1, integers.size());
        assertEquals(5, integers.get(0));
    }

    @Test
    void testSplit() {
        Map<Integer, Integer> sourceMap = Map.of(
                11, 32,
                7, 3,
                1, 2,
                21, 99
        );

        List<Integer> result = sourceMap.entrySet().stream()
                .map(CollectionUtils.split(Integer::sum))
                .sorted()
                .collect(Collectors.toList());

        assertEquals(4, result.size());
        assertEquals(3, result.get(0));
        assertEquals(120, result.get(3));
    }

    @Test
    void testSplitPredicate() {
        Map<Integer, Integer> sourceMap = Map.of(
                11, 32,
                7, 3,
                1, 2,
                21, 99
        );

        BiPredicate<Integer, Integer> isEvenSum = (k, v) ->
                (k + v) % 2 == 0;

        List<Integer> result = sourceMap.entrySet().stream()
                .filter(CollectionUtils.splitPredicate(isEvenSum))
                .map(CollectionUtils.split(Integer::sum))
                .sorted()
                .collect(Collectors.toList());

        assertEquals(2, result.size());
        assertEquals(10, result.get(0));
        assertEquals(120, result.get(1));
    }

    @Test
    void testKeyPredicate() {
        Map<Integer, Integer> sourceMap = Map.of(
                11, 32,
                2, 3,
                1, 2,
                32, 99
        );

        List<Integer> result = sourceMap.entrySet().stream()
                .filter(CollectionUtils.keyPredicate(k -> k % 2 == 0))
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(2, result.size());
        assertEquals(2, result.get(0));
        assertEquals(32, result.get(1));
    }

    @Test
    void testValuePredicate() {
        Map<Integer, Integer> sourceMap = Map.of(
                11, 24,
                2, 3,
                1, 2,
                32, 99
        );

        List<Integer> result = sourceMap.entrySet().stream()
                .filter(CollectionUtils.valuePredicate(v -> v % 2 == 0))
                .map(Map.Entry::getValue)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(2, result.size());
        assertEquals(2, result.get(0));
        assertEquals(24, result.get(1));
    }

    @Test
    void testToStringCollector() {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        String result = IntStream.range(0, chars.length)
                .mapToObj(idx -> chars[idx])
                .collect(CollectionUtils.toStringCollector());
        assertEquals(chars.length, result.length());
    }

    @Test
    void testSafeContains() {
        assertFalse(CollectionUtils.safeContains(null, null));
        assertFalse(CollectionUtils.safeContains(null, "Test"));
        assertFalse(CollectionUtils.safeContains(List.of(1), null));
        assertFalse(CollectionUtils.safeContains(List.of(1, 2, 3, 4, 5), 6));
        assertFalse(CollectionUtils.safeContains(List.of(1, 2, 3, 4, 5), "String"));
        assertTrue(CollectionUtils.safeContains(List.of(1, 2, 3, 4, 5), 2));
        assertTrue(CollectionUtils.safeContains(List.of("Foo", "Bar", "Test"), "Bar"));
    }

    @Test
    void testNotEmptyOrElseGet() {
        assertEquals(List.of(1, 2, 3), CollectionUtils.notEmptyOrElseGet(List.of(1, 2, 3), () -> List.of(4, 5, 6)));
        assertEquals(List.of(4, 5, 6), CollectionUtils.notEmptyOrElseGet(null, () -> List.of(4, 5, 6)));
        assertEquals(List.of(4, 5, 6), CollectionUtils.notEmptyOrElseGet(List.of(), () -> List.of(4, 5, 6)));
        assertThrows(NullPointerException.class, () -> CollectionUtils.notEmptyOrElseGet(null, null));
    }

    @Test
    void testSafeContainsAnyArg() {
        assertFalse(CollectionUtils.safeContainsAnyArg(null, 1, 2, 3));
        assertFalse(CollectionUtils.safeContainsAnyArg(List.of(), 1, 2, 3));
        assertFalse(CollectionUtils.safeContainsAnyArg(List.of()));
        assertFalse(CollectionUtils.safeContainsAnyArg(List.of(1, 2, 3, 4, 5), 0));
        assertTrue(CollectionUtils.safeContainsAnyArg(List.of(1, 2, 3, 4, 5), 2));
    }

    @Test
    void testSafeContainsAllArg() {
        assertFalse(CollectionUtils.safeContainsAllArg(null, 1, 2, 3));
        assertFalse(CollectionUtils.safeContainsAllArg(List.of(), 1, 2, 3));
        assertFalse(CollectionUtils.safeContainsAllArg(List.of(1, 2, 3, 4, 5), 1, 2, 3, 4, 6));
        assertTrue(CollectionUtils.safeContainsAllArg(List.of(1, 2, 3, 4, 5), 1, 2, 3, 4));
        assertTrue(CollectionUtils.safeContainsAllArg(List.of()));
        assertTrue(CollectionUtils.safeContainsAllArg(List.of(1, 2, 3, 4, 5), 1, 2, 3, 4, 5));
    }

    @Test
    void testSafeNotContainsArg() {
        assertFalse(CollectionUtils.safeNotContainsArg(null, 1, 2, 3));
        assertFalse(CollectionUtils.safeNotContainsArg(List.of(1, 2, 3, 4, 5), 1, 2));
        assertFalse(CollectionUtils.safeNotContainsArg(List.of(1, 2, 3, 4, 5), 1, 2, 3, 4, 5));
        assertTrue(CollectionUtils.safeNotContainsArg(List.of(1, 2, 3, 4, 5), 7, 8, 9, 10));
        assertTrue(CollectionUtils.safeNotContainsArg(List.of()));
        assertTrue(CollectionUtils.safeNotContainsArg(List.of(), 1, 2, 3));
    }

    @Test
    void testSafeContainsAny() {
        assertFalse(CollectionUtils.safeContainsAny(null, null));
        assertFalse(CollectionUtils.safeContainsAny(List.of(1), null));
        assertFalse(CollectionUtils.safeContainsAny(null, List.of(1)));
        assertFalse(CollectionUtils.safeContainsAny(List.of(), List.of(1, 2, 3)));
        assertFalse(CollectionUtils.safeContainsAny(List.of(), List.of()));
        assertFalse(CollectionUtils.safeContainsAny(List.of(1, 2, 3, 4, 5), List.of(0, 10)));
        assertTrue(CollectionUtils.safeContainsAny(List.of(1, 2, 3, 4, 5), List.of(2, 10)));
    }
}
