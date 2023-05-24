package com.hijackermax.utils.builders;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Interface for abstract collection builder
 *
 * @param <T> collection element type
 * @param <C> collection type
 * @param <B> builder type
 * @since 0.0.8
 */
public interface CollectionBuilder<T, C extends Collection<? extends T>, B extends CollectionBuilder<T, C, B>> {

    /**
     * Adds provided value to collection which is being built
     *
     * @param value value that should be added to collection
     * @return builder instance
     * @since 0.0.8
     */
    B add(T value);

    /**
     * Adds result of invoking of provided value supplier to collection which is being built
     *
     * @param valueSupplier supplier of value that should be added to collection
     * @return builder instance
     * @since 0.0.8
     */
    B add(Supplier<? extends T> valueSupplier);

    /**
     * Adds values from provided collection to collection which is being built
     *
     * @param values collection that should be added to collection which is being built
     * @return builder instance
     * @since 0.0.8
     */
    B addAll(Collection<? extends T> values);

    /**
     * Builds collection
     *
     * @return built collection
     * @since 0.0.8
     */
    C build();

    /**
     * Builds unmodifiable representation of built collection
     *
     * @return unmodifiable representation of built collection
     * @since 0.0.8
     */
    C buildUnmodifiable();

    /**
     * Builds collection and provides in to the consumer
     *
     * @param collectionConsumer resulting collection consumer
     * @since 0.0.8
     */
    default void provide(Consumer<C> collectionConsumer) {
        collectionConsumer.accept(build());
    }

    /**
     * Builds unmodifiable representation of built collection and provides in to the consumer
     *
     * @param collectionConsumer resulting unmodifiable collection consumer
     * @since 0.0.8
     */
    default void provideUnmodifiable(Consumer<C> collectionConsumer) {
        collectionConsumer.accept(buildUnmodifiable());
    }
}
