package com.hijackermax.utils.lang;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Set of utility methods that can help with math operations
 */
public final class MathUtils {
    private MathUtils() {
    }

    /**
     * Calculates cylinder volume using provided diameter and height
     *
     * @param diameter cylinder diameter
     * @param height   cylinder height
     * @return cylinder volume
     * @since 0.0.4
     */
    public static double cylinderVolume(double diameter, double height) {
        return height * circleArea(diameter);
    }

    /**
     * Calculates cone volume using provided diameter and height
     *
     * @param diameter cone diameter
     * @param height   cone height
     * @return cone volume
     * @since 0.0.4
     */
    public static double coneVolume(double diameter, double height) {
        return cylinderVolume(diameter, height) / 3D;
    }

    /**
     * Calculates circle area using provided diameter
     *
     * @param diameter circle diameter
     * @return circle area
     * @since 0.0.4
     */
    public static double circleArea(double diameter) {
        return Math.pow(diameter / 2, 2) * Math.PI;
    }

    /**
     * Subtracts one int from another int as per the - operator
     *
     * @param a the minuend operand
     * @param b the subtrahend operand
     * @return difference between input values
     * @since 0.0.4
     */
    public static int subtract(int a, int b) {
        return a - b;
    }

    /**
     * Subtracts one long from another long as per the - operator
     *
     * @param a the minuend operand
     * @param b the subtrahend operand
     * @return difference between input values
     * @since 0.0.4
     */
    public static long subtract(long a, long b) {
        return a - b;
    }

    /**
     * Subtracts one double from another double as per the - operator
     *
     * @param a the minuend operand
     * @param b the subtrahend operand
     * @return difference between input values
     * @since 0.0.4
     */
    public static double subtract(double a, double b) {
        return a - b;
    }

    /**
     * Subtracts one float from another float as per the - operator
     *
     * @param a the minuend operand
     * @param b the subtrahend operand
     * @return difference between input values
     * @since 0.0.4
     */
    public static float subtract(float a, float b) {
        return a - b;
    }

    /**
     * Subtracts one int from another int as per the - operator and returns absolute value of difference
     *
     * @param a the minuend operand
     * @param b the subtrahend operand
     * @return absolute difference between input values
     * @since 0.0.4
     */
    public static int absSubtract(int a, int b) {
        return Math.abs(a - b);
    }

    /**
     * Subtracts one long from another long as per the - operator and returns absolute value of difference
     *
     * @param a the minuend operand
     * @param b the subtrahend operand
     * @return absolute difference between input values
     * @since 0.0.4
     */
    public static long absSubtract(long a, long b) {
        return Math.abs(a - b);
    }

    /**
     * Subtracts one double from another double as per the - operator and returns absolute value of difference
     *
     * @param a the minuend operand
     * @param b the subtrahend operand
     * @return absolute difference between input values
     * @since 0.0.4
     */
    public static double absSubtract(double a, double b) {
        return Math.abs(a - b);
    }

    /**
     * Subtracts one float from another float as per the - operator and returns absolute value of difference
     *
     * @param a the minuend operand
     * @param b the subtrahend operand
     * @return absolute difference between input values
     * @since 0.0.4
     */
    public static float absSubtract(float a, float b) {
        return Math.abs(a - b);
    }

    /**
     * Multiplies one int by another int as per the * operator
     *
     * @param a the multiplier operand
     * @param b the multiplicand operand
     * @return product of multiplication
     * @since 0.0.4
     */
    public static int multiply(int a, int b) {
        return a * b;
    }

    /**
     * Multiplies one long by another long as per the * operator
     *
     * @param a the multiplier operand
     * @param b the multiplicand operand
     * @return product of multiplication
     * @since 0.0.4
     */
    public static long multiply(long a, long b) {
        return a * b;
    }

    /**
     * Multiplies one double by another double as per the * operator
     *
     * @param a the multiplier operand
     * @param b the multiplicand operand
     * @return product of multiplication
     * @since 0.0.4
     */
    public static double multiply(double a, double b) {
        return a * b;
    }

    /**
     * Multiplies one float by another float as per the * operator
     *
     * @param a the multiplier operand
     * @param b the multiplicand operand
     * @return product of multiplication
     * @since 0.0.4
     */
    public static float multiply(float a, float b) {
        return a * b;
    }

    /**
     * Multiplies one int by another int as per the * operator and returns absolute value of multiplication product
     *
     * @param a the multiplier operand
     * @param b the multiplicand operand
     * @return absolute product of multiplication
     * @since 0.0.4
     */
    public static int absMultiply(int a, int b) {
        return Math.abs(a * b);
    }

    /**
     * Multiplies one long by another long as per the * operator and returns absolute value of multiplication product
     *
     * @param a the multiplier operand
     * @param b the multiplicand operand
     * @return absolute product of multiplication
     * @since 0.0.4
     */
    public static long absMultiply(long a, long b) {
        return Math.abs(a * b);
    }

    /**
     * Multiplies one double by another double as per the * operator and returns absolute value of multiplication product
     *
     * @param a the multiplier operand
     * @param b the multiplicand operand
     * @return absolute product of multiplication
     * @since 0.0.4
     */
    public static double absMultiply(double a, double b) {
        return Math.abs(a * b);
    }

    /**
     * Multiplies one float by another float as per the * operator and returns absolute value of multiplication product
     *
     * @param a the multiplier operand
     * @param b the multiplicand operand
     * @return absolute product of multiplication
     * @since 0.0.4
     */
    public static float absMultiply(float a, float b) {
        return Math.abs(a * b);
    }

    /**
     * Provides {@link UnaryOperator} which subtracts provided value from {@link Function} argument
     *
     * @param b subtrahend operand
     * @return {@link UnaryOperator} which subtracts provided value from {@link Function} argument
     * @since 0.0.7
     */
    public static UnaryOperator<Integer> subtract(int b) {
        return a -> subtract(a, b);
    }

    /**
     * Provides {@link UnaryOperator} which subtracts provided value from {@link Function} argument
     *
     * @param b subtrahend operand
     * @return {@link UnaryOperator} which subtracts provided value from {@link Function} argument
     * @since 0.0.7
     */
    public static UnaryOperator<Long> subtract(long b) {
        return a -> subtract(a, b);
    }

    /**
     * Provides {@link UnaryOperator} which subtracts provided value from {@link Function} argument
     *
     * @param b subtrahend operand
     * @return {@link UnaryOperator} which subtracts provided value from {@link Function} argument
     * @since 0.0.7
     */
    public static UnaryOperator<Float> subtract(float b) {
        return a -> subtract(a, b);
    }

    /**
     * Provides {@link UnaryOperator} which subtracts provided value from {@link Function} argument
     *
     * @param b subtrahend operand
     * @return {@link UnaryOperator} which subtracts provided value from {@link Function} argument
     * @since 0.0.7
     */
    public static UnaryOperator<Double> subtract(double b) {
        return a -> subtract(a, b);
    }

    /**
     * Provides {@link UnaryOperator} which adds provided value to {@link Function} argument
     *
     * @param b addend
     * @return {@link UnaryOperator} which adds provided value to {@link Function} argument
     * @since 0.0.7
     */
    public static UnaryOperator<Integer> add(int b) {
        return a -> Integer.sum(a, b);
    }

    /**
     * Provides {@link UnaryOperator} which adds provided value to {@link Function} argument
     *
     * @param b addend
     * @return {@link UnaryOperator} which adds provided value to {@link Function} argument
     * @since 0.0.7
     */
    public static UnaryOperator<Long> add(long b) {
        return a -> Long.sum(a, b);
    }

    /**
     * Provides {@link UnaryOperator} which adds provided value to {@link Function} argument
     *
     * @param b addend
     * @return {@link UnaryOperator} which adds provided value to {@link Function} argument
     * @since 0.0.7
     */
    public static UnaryOperator<Float> add(float b) {
        return a -> Float.sum(a, b);
    }

    /**
     * Provides {@link UnaryOperator} which adds provided value to {@link Function} argument
     *
     * @param b addend
     * @return {@link UnaryOperator} which adds provided value to {@link Function} argument
     * @since 0.0.7
     */
    public static UnaryOperator<Double> add(double b) {
        return a -> Double.sum(a, b);
    }

    /**
     * Provides {@link UnaryOperator} which multiplies provided value by {@link Function} argument as per the * operator
     *
     * @param b multiplicand
     * @return {@link UnaryOperator} which multiplies provided value to {@link Function} argument
     * @since 0.0.8
     */
    public static UnaryOperator<Integer> multiply(int b) {
        return a -> a * b;
    }

    /**
     * Provides {@link UnaryOperator} which multiplies provided value by {@link Function} argument as per the * operator
     *
     * @param b multiplicand
     * @return {@link UnaryOperator} which multiplies provided value to {@link Function} argument
     * @since 0.0.8
     */
    public static UnaryOperator<Long> multiply(long b) {
        return a -> a * b;
    }

    /**
     * Provides {@link UnaryOperator} which multiplies provided value by {@link Function} argument as per the * operator
     *
     * @param b multiplicand
     * @return {@link UnaryOperator} which multiplies provided value to {@link Function} argument
     * @since 0.0.8
     */
    public static UnaryOperator<Float> multiply(float b) {
        return a -> a * b;
    }

    /**
     * Provides {@link UnaryOperator} which multiplies provided value by {@link Function} argument as per the * operator
     *
     * @param b multiplicand
     * @return {@link UnaryOperator} which multiplies provided value to {@link Function} argument
     * @since 0.0.8
     */
    public static UnaryOperator<Double> multiply(double b) {
        return a -> a * b;
    }
}
