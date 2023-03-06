package com.hijackermax.utils;

import com.hijackermax.utils.lang.MathUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MathUtilsTest {
    @Test
    void testConeVolume() {
        assertEquals(1047.19, MathUtils.coneVolume(20, 10), 0.01);
        assertEquals(1767.15, MathUtils.coneVolume(15, 30), 0.01);
    }

    @Test
    void testCylinderVolume() {
        assertEquals(3141.59, MathUtils.cylinderVolume(20, 10), 0.01);
        assertEquals(5301.43, MathUtils.cylinderVolume(15, 30), 0.01);
    }

    @Test
    void testCircleArea() {
        assertEquals(314.15, MathUtils.circleArea(20), 0.01);
        assertEquals(176.71, MathUtils.circleArea(15), 0.01);
    }

    @Test
    void testSubtractLong() {
        assertEquals(-10L, MathUtils.subtract(10L, 20L));
        assertEquals(0L, MathUtils.subtract(0L, 0L));
        assertEquals(20L, MathUtils.subtract(10L, -10L));
    }

    @Test
    void testSubtractInt() {
        assertEquals(-10, MathUtils.subtract(10, 20));
        assertEquals(0, MathUtils.subtract(0, 0));
        assertEquals(20, MathUtils.subtract(10, -10));
    }

    @Test
    void testSubtractFloat() {
        assertEquals(-10F, MathUtils.subtract(10F, 20F));
        assertEquals(0F, MathUtils.subtract(0F, 0F));
        assertEquals(20F, MathUtils.subtract(10F, -10F));
    }

    @Test
    void testSubtractDouble() {
        assertEquals(-10D, MathUtils.subtract(10D, 20D));
        assertEquals(0D, MathUtils.subtract(0D, 0D));
        assertEquals(20D, MathUtils.subtract(10D, -10D));
    }

    @Test
    void testAbsSubtractLong() {
        assertEquals(10L, MathUtils.absSubtract(10L, 20L));
        assertEquals(0L, MathUtils.absSubtract(0L, -0L));
        assertEquals(20L, MathUtils.absSubtract(10L, -10L));
    }

    @Test
    void testAbsSubtractInt() {
        assertEquals(10, MathUtils.absSubtract(10, 20));
        assertEquals(0, MathUtils.absSubtract(0, -0));
        assertEquals(20, MathUtils.absSubtract(10, -10));
    }

    @Test
    void testAbsSubtractFloat() {
        assertEquals(10F, MathUtils.absSubtract(10F, 20F));
        assertEquals(0F, MathUtils.absSubtract(0F, -0F));
        assertEquals(20F, MathUtils.absSubtract(10F, -10F));
    }

    @Test
    void testAbsSubtractDouble() {
        assertEquals(10D, MathUtils.absSubtract(10D, 20D));
        assertEquals(0D, MathUtils.absSubtract(0D, -0D));
        assertEquals(20D, MathUtils.absSubtract(10D, -10D));
    }

    @Test
    void testMultiplyLong() {
        assertEquals(200L, MathUtils.multiply(10L, 20L));
        assertEquals(0L, MathUtils.multiply(20L, 0L));
        assertEquals(-100L, MathUtils.multiply(10L, -10L));
    }

    @Test
    void testMultiplyInt() {
        assertEquals(200, MathUtils.multiply(10, 20));
        assertEquals(0, MathUtils.multiply(20, 0));
        assertEquals(-100, MathUtils.multiply(10, -10));
    }

    @Test
    void testMultiplyFloat() {
        assertEquals(200F, MathUtils.multiply(10F, 20F));
        assertEquals(0F, MathUtils.multiply(20F, 0F));
        assertEquals(-100F, MathUtils.multiply(10F, -10F));
    }

    @Test
    void testMultiplyDouble() {
        assertEquals(200D, MathUtils.multiply(10D, 20D));
        assertEquals(0D, MathUtils.multiply(20D, 0D));
        assertEquals(-100D, MathUtils.multiply(10D, -10D));
    }

    @Test
    void testAbsMultiplyLong() {
        assertEquals(200L, MathUtils.absMultiply(10L, 20L));
        assertEquals(0L, MathUtils.absMultiply(20L, 0L));
        assertEquals(100L, MathUtils.absMultiply(10L, -10L));
    }

    @Test
    void testAbsMultiplyInt() {
        assertEquals(200, MathUtils.absMultiply(10, 20));
        assertEquals(0, MathUtils.absMultiply(20, 0));
        assertEquals(100, MathUtils.absMultiply(10, -10));
    }

    @Test
    void testAbsMultiplyFloat() {
        assertEquals(200F, MathUtils.absMultiply(10F, 20F));
        assertEquals(0F, MathUtils.absMultiply(20F, -0F));
        assertEquals(100F, MathUtils.absMultiply(10F, -10F));
    }

    @Test
    void testAbsMultiplyDouble() {
        assertEquals(200D, MathUtils.absMultiply(10D, 20D));
        assertEquals(0D, MathUtils.absMultiply(20D, -0D));
        assertEquals(100D, MathUtils.absMultiply(10D, -10D));
    }
}
