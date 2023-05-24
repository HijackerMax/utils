package com.hijackermax.utils.builders;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Helper class, provides ability to perform transformation of immutable classes like {@link String} or {@link Integer}
 *
 * @param <T> type of value for transformation
 * @since 0.0.8
 */
public class Transformer<T> {
    private T value;

    @SuppressWarnings("unused")
    private Transformer() {
        throw new UnsupportedOperationException();
    }

    private Transformer(T value) {
        this.value = Objects.requireNonNull(value, "Input argument cannot be null");
    }

    /**
     * Provides instance of {@link Transformer} with given value
     *
     * @param value immutable value to transform
     * @param <T>   type of value for transformation
     * @return instance of {@link Transformer}
     * @throws NullPointerException if provided value is null
     */
    public static <T> Transformer<T> of(T value) {
        return new Transformer<>(value);
    }

    /**
     * Conducts transformation of value with provided {@link UnaryOperator} processor
     *
     * @param valueProcessor {@link UnaryOperator} which does transformation over a value
     * @return instance of {@link Transformer}
     * @throws NullPointerException if valueProcessor result is null
     */
    public Transformer<T> run(UnaryOperator<T> valueProcessor) {
        this.value = Objects.requireNonNull(valueProcessor.apply(value), "Transformation result cannot be null");
        return this;
    }

    /**
     * Returns result of all transformations conducted before
     *
     * @return resulting value, cannot be null
     */
    public T result() {
        return value;
    }

    /**
     * Provides result of all transformations conducted before
     *
     * @param valueConsumer resulting value {@link Consumer}
     */
    public void provide(Consumer<T> valueConsumer) {
        valueConsumer.accept(value);
    }

    /**
     * Provides new instance of {@link Transformer} which is built with result of converting
     * current {@link Transformer} value with provided mapper {@link Function}
     *
     * @param mapper converting {@link Function}
     * @param <V>    type of mapping result
     * @return new instance of {@link Transformer} for mapping result
     */
    public <V> Transformer<V> evolve(Function<T, V> mapper) {
        return new Transformer<>(mapper.apply(value));
    }
}
