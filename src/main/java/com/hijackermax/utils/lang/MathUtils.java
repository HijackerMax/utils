package com.hijackermax.utils.lang;

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
}
