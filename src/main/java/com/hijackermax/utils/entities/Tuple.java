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

    /**
     * Creates new instance of {@link Tuple} with provided key and value
     *
     * @param key   that should be wrapped
     * @param value that should be wrapped
     */
    public Tuple(K key, V value) {
        super(value);
        this.key = key;
    }

    /**
     * Creates new instance of {@link Tuple} with null key and value
     */
    public Tuple() {
    }

    /**
     * Returns key of this instance of {@link Tuple}
     *
     * @return key
     */
    public K getKey() {
        return key;
    }

    /**
     * Sets key to this instance of {@link Tuple}
     *
     * @param key new key
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Compares key of this instance of {@link Tuple} with null
     *
     * @return true if key is not null, otherwise false
     */
    public boolean containsKey() {
        return key != null;
    }

    /**
     * Sets key and value to this instance of {@link Tuple}
     *
     * @param key   new key
     * @param value new value
     */
    public void setPair(K key, V value) {
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
