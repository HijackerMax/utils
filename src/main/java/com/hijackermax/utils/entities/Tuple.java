package com.hijackermax.utils.entities;

import java.util.Objects;

/**
 * Simple key:value pair wrapper
 *
 * @param <K> key type
 * @param <V> value type
 */
public class Tuple<K, V> extends Single<V> {
    private K key;

    public Tuple(K key, V value) {
        super(value);
        this.key = key;
    }

    public Tuple() {
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setPair(K key, V value){
        setKey(key);
        setValue(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tuple)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(key, tuple.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key);
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "key=" + key +
                "} " + super.toString();
    }
}
