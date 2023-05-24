package com.hijackermax.utils.builders;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Set builder, builds {@link HashSet} if not initialized with custom set constructor via {@link com.hijackermax.utils.builders.SetBuilder#with(Supplier)}
 * Does not support null elements
 *
 * @param <T> set element type
 * @since 0.0.8
 */
public class SetBuilder<T> implements CollectionBuilder<T, Set<T>, SetBuilder<T>> {
    private final Set<T> data;

    private SetBuilder() {
        this.data = new HashSet<>();
    }

    private SetBuilder(Collection<? extends T> data) {
        this.data = new HashSet<>(Objects.requireNonNull(data));
    }

    private SetBuilder(T value) {
        this(Set.of(Objects.requireNonNull(value)));
    }

    private SetBuilder(Supplier<Set<T>> setSupplier) {
        this.data = Objects.requireNonNull(setSupplier.get());
    }

    /**
     * Builds instance of set builder with provided set supplier as base collection
     *
     * @param setSupplier new base collection instance supplier
     * @param <T>         collection element type
     * @return builder instance with custom base collection
     * @since 0.0.8
     */
    public static <T> SetBuilder<T> with(Supplier<Set<T>> setSupplier) {
        return new SetBuilder<>(setSupplier);
    }

    /**
     * Builds instance of set builder with {@link HashSet} base collection
     *
     * @param <T> collection element type
     * @return builder instance with {@link HashSet} base collection
     * @since 0.0.8
     */
    public static <T> SetBuilder<T> of() {
        return new SetBuilder<>();
    }

    /**
     * Builds instance of set builder with {@link HashSet} base collection and inserts provided value
     *
     * @param value element that should be inserted into set
     * @param <T>   collection element type
     * @return builder instance with {@link HashSet} base collection
     * @since 0.0.8
     */
    public static <T> SetBuilder<T> of(T value) {
        return new SetBuilder<>(value);
    }

    /**
     * Builds instance of set builder with {@link HashSet} base collection and inserts provided collection values
     *
     * @param data element collection which values should be inserted into set
     * @param <T>  collection element type
     * @return builder instance with {@link HashSet} base collection
     * @since 0.0.8
     */
    public static <T> SetBuilder<T> of(Collection<? extends T> data) {
        return new SetBuilder<>(data);
    }

    /**
     * Adds provided value to set which is being built
     *
     * @param value value that should be added to collection
     * @return builder instance
     * @since 0.0.8
     */
    @Override
    public SetBuilder<T> add(T value) {
        data.add(Objects.requireNonNull(value));
        return this;
    }

    /**
     * Adds values from provided collection to set which is being built
     *
     * @param values collection that should be added to collection which is being built
     * @return builder instance
     * @since 0.0.8
     */
    @Override
    public SetBuilder<T> addAll(Collection<? extends T> values) {
        data.addAll(Objects.requireNonNull(values));
        return this;
    }

    /**
     * Adds result of invoking of provided value supplier to set which is being built
     *
     * @param valueSupplier supplier of value that should be added to collection
     * @return builder instance
     * @since 0.0.8
     */
    @Override
    public SetBuilder<T> add(Supplier<? extends T> valueSupplier) {
        return add(valueSupplier.get());
    }

    /**
     * Builds set
     *
     * @return built set
     * @since 0.0.8
     */
    @Override
    public Set<T> build() {
        return data;
    }

    /**
     * Builds unmodifiable representation of built set
     *
     * @return unmodifiable representation of built set
     * @since 0.0.8
     */
    @Override
    public Set<T> buildUnmodifiable() {
        return Collections.unmodifiableSet(data);
    }
}
