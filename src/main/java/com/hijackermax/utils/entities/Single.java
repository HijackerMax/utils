package com.hijackermax.utils.entities;

import java.util.Objects;

/**
 * Simple value wrapper
 * @param <V> value type
 */
public class Single<V> {
    private V value;

    /**
     * Creates instance of {@link Single} with value null
     */
    public Single() {
    }

    /**
     * Creates instance of {@link Single} with provided value
     * @param value that should be wrapped
     */
    public Single(V value) {
        this.value = value;
    }

    /**
     * Compares value of this instance of {@link Single} with null
     * @return true if value is not null, otherwise false
     */
    public boolean containsValue() {
        return value != null;
    }

    /**
     * Returns value of this instance of {@link Single}
     * @return value
     */
    public V getValue() {
        return value;
    }

    /**
     * Sets value to this instance of {@link Single}
     * @param value new value
     */
    public void setValue(V value) {
        this.value = value;
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
}
