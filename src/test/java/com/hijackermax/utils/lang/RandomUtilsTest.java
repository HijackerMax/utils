package com.hijackermax.utils.lang;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomUtilsTest {
    @Test
    void testRandomIntBounded() {
        int iterationsCount = 2000;
        int boundary = iterationsCount * 2;
        List<Integer> randomIntegers = IntStream.range(0, iterationsCount)
                .mapToObj(idx -> RandomUtils.randomInt(boundary))
                .distinct()
                .collect(Collectors.toList());
        assertTrue(randomIntegers.size() > iterationsCount / 10);
        assertTrue(randomIntegers.stream().allMatch(v -> v >= 0 && v < boundary));
    }

    @Test
    void testRandomInt() {
        int iterationsCount = 2000;
        List<Integer> randomIntegers = IntStream.range(0, iterationsCount)
                .mapToObj(idx -> RandomUtils.randomInt())
                .distinct()
                .collect(Collectors.toList());
        assertTrue(randomIntegers.size() > iterationsCount / 2);
        assertTrue(randomIntegers.stream().allMatch(v -> v >= 0));
    }

    @Test
    void testRandomLong() {
        int iterationsCount = 2000;
        List<Long> randomLongs = IntStream.range(0, iterationsCount)
                .mapToObj(idx -> RandomUtils.randomLong())
                .distinct()
                .collect(Collectors.toList());
        assertTrue(randomLongs.size() > iterationsCount / 2);
        assertTrue(randomLongs.stream().allMatch(v -> v >= 0));
    }

    @Test
    void testRandomString() {
        int stringLength = 32;

        String randomString = RandomUtils.randomString(stringLength);
        assertEquals(stringLength, randomString.length());

        int iterationsCount = 32;
        List<String> randomStrings = IntStream.range(0, iterationsCount)
                .mapToObj(idx -> RandomUtils.randomString(stringLength))
                .distinct()
                .collect(Collectors.toList());
        assertEquals(iterationsCount, randomStrings.size());
        assertTrue(randomStrings.stream().map(String::length).allMatch(v -> stringLength == v));
    }

    @Test
    void testRandomNumericString() {
        int stringLength = 32;

        String randomString = RandomUtils.randomNumericString(stringLength);
        assertEquals(stringLength, randomString.length());

        int iterationsCount = 32;
        List<String> randomStrings = IntStream.range(0, iterationsCount)
                .mapToObj(idx -> RandomUtils.randomNumericString(stringLength))
                .distinct()
                .collect(Collectors.toList());
        assertEquals(iterationsCount, randomStrings.size());
        assertTrue(randomStrings.stream().map(String::length).allMatch(v -> stringLength == v));
    }

    @Test
    void testRandomByteArray() {
        int size = 32;
        assertEquals(size, RandomUtils.randomBytes(size).length);
    }

    @Test
    void testShuffleIntArray() {
        int[] source = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0xAA};
        Arrays.sort(source);
        int[] shuffled = RandomUtils.shuffle(source);
        assertFalse(Arrays.equals(source, shuffled));
        Arrays.sort(shuffled);
        assertArrayEquals(source, shuffled);
    }

    @Test
    void testShuffleLongArray() {
        long[] source = new long[]{1, 2, 3, 4, 5, 6, 7, 8, 0xBEEF};
        Arrays.sort(source);
        long[] shuffled = RandomUtils.shuffle(source);
        assertFalse(Arrays.equals(source, shuffled));
        Arrays.sort(shuffled);
        assertArrayEquals(source, shuffled);
    }

    @Test
    void testShuffleShortArray() {
        short[] source = new short[]{1, 2, 3, 4, 5, 6, 7, 8, 0xFF};
        Arrays.sort(source);
        short[] shuffled = RandomUtils.shuffle(source);
        assertFalse(Arrays.equals(source, shuffled));
        Arrays.sort(shuffled);
        assertArrayEquals(source, shuffled);
    }

    @Test
    void testShuffleByteArray() {
        byte[] source = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Arrays.sort(source);
        byte[] shuffled = RandomUtils.shuffle(source);
        assertFalse(Arrays.equals(source, shuffled));
        Arrays.sort(shuffled);
        assertArrayEquals(source, shuffled);
    }

    @Test
    void testShuffleCharArray() {
        char[] source = new char[]{'a', 'b', 'c', 'd', 'e', 'w', 'q'};
        Arrays.sort(source);
        char[] shuffled = RandomUtils.shuffle(source);
        assertFalse(Arrays.equals(source, shuffled));
        Arrays.sort(shuffled);
        assertArrayEquals(source, shuffled);
    }

    @Test
    void testShuffleDoubleArray() {
        double[] source = new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10D};
        Arrays.sort(source);
        double[] shuffled = RandomUtils.shuffle(source);
        assertFalse(Arrays.equals(source, shuffled));
        Arrays.sort(shuffled);
        assertArrayEquals(source, shuffled);
    }

    @Test
    void testShuffleFloatArray() {
        float[] source = new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10F};
        Arrays.sort(source);
        float[] shuffled = RandomUtils.shuffle(source);
        assertFalse(Arrays.equals(source, shuffled));
        Arrays.sort(shuffled);
        assertArrayEquals(source, shuffled);
    }

    @Test
    void testShuffleStringArray() {
        String[] source = new String[]{"Hello", "Foo", "Bar", "World", "1", "ABCD"};
        Arrays.sort(source);
        String[] shuffled = RandomUtils.shuffle(source);
        assertFalse(Arrays.equals(source, shuffled));
        Arrays.sort(shuffled);
        assertArrayEquals(source, shuffled);
    }

    @Test
    void testShuffledRange() {
        int upperBound = 100;
        int lowerBound = 0;
        int[] referenceArray = IntStream.range(lowerBound, upperBound).toArray();
        int[] shuffled = RandomUtils.shuffledRange(lowerBound, upperBound);
        assertFalse(Arrays.equals(referenceArray, shuffled));
        Arrays.sort(shuffled);
        assertArrayEquals(referenceArray, shuffled);
    }

    @Test
    void testRandomStringSequence() {
        int stringLength = 32;
        char[] chars = "ABCDEFGHIJK0123456789".toCharArray();
        String randomString = RandomUtils.randomStringSequence(stringLength, chars);
        assertEquals(stringLength, randomString.length());
        int encounters = 0;
        for (char c : chars) {
            for (char r : randomString.toCharArray()) {
                if (r == c) {
                    encounters++;
                    break;
                }
            }
        }
        assertTrue(encounters > 0);
    }
}
