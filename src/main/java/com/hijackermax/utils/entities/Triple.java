package com.hijackermax.utils.entities;

import com.hijackermax.utils.functional.TriConsumer;

import java.util.Objects;
import java.util.function.Consumer;

import static java.util.Optional.ofNullable;

/**
 * Simple key:middle:value triple wrapper
 *
 * @param <K> key type
 * @param <M> middle type
 * @param <V> value type
 * @since 0.0.3
 */
public class Triple<K, M, V> extends Tuple<K, V> {
    private M middle;

    /**
     * Creates new instance of {@link Triple} with provided key, middle and value
     *
     * @param key    that should be wrapped
     * @param middle that should be wrapped
     * @param value  that should be wrapped
     */
    public Triple(K key, M middle, V value) {
        super(key, value);
        this.middle = middle;
    }

    /**
     * Creates new instance of {@link Triple} with null key, middle and value
     */
    public Triple() {
    }

    /**
     * Returns middle of this instance of {@link Triple}
     *
     * @return middle
     */
    public M getMiddle() {
        return middle;
    }

    /**
     * Sets middle to this instance of {@link Triple}
     *
     * @param middle new middle
     */
    public void setMiddle(M middle) {
        this.middle = middle;
    }

    /**
     * Compares middle of this instance of {@link Triple} with null
     *
     * @return true if middle is not null, otherwise false
     */
    public boolean containsMiddle() {
        return middle != null;
    }

    /**
     * Provides non-null middle to the provided {@link Consumer}
     *
     * @param middleConsumer {@link Consumer} of non-null middle
     */
    public void provideMiddleIfPresent(Consumer<M> middleConsumer) {
        ofNullable(middle).ifPresent(middleConsumer);
    }

    /**
     * Provides key, middle and value to the provided {@link TriConsumer}
     *
     * @param tripleConsumer {@link TriConsumer} of key, middle and value
     */
    public void provideTriple(TriConsumer<K, M, V> tripleConsumer) {
        tripleConsumer.accept(getKey(), middle, getValue());
    }

    /**
     * Provides non-null key, non-null middle and non-null value to the provided {@link TriConsumer}
     *
     * @param tripleConsumer {@link TriConsumer} of non-null key, non-null middle and non-null value
     */
    public void provideTripleIfPresent(TriConsumer<K, M, V> tripleConsumer) {
        if (Objects.nonNull(getKey()) && Objects.nonNull(middle) && Objects.nonNull(getValue())) {
            tripleConsumer.accept(getKey(), middle, getValue());
        }
    }

    /**
     * Sets key, middle and value to this instance of {@link Triple}
     *
     * @param key    new key
     * @param middle new middle
     * @param value  new value
     */
    public void setThree(K key, M middle, V value) {
        setKey(key);
        setMiddle(middle);
        setValue(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Triple)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Triple<?, ?, ?> triple = (Triple<?, ?, ?>) o;
        return Objects.equals(getMiddle(), triple.getMiddle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMiddle());
    }

    @Override
    public String toString() {
        return "Triple{" +
                "middle=" + middle +
                "} " + super.toString();
    }
}
