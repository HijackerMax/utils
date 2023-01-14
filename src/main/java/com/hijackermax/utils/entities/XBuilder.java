package com.hijackermax.utils.entities;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Helper class, provides ability to build instance of type T without implemented builder
 *
 * @param <T> item type
 */
public class XBuilder<T> {
    private final T entity;

    public XBuilder(Supplier<T> entitySupplier) {
        this.entity = entitySupplier.get();
    }

    public <V> XBuilder<T> add(BiConsumer<T, V> supplier, V value) {
        supplier.accept(entity, value);
        return this;
    }

    public <R> R use(Function<T, R> user) {
        return user.apply(entity);
    }

    public T build() {
        return entity;
    }
}
