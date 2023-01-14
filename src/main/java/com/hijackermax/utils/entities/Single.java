package com.hijackermax.utils.entities;

import java.util.Objects;

/**
 * Simple value wrapper
 * @param <V> value type
 */
public class Single<V> {
    private V value;

    public Single() {
    }

    public Single(V value) {
        this.value = value;
    }

    public boolean containsValue() {
        return value != null;
    }

    public V getValue() {
        return value;
    }

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
