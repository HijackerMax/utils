package com.hijackermax.utils.entities;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static java.util.Optional.ofNullable;

/**
 * Simple value wrapper
 *
 * @param <V> value type
 * @since 0.0.1
 */
public class Single<V> implements Consumer<V>, Supplier<V> {
    private V value;

    /**
     * Creates instance of {@link Single} with value null
     */
    public Single() {
    }

    /**
     * Creates instance of {@link Single} with provided value
     *
     * @param value that should be wrapped
     */
    public Single(V value) {
        this.value = value;
    }

    /**
     * Creates instance of {@link Single} with provided value
     *
     * @param value that should be wrapped
     * @param <V>   value type
     * @return instance of {@link Single} with provided value
     */
    public static <V> Single<V> of(V value) {
        return new Single<>(value);
    }

    /**
     * Compares value of this instance of {@link Single} with null
     *
     * @return true if value is not null, otherwise false
     */
    public boolean containsValue() {
        return value != null;
    }

    /**
     * Returns value of this instance of {@link Single}
     *
     * @return value
     */
    public V getValue() {
        return value;
    }

    /**
     * Sets value to this instance of {@link Single}
     *
     * @param value new value
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Provides ability to modify existing value
     *
     * @param processor {@link UnaryOperator} which will be applied to existing value
     */
    public void modifyValue(UnaryOperator<V> processor) {
        this.value = processor.apply(value);
    }

    /**
     * Provides ability to modify existing value if it satisfies provided predicate
     *
     * @param processor      {@link UnaryOperator} which will be applied to existing value
     * @param valuePredicate {@link Predicate} which will be used to check compatibility of existing value
     */
    public void modifyValueIfSatisfies(UnaryOperator<V> processor, Predicate<V> valuePredicate) {
        if (valuePredicate.test(value)) {
            this.value = processor.apply(value);
        }
    }

    /**
     * Provides non-null value to the provided {@link Consumer}
     *
     * @param valueConsumer {@link Consumer} of non-null value
     */
    public void provideValueIfPresent(Consumer<V> valueConsumer) {
        ofNullable(value).ifPresent(valueConsumer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Single)) {
            return false;
        }
        Single<?> single = (Single<?>) o;
        return Objects.equals(value, single.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Single{" +
                "value=" + value +
                '}';
    }

    /**
     * Accepts and stores provided value inside this instance of {@link Single}
     *
     * @param value new value
     */
    @Override
    public void accept(V value) {
        setValue(value);
    }

    /**
     * Returns stored value
     *
     * @return stored value
     */
    @Override
    public V get() {
        return value;
    }
}
