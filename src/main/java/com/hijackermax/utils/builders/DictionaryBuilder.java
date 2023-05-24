package com.hijackermax.utils.builders;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Interface for abstract map builder
 *
 * @param <K> map keys type
 * @param <V> map values type
 * @param <M> map type
 * @param <B> builder type
 * @since 0.0.8
 */
public interface DictionaryBuilder<K, V, M extends Map<? extends K, ? extends V>, B extends DictionaryBuilder<K, V, M, B>> {

    /**
     * Puts provided key-value pair to map which is being built
     *
     * @param key   key that should be added to map
     * @param value value that should be added to map
     * @return builder instance
     * @since 0.0.8
     */
    B put(K key, V value);

    /**
     * Puts provided key-value pair to map which is being built if some value wasn't added previously with the same key
     *
     * @param key   key that should be added to map
     * @param value value that should be added to map
     * @return builder instance
     * @since 0.0.8
     */
    B putIfAbsent(K key, V value);

    /**
     * Puts key-value pairs from provided map to map which is being built
     *
     * @param values map that should be added to map which is being built
     * @return builder instance
     * @since 0.0.8
     */
    B putAll(Map<? extends K, ? extends V> values);

    /**
     * Builds map
     *
     * @return built map
     * @since 0.0.8
     */
    M build();

    /**
     * Builds unmodifiable representation of built map
     *
     * @return unmodifiable representation of built map
     * @since 0.0.8
     */
    M buildUnmodifiable();

    /**
     * Builds map and provides in to the consumer
     *
     * @param mapConsumer resulting map consumer
     * @since 0.0.8
     */
    default void provide(Consumer<M> mapConsumer) {
        mapConsumer.accept(build());
    }

    /**
     * Builds unmodifiable representation of built map and provides in to the consumer
     *
     * @param mapConsumer resulting unmodifiable map consumer
     * @since 0.0.8
     */
    default void provideUnmodifiable(Consumer<M> mapConsumer) {
        mapConsumer.accept(buildUnmodifiable());
    }
}
