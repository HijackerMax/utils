package com.hijackermax.utils.builders;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Map builder, builds {@link HashMap} if not initialized with custom map constructor via {@link com.hijackermax.utils.builders.MapBuilder#with(Supplier)} or {@link com.hijackermax.utils.builders.MapBuilder#ofEnum(Class)}
 *
 * @param <K> key type
 * @param <V> value type
 * @since 0.0.8
 */
public class MapBuilder<K, V> implements DictionaryBuilder<K, V, Map<K, V>, MapBuilder<K, V>> {
    private final Map<K, V> data;

    private MapBuilder() {
        this.data = new HashMap<>();
    }

    private MapBuilder(Map<? extends K, ? extends V> data) {
        this.data = new HashMap<>(Objects.requireNonNull(data));
    }

    private MapBuilder(K key, V value) {
        this(Map.of(key, value));
    }

    private MapBuilder(Supplier<Map<K, V>> mapSupplier) {
        this.data = Objects.requireNonNull(mapSupplier.get());
    }

    /**
     * Builds instance of map builder for {@link EnumMap} with provided {@link Enum} class
     *
     * @param enumClass class of enum that should be used as a key
     * @param <K>       key type, should be an enum
     * @param <V>       value type
     * @return builder instance with {@link EnumMap} as base map
     * @since 0.0.8
     */
    public static <K extends Enum<K>, V> MapBuilder<K, V> ofEnum(Class<K> enumClass) {
        return with(() -> new EnumMap<>(Objects.requireNonNull(enumClass)));
    }

    /**
     * Builds instance of map builder with provided map supplier as base map
     *
     * @param mapSupplier new base map instance supplier
     * @param <K>         key type
     * @param <V>         value type
     * @return builder instance with custom base map
     * @since 0.0.8
     */
    public static <K, V> MapBuilder<K, V> with(Supplier<Map<K, V>> mapSupplier) {
        return new MapBuilder<>(mapSupplier);
    }

    /**
     * Builds instance of map builder with {@link HashMap} base map
     *
     * @param <K> key type
     * @param <V> value type
     * @return builder instance with {@link HashMap} base map
     * @since 0.0.8
     */
    public static <K, V> MapBuilder<K, V> of() {
        return new MapBuilder<>();
    }

    /**
     * Builds instance of map builder with {@link HashMap} base map and puts provided key-value pair
     *
     * @param key   key that should be put into map which is being built
     * @param value value that should be put into map
     * @param <K>   key type
     * @param <V>   value type
     * @return builder instance with {@link HashMap} base map
     * @since 0.0.8
     */
    public static <K, V> MapBuilder<K, V> of(K key, V value) {
        return new MapBuilder<>(key, value);
    }

    /**
     * Builds instance of map builder with {@link HashMap} base map and puts provided map key-value pairs
     *
     * @param data map which key-value pairs should be put into map which is being built
     * @param <K>  key type
     * @param <V>  value type
     * @return builder instance with {@link HashMap} base map
     * @since 0.0.8
     */
    public static <K, V> MapBuilder<K, V> of(Map<? extends K, ? extends V> data) {
        return new MapBuilder<>(data);
    }

    /**
     * Puts provided key-value pair to map which is being built
     *
     * @param key   key that should be added to map
     * @param value value that should be added to map
     * @return builder instance
     * @since 0.0.8
     */
    @Override
    public MapBuilder<K, V> put(K key, V value) {
        data.put(key, value);
        return this;
    }

    /**
     * Puts provided key-value pair to map which is being built if some value wasn't added previously with the same key
     *
     * @param key   key that should be added to map
     * @param value value that should be added to map
     * @return builder instance
     * @since 0.0.8
     */
    @Override
    public MapBuilder<K, V> putIfAbsent(K key, V value) {
        data.putIfAbsent(key, value);
        return this;
    }

    /**
     * Puts key-value pairs from provided map to map which is being built
     *
     * @param values map that should be added to map which is being built
     * @return builder instance
     * @since 0.0.8
     */
    @Override
    public MapBuilder<K, V> putAll(Map<? extends K, ? extends V> values) {
        data.putAll(Objects.requireNonNull(values));
        return this;
    }

    /**
     * Builds map
     *
     * @return built map
     * @since 0.0.8
     */
    @Override
    public Map<K, V> build() {
        return data;
    }

    /**
     * Builds unmodifiable representation of built map
     *
     * @return unmodifiable representation of built map
     * @since 0.0.8
     */
    @Override
    public Map<K, V> buildUnmodifiable() {
        return Collections.unmodifiableMap(data);
    }
}
