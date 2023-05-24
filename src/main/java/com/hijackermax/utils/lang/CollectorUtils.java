package com.hijackermax.utils.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Set of stream collectors
 */
public final class CollectorUtils {
    private CollectorUtils() {
    }

    /**
     * Provides grouping collector which allows null key
     *
     * @param keyExtractor {@link Function} for key extraction
     * @param <T>          input stream elements type
     * @param <K>          key type
     * @return Grouping collector with supported null key
     * @since 0.0.1
     */
    public static <T, K> Collector<T, ?, Map<K, List<T>>> groupingByWithNull(Function<? super T, ? extends K> keyExtractor) {
        return groupingByWithNull(keyExtractor, Function.identity());
    }

    /**
     * Provides grouping collector which allows null key
     *
     * @param keyExtractor   {@link Function} for key extraction
     * @param valueExtractor {@link Function} for values extraction
     * @param <T>            input stream elements type
     * @param <K>            key type
     * @param <V>            value type
     * @return Grouping collector with supported null key
     * @since 0.0.1
     */
    public static <T, K, V> Collector<T, ?, Map<K, List<V>>> groupingByWithNull(Function<? super T, ? extends K> keyExtractor,
                                                                                Function<? super T, ? extends V> valueExtractor) {
        return Collectors.toMap(
                keyExtractor,
                v -> Collections.singletonList(valueExtractor.apply(v)),
                (prev, curr) -> {
                    List<V> result = new ArrayList<>(prev.size() + curr.size());
                    result.addAll(prev);
                    result.addAll(curr);
                    return result;
                });
    }

    /**
     * Provides to {@link String} {@link Collector} for stream of {@link Character}
     *
     * @return {@link Collector} which concatenates {@link Character} elements in encounter order
     * @since 0.0.4
     */
    public static Collector<Character, ?, String> toStringCollector() {
        return Collector.of(
                StringBuffer::new,
                StringBuffer::append,
                StringBuffer::append,
                StringBuffer::toString
        );
    }

    /**
     * Provides to {@link EnumMap} {@link Collector}
     *
     * @param keyExtractor   {@link Function} for key extraction
     * @param valueExtractor {@link Function} for values extraction
     * @param mergeFunction  {@link BinaryOperator}, used to resolve collisions between values associated with the same key
     * @param enumClass      class of {@link Enum} which represents key of resulting {@link EnumMap}
     * @param <T>            input stream elements type
     * @param <K>            key type
     * @param <V>            value type
     * @return {@link Collector} which collects provided elements to {@link EnumMap}
     * @since 0.0.8
     */
    public static <T, K extends Enum<K>, V> Collector<T, ?, Map<K, V>> toEnumMap(Function<? super T, ? extends K> keyExtractor,
                                                                                 Function<? super T, ? extends V> valueExtractor,
                                                                                 BinaryOperator<V> mergeFunction,
                                                                                 Class<K> enumClass) {
        return Collectors.toMap(
                keyExtractor,
                valueExtractor,
                mergeFunction,
                () -> new EnumMap<>(enumClass)
        );
    }

    /**
     * Provides to {@link EnumMap} {@link Collector}
     *
     * @param keyExtractor   {@link Function} for key extraction
     * @param valueExtractor {@link Function} for values extraction
     * @param enumClass      class of {@link Enum} which represents key of resulting {@link EnumMap}
     * @param <T>            input stream elements type
     * @param <K>            key type
     * @param <V>            value type
     * @return {@link Collector} which collects provided elements to {@link EnumMap}
     * @throws IllegalStateException if collisions between values associated with the same key encountered
     * @since 0.0.8
     */
    public static <T, K extends Enum<K>, V> Collector<T, ?, Map<K, V>> toEnumMap(Function<? super T, ? extends K> keyExtractor,
                                                                                 Function<? super T, ? extends V> valueExtractor,
                                                                                 Class<K> enumClass) {
        return toEnumMap(
                keyExtractor,
                valueExtractor,
                (left, right) -> {
                    throw new IllegalStateException(String.format("Duplicate keys for values %s and %s", left, right));
                },
                enumClass
        );
    }
}
