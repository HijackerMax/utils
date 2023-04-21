package com.hijackermax.utils;

import com.hijackermax.utils.entities.Single;
import com.hijackermax.utils.entities.Tuple;
import com.hijackermax.utils.lang.FunctionalUtils;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalUtilsTest {
    @Test
    void testFailSafeStrategyRunnable() {
        var testValue = "FooBar";
        Runnable throwingRunnable = () -> {
            throw new NullPointerException(testValue);
        };
        Runnable emptyRunnable = () -> {
        };
        Single<Exception> exceptionHolder = new Single<>();
        assertDoesNotThrow(() -> FunctionalUtils.failSafeStrategy(throwingRunnable, exceptionHolder::setValue));
        assertTrue(exceptionHolder.containsValue());
        Exception exception = exceptionHolder.getValue();
        assertTrue(exception instanceof NullPointerException);
        assertEquals(testValue, exception.getMessage());
        exceptionHolder.setValue(null);
        assertDoesNotThrow(() -> FunctionalUtils.failSafeStrategy(emptyRunnable, exceptionHolder::setValue));
        assertFalse(exceptionHolder.containsValue());
    }

    @Test
    void testFailSafeStrategyCallable() {
        var testValue = "FooBar";
        var fallbackValue = "test";
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
        assertTrue(exception instanceof NullPointerException);
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
        var testValue = "FooBar";
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
        assertTrue(exception instanceof NullPointerException);
        assertEquals(exceptionHolder.getKey(), exception.getMessage());
        exceptionHolder.setPair(null, null);
        assertDoesNotThrow(() -> FunctionalUtils.failSafeStrategy(emptyConsumer, exceptionHolder::setPair).accept(testValue));
        assertFalse(exceptionHolder.containsValue());
        assertFalse(exceptionHolder.containsKey());
    }
}
