package com.hijackermax.utils.builders;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Helper class, provides ability to build instance of type T without implemented builder
 *
 * @param <T> item type
 * @since 0.0.1
 */
public class XBuilder<T> {
    private final T entity;

    /**
     * Creates new instance of XBuilder with value provided by value supplier
     *
     * @param entitySupplier supplier of entity that should be wrapped
     */
    public XBuilder(Supplier<T> entitySupplier) {
        this.entity = entitySupplier.get();
    }

    /**
     * Sets value to one of the fields of wrapped entity
     *
     * @param consumer {@link BiConsumer} from entity for provided value
     * @param value    for entity
     * @param <V>      type of provided value
     * @return this instance of {@link XBuilder}
     */
    public <V> XBuilder<T> add(BiConsumer<T, V> consumer, V value) {
        consumer.accept(entity, value);
        return this;
    }

    /**
     * Transforms entity wrapped by this XBuilder using provided transformer function
     *
     * @param transformer {@link Function}
     * @param <R>         type of transformed object
     * @return transformed entity
     */
    public <R> R transform(Function<T, R> transformer) {
        return transformer.apply(entity);
    }

    /**
     * Returns entity wrapped by this XBuilder
     *
     * @return wrapped entity
     */
    public T build() {
        return entity;
    }
}
