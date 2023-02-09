package com.hijackermax;

import com.hijackermax.utils.lang.OptionalUtils;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OptionalUtilsTest {
    @Test
    void testOfFirst() {
        Optional<Integer> integerOptional = OptionalUtils.ofFirst(List.of(22, 3, 1, 2));
        assertTrue(integerOptional.isPresent());
        assertEquals(22, integerOptional.orElseThrow());
        assertTrue(OptionalUtils.ofFirst(null).isEmpty());
    }

    @Test
    void testOfInstance() {
        assertTrue(OptionalUtils.ofInstance(128, Integer.class).isPresent());
        assertFalse(OptionalUtils.ofInstance(128, Double.class).isPresent());
        assertEquals(128, OptionalUtils.ofInstance(128, Integer.class).orElseThrow());
        assertTrue(OptionalUtils.ofInstance(null, Integer.class).isEmpty());
    }

    @Test
    void testOfInstanceFunction() {
        assertTrue(OptionalUtils.ofInstance(Integer.class).apply(128).isPresent());
        assertFalse(OptionalUtils.ofInstance(Double.class).apply(128).isPresent());
        assertEquals(128, OptionalUtils.ofInstance(Integer.class).apply(128).orElseThrow());
    }

    @Test
    void testOfEmpty() {
        assertTrue(OptionalUtils.ofEmpty(null).isEmpty());
        assertTrue(OptionalUtils.ofEmpty("").isEmpty());
        assertTrue(OptionalUtils.ofEmpty("Test").isPresent());
    }

    @Test
    void testOfBlank() {
        assertTrue(OptionalUtils.ofBlank(null).isEmpty());
        assertTrue(OptionalUtils.ofBlank(" ").isEmpty());
        assertTrue(OptionalUtils.ofBlank("Test").isPresent());
    }
}
