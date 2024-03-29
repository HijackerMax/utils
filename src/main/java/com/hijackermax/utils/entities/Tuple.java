package com.hijackermax.utils.entities;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static java.util.Optional.ofNullable;

/**
 * Simple key:value pair wrapper
 *
 * @param <K> key type
 * @param <V> value type
 * @since 0.0.1
 */
public class Tuple<K, V> extends Single<V> implements BiConsumer<K, V> {
    private K key;

    /**
     * Creates instance of {@link Tuple} with provided key and value
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
     * Creates instance of {@link Tuple} with provided key and value
     *
     * @param key   that should be wrapped
     * @param value that should be wrapped
     * @param <K>   key type
     * @param <V>   value type
     * @return instance of {@link Tuple} with provided key and value
     */
    public static <K, V> Tuple<K, V> of(K key, V value) {
        return new Tuple<>(key, value);
    }

    /**
     * Creates instance of {@link Tuple} from provided {@link Map.Entry}
     *
     * @param entry that should be converted to tuple
     * @param <K>   key type
     * @param <V>   value type
     * @return instance of {@link Tuple} from provided map entity
     */
    public static <K, V> Tuple<K, V> of(Map.Entry<K, V> entry) {
        return new Tuple<>(entry.getKey(), entry.getValue());
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
     * Returns left value (key) of this instance of {@link Tuple}
     *
     * @return left value (key)
     */
    public K getLeft() {
        return key;
    }

    /**
     * Sets left value (key) to this instance of {@link Tuple}
     *
     * @param left new left value (key)
     */
    public void setLeft(K left) {
        setKey(left);
    }

    /**
     * Returns right value (value) of this instance of {@link Tuple}
     *
     * @return right value (value)
     */
    public V getRight() {
        return getValue();
    }

    /**
     * Sets right value (value) to this instance of {@link Tuple}
     *
     * @param right new right value (value)
     */
    public void setRight(V right) {
        setValue(right);
    }

    /**
     * Provides ability to modify existing key
     *
     * @param processor {@link UnaryOperator} which will be applied to existing key
     */
    public void modifyKey(UnaryOperator<K> processor) {
        this.key = processor.apply(key);
    }

    /**
     * Provides ability to modify existing key if it satisfies provided predicate
     *
     * @param processor    {@link UnaryOperator} which will be applied to existing key
     * @param keyPredicate {@link Predicate} which will be used to check compatibility of existing key
     */
    public void modifyKeyIfSatisfies(UnaryOperator<K> processor, Predicate<K> keyPredicate) {
        if (keyPredicate.test(key)) {
            this.key = processor.apply(key);
        }
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
     * Compares left value (key) of this instance of {@link Tuple} with null
     *
     * @return true if left value (key) is not null, otherwise false
     */
    public boolean containsLeft() {
        return containsKey();
    }

    /**
     * Compares right value (value) of this instance of {@link Tuple} with null
     *
     * @return true if right value (value) is not null, otherwise false
     */
    public boolean containsRight() {
        return containsValue();
    }

    /**
     * Provides non-null key to the provided {@link Consumer}
     *
     * @param keyConsumer {@link Consumer} of non-null key
     */
    public void provideKeyIfPresent(Consumer<K> keyConsumer) {
        ofNullable(key).ifPresent(keyConsumer);
    }

    /**
     * Provides key and value to the provided {@link BiConsumer}
     *
     * @param tupleConsumer {@link BiConsumer} of key and value
     */
    public void provideTuple(BiConsumer<K, V> tupleConsumer) {
        tupleConsumer.accept(key, getValue());
    }

    /**
     * Provides non-null key and non-null value to the provided {@link BiConsumer}
     *
     * @param tupleConsumer {@link BiConsumer} of non-null key and non-null value
     */
    public void provideTupleIfPresent(BiConsumer<K, V> tupleConsumer) {
        if (Objects.nonNull(key) && Objects.nonNull(getValue())) {
            tupleConsumer.accept(key, getValue());
        }
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
                "value=" + getValue() +
                '}';
    }

    /**
     * Accepts and stores provided key and value inside this instance of {@link Tuple}
     *
     * @param key   new key
     * @param value new value
     */
    @Override
    public void accept(K key, V value) {
        setPair(key, value);
    }
}
