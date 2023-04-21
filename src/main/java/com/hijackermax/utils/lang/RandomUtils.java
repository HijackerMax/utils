package com.hijackermax.utils.lang;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.stream.IntStream;

/**
 * Set of utility methods that can provide some random values
 */
public class RandomUtils {
    private static final char[] ALPHA_NUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] NUMERIC = "1234567890".toCharArray();
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private RandomUtils() {
    }

    /**
     * Provides random int value in range of 0...Integer.MAX_VALUE
     *
     * @return random int value in range of 0...Integer.MAX_VALUE
     * @since 0.0.4
     */
    public static int randomInt() {
        return SECURE_RANDOM.nextInt() & Integer.MAX_VALUE;
    }

    /**
     * Provides random int value in range of 0...boundary - 1
     *
     * @param boundary upper boundary of random values, exclusive
     * @return random int value in range of 0...boundary - 1
     * @since 0.0.4
     */
    public static int randomInt(int boundary) {
        return SECURE_RANDOM.nextInt(boundary);
    }

    /**
     * Provides random long value in range of 0...Long.MAX_VALUE
     *
     * @return random long value in range of 0...Long.MAX_VALUE
     * @since 0.0.4
     */
    public static long randomLong() {
        return SECURE_RANDOM.nextLong() & Long.MAX_VALUE;
    }

    /**
     * Provides random ASCII {@link String} of predefined length
     *
     * @param length output {@link String} length
     * @return random ASCII {@link String} of predefined length
     * @since 0.0.4
     */
    public static String randomString(int length) {
        return randomStringSequence(ALPHA_NUMERIC, length);
    }

    /**
     * Provides random numeric {@link String} of predefined length
     *
     * @param length output {@link String} length
     * @return random numeric {@link String} of predefined length
     * @since 0.0.4
     */
    public static String randomNumericString(int length) {
        return randomStringSequence(NUMERIC, length);
    }

    /**
     * Provides random byte array with predefined length
     *
     * @param length output array length
     * @return byte array of predefined length
     * @since 0.0.5
     */
    public static byte[] randomBytes(int length) {
        byte[] result = new byte[length];
        SECURE_RANDOM.nextBytes(result);
        return result;
    }

    /**
     * Provides random {@link String} of predefined length and predefined group of chars
     *
     * @param charSequence array that contains characters to choose randomly from
     * @param length       output {@link String} length
     * @return random ASCII {@link String} of predefined length
     * @since 0.0.6
     */
    public static String randomStringSequence(int length, char... charSequence) {
        return randomStringSequence(charSequence, length);
    }

    /**
     * Provides shuffled array of int range with provided boundaries
     *
     * @param lowerBound lower boundary of int range
     * @param upperBound upper boundary of int range
     * @return shuffled array of int range with provided boundaries
     * @since 0.0.4
     */
    public static int[] shuffledRange(int lowerBound, int upperBound) {
        int[] source = IntStream.range(lowerBound, upperBound)
                .toArray();
        for (int idx = source.length - 1; idx > 0; idx--) {
            int rnd = randomInt(idx + 1);
            if (rnd != idx) {
                source[rnd] ^= source[idx];
                source[idx] ^= source[rnd];
                source[rnd] ^= source[idx];
            }
        }
        return source;
    }

    /**
     * Creates shuffled version of source array, does not modify source
     *
     * @param source source array
     * @return shuffled array
     * @since 0.0.4
     */
    public static int[] shuffle(int[] source) {
        int[] result = new int[source.length];
        shuffleToArray(source, result);
        return result;
    }

    /**
     * Creates shuffled version of source array, does not modify source
     *
     * @param source source array
     * @return shuffled array
     * @since 0.0.4
     */
    public static long[] shuffle(long[] source) {
        long[] result = new long[source.length];
        shuffleToArray(source, result);
        return result;
    }

    /**
     * Creates shuffled version of source array, does not modify source
     *
     * @param source source array
     * @return shuffled array
     * @since 0.0.4
     */
    public static char[] shuffle(char[] source) {
        char[] result = new char[source.length];
        shuffleToArray(source, result);
        return result;
    }

    /**
     * Creates shuffled version of source array, does not modify source
     *
     * @param source source array
     * @return shuffled array
     * @since 0.0.4
     */
    public static byte[] shuffle(byte[] source) {
        byte[] result = new byte[source.length];
        shuffleToArray(source, result);
        return result;
    }

    /**
     * Creates shuffled version of source array, does not modify source
     *
     * @param source source array
     * @return shuffled array
     * @since 0.0.4
     */
    public static double[] shuffle(double[] source) {
        double[] result = new double[source.length];
        shuffleToArray(source, result);
        return result;
    }

    /**
     * Creates shuffled version of source array, does not modify source
     *
     * @param source source array
     * @return shuffled array
     * @since 0.0.4
     */
    public static float[] shuffle(float[] source) {
        float[] result = new float[source.length];
        shuffleToArray(source, result);
        return result;
    }

    /**
     * Creates shuffled version of source array, does not modify source
     *
     * @param source source array
     * @return shuffled array
     * @since 0.0.4
     */
    public static short[] shuffle(short[] source) {
        short[] result = new short[source.length];
        shuffleToArray(source, result);
        return result;
    }

    /**
     * Creates shuffled version of source array, does not modify source
     *
     * @param source source array
     * @return shuffled array
     * @since 0.0.4
     */
    public static String[] shuffle(String[] source) {
        String[] result = new String[source.length];
        shuffleToArray(source, result);
        return result;
    }

    private static void shuffleToArray(Object source, Object result) {
        for (int idx = 0; idx < Array.getLength(source); idx++) {
            int rnd = randomInt(idx + 1);
            if (idx != rnd) {
                Array.set(result, idx, Array.get(result, rnd));
            }
            Array.set(result, rnd, Array.get(source, idx));
        }
    }

    private static String randomStringSequence(char[] charSequence, int length) {
        return SECURE_RANDOM.ints(length, 0, charSequence.length)
                .mapToObj(idx -> charSequence[idx])
                .collect(CollectionUtils.toStringCollector());
    }
}
