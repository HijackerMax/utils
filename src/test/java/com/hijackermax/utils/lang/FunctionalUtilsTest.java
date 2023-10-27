package com.hijackermax.utils.lang;

import com.hijackermax.utils.entities.Single;
import com.hijackermax.utils.entities.Tuple;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FunctionalUtilsTest {
    @Test
    void testFailSafeStrategyRunnable() {
        String testValue = "FooBar";
        Runnable throwingRunnable = () -> {
            throw new NullPointerException(testValue);
        };
        Runnable emptyRunnable = () -> {
        };
        Single<Exception> exceptionHolder = new Single<>();
        assertDoesNotThrow(() -> FunctionalUtils.failSafeStrategy(throwingRunnable, exceptionHolder::setValue));
        assertTrue(exceptionHolder.containsValue());
        Exception exception = exceptionHolder.getValue();
        assertInstanceOf(NullPointerException.class, exception);
        assertEquals(testValue, exception.getMessage());
        exceptionHolder.setValue(null);
        assertDoesNotThrow(() -> FunctionalUtils.failSafeStrategy(emptyRunnable, exceptionHolder::setValue));
        assertFalse(exceptionHolder.containsValue());
    }

    @Test
    void testFailSafeStrategyCallable() {
        String testValue = "FooBar";
        String fallbackValue = "test";
        Callable<String> throwingCallable = () -> {
            throw new NullPointerException(testValue);
        };
        Callable<String> normalCallable = () -> testValue;
        Supplier<String> fallbackCallable = () -> fallbackValue;
        Tuple<String, Exception> exceptionHolder = new Tuple<>();
        assertDoesNotThrow(() -> exceptionHolder.setKey(FunctionalUtils.failSafeStrategy(throwingCallable, fallbackCallable, exceptionHolder::setValue)));
        assertTrue(exceptionHolder.containsValue());
        assertTrue(exceptionHolder.containsKey());
        Exception exception = exceptionHolder.getValue();
        assertInstanceOf(NullPointerException.class, exception);
        assertEquals(testValue, exception.getMessage());
        assertEquals(fallbackValue, exceptionHolder.getKey());
        exceptionHolder.setPair(null, null);
        assertDoesNotThrow(() -> exceptionHolder.setKey(FunctionalUtils.failSafeStrategy(normalCallable, fallbackCallable, exceptionHolder::setValue)));
        assertFalse(exceptionHolder.containsValue());
        assertTrue(exceptionHolder.containsKey());
        assertEquals(testValue, exceptionHolder.getKey());
    }

    @Test
    void testFailSafeStrategyConsumer() {
        String testValue = "FooBar";
        Consumer<String> throwingConsumer = v -> {
            throw new NullPointerException(v);
        };
        Consumer<String> emptyConsumer = v -> {
        };
        Tuple<String, Exception> exceptionHolder = new Tuple<>();
        assertDoesNotThrow(() -> FunctionalUtils.failSafeStrategy(throwingConsumer, exceptionHolder::setPair).accept(testValue));
        assertTrue(exceptionHolder.containsValue());
        assertTrue(exceptionHolder.containsKey());
        Exception exception = exceptionHolder.getValue();
        assertInstanceOf(NullPointerException.class, exception);
        assertEquals(exceptionHolder.getKey(), exception.getMessage());
        exceptionHolder.setPair(null, null);
        assertDoesNotThrow(() -> FunctionalUtils.failSafeStrategy(emptyConsumer, exceptionHolder::setPair).accept(testValue));
        assertFalse(exceptionHolder.containsValue());
        assertFalse(exceptionHolder.containsKey());
    }

    @Test
    void testFailSafeStrategyBiConsumer() {
        String leftTestValue = "FooBar";
        String rightTestValue = "BarFoo";
        BiConsumer<String, String> throwingConsumer = (l, r) -> {
            throw new NullPointerException(l);
        };
        BiConsumer<String, String> emptyConsumer = FunctionalUtils.emptyBiConsumer();
        Tuple<Tuple<String, String>, Exception> exceptionHolder = new Tuple<>();
        assertDoesNotThrow(() -> FunctionalUtils.failSafeStrategy(throwingConsumer, exceptionHolder::setPair).accept(leftTestValue, rightTestValue));
        assertTrue(exceptionHolder.containsValue());
        assertTrue(exceptionHolder.containsKey());
        Exception exception = exceptionHolder.getValue();
        assertInstanceOf(NullPointerException.class, exception);
        assertEquals(exceptionHolder.getLeft().getLeft(), exception.getMessage());
        exceptionHolder.setPair(null, null);
        assertDoesNotThrow(() -> FunctionalUtils.failSafeStrategy(emptyConsumer, exceptionHolder::setPair).accept(leftTestValue, rightTestValue));
        assertFalse(exceptionHolder.containsValue());
        assertFalse(exceptionHolder.containsKey());
    }

    @Test
    void testMappedPredicate() {
        List<Single<String>> singlesList = List.of(
                Single.of("Foo@"),
                Single.of("Bar@"),
                Single.of("Test$")
        );

        List<String> filteredList = singlesList.stream()
                .filter(FunctionalUtils.mappedPredicate(Single::getValue, StringUtils.endsWith("@")))
                .map(Single::getValue)
                .collect(Collectors.toList());
        assertEquals(2, filteredList.size());
        assertEquals("Foo@", filteredList.get(0));
        assertEquals("Bar@", filteredList.get(1));
    }

    @Test
    void testFailSafeStrategyFunction() {
        String sourceValue = "FooBar";
        String resultValue = "BarFoo";
        Function<String, String> throwingFunction = v -> {
            throw new NullPointerException(v);
        };
        Function<String, String> emptyFunction = Function.identity();
        Tuple<String, Exception> exceptionHolder = new Tuple<>();
        assertDoesNotThrow(() -> FunctionalUtils.failSafeStrategy(throwingFunction, () -> resultValue, exceptionHolder).apply(sourceValue));
        assertTrue(exceptionHolder.containsValue());
        assertTrue(exceptionHolder.containsKey());
        Exception exception = exceptionHolder.getValue();
        assertInstanceOf(NullPointerException.class, exception);
        assertEquals(exceptionHolder.getLeft(), exception.getMessage());
        assertEquals(resultValue, FunctionalUtils.failSafeStrategy(throwingFunction, () -> resultValue, exceptionHolder).apply(sourceValue));
        exceptionHolder.setPair(null, null);
        assertDoesNotThrow(() -> FunctionalUtils.failSafeStrategy(emptyFunction, () -> resultValue, exceptionHolder).apply(sourceValue));
        assertFalse(exceptionHolder.containsValue());
        assertFalse(exceptionHolder.containsKey());
        assertEquals(sourceValue, FunctionalUtils.failSafeStrategy(emptyFunction, () -> resultValue, exceptionHolder).apply(sourceValue));
    }
}
