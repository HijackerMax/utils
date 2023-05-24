package com.hijackermax.utils.builders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * List builder, builds {@link ArrayList} if not initialized with custom list constructor via {@link com.hijackermax.utils.builders.ListBuilder#with(Supplier)}
 * Does not support null elements
 *
 * @param <T> list element type
 * @since 0.0.8
 */
public class ListBuilder<T> implements CollectionBuilder<T, List<T>, ListBuilder<T>> {
    private final List<T> data;

    private ListBuilder() {
        this.data = new ArrayList<>();
    }

    private ListBuilder(Collection<? extends T> data) {
        this.data = new ArrayList<>(Objects.requireNonNull(data));
    }

    private ListBuilder(T value) {
        this(List.of(Objects.requireNonNull(value)));
    }

    private ListBuilder(Supplier<List<T>> listSupplier) {
        this.data = Objects.requireNonNull(listSupplier.get());
    }

    /**
     * Builds instance of list builder with provided list supplier as base collection
     *
     * @param listSupplier new base collection instance supplier
     * @param <T>          collection element type
     * @return builder instance with custom base collection
     * @since 0.0.8
     */
    public static <T> ListBuilder<T> with(Supplier<List<T>> listSupplier) {
        return new ListBuilder<>(listSupplier);
    }

    /**
     * Builds instance of list builder with {@link ArrayList} base collection
     *
     * @param <T> collection element type
     * @return builder instance with {@link ArrayList} base collection
     * @since 0.0.8
     */
    public static <T> ListBuilder<T> of() {
        return new ListBuilder<>();
    }

    /**
     * Builds instance of list builder with {@link ArrayList} base collection and inserts provided value
     *
     * @param value element that should be inserted into list
     * @param <T>   collection element type
     * @return builder instance with {@link ArrayList} base collection
     * @since 0.0.8
     */
    public static <T> ListBuilder<T> of(T value) {
        return new ListBuilder<>(value);
    }

    /**
     * Builds instance of list builder with {@link ArrayList} base collection and inserts provided collection values
     *
     * @param data element collection which values should be inserted into list
     * @param <T>  collection element type
     * @return builder instance with {@link ArrayList} base collection
     * @since 0.0.8
     */
    public static <T> ListBuilder<T> of(Collection<? extends T> data) {
        return new ListBuilder<>(data);
    }

    /**
     * Adds provided value to list which is being built
     *
     * @param value value that should be added to collection
     * @return builder instance
     * @since 0.0.8
     */
    @Override
    public ListBuilder<T> add(T value) {
        data.add(Objects.requireNonNull(value));
        return this;
    }

    /**
     * Adds values from provided collection to list which is being built
     *
     * @param values collection that should be added to collection which is being built
     * @return builder instance
     * @since 0.0.8
     */
    @Override
    public ListBuilder<T> addAll(Collection<? extends T> values) {
        data.addAll(Objects.requireNonNull(values));
        return this;
    }

    /**
     * Adds result of invoking of provided value supplier to list which is being built
     *
     * @param valueSupplier supplier of value that should be added to collection
     * @return builder instance
     * @since 0.0.8
     */
    @Override
    public ListBuilder<T> add(Supplier<? extends T> valueSupplier) {
        return add(valueSupplier.get());
    }

    /**
     * Builds list
     *
     * @return built list
     * @since 0.0.8
     */
    @Override
    public List<T> build() {
        return data;
    }

    /**
     * Builds unmodifiable representation of built list
     *
     * @return unmodifiable representation of built list
     * @since 0.0.8
     */
    @Override
    public List<T> buildUnmodifiable() {
        return Collections.unmodifiableList(data);
    }
}
