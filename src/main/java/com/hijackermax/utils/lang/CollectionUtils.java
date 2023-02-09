package com.hijackermax.utils.lang;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Set of utility methods that can help to work with Java collections
 */
public final class CollectionUtils {
    private CollectionUtils() {
    }

    /**
     * Checks if {@link Map} is empty or null
     *
     * @param value {@link Map} that should be checked
     * @return true if {@link Map} is empty or null
     * @since 0.0.1
     */
    public static boolean isEmpty(Map<?, ?> value) {
        return Objects.isNull(value) || value.isEmpty();
    }

    /**
     * Checks if {@link Map} is not empty or null
     *
     * @param value {@link Map} that should be checked
     * @return true if {@link Map} is not empty or null
     * @since 0.0.1
     */
    public static boolean isNotEmpty(Map<?, ?> value) {
        return !isEmpty(value);
    }

    /**
     * Checks if {@link Collection} is empty or null
     *
     * @param value {@link Collection} that should be checked
     * @return true if {@link Collection} is empty or null
     * @since 0.0.1
     */
    public static boolean isEmpty(Collection<?> value) {
        return Objects.isNull(value) || value.isEmpty();
    }

    /**
     * Checks if {@link Collection} is not empty or null
     *
     * @param value {@link Collection} that should be checked
     * @return true if {@link Collection} is not empty or null
     * @since 0.0.1
     */
    public static boolean isNotEmpty(Collection<?> value) {
        return !isEmpty(value);
    }

    /**
     * Returns stream from input {@link Collection} if it is not null or empty, or empty stream
     *
     * @param input source collection
     * @param <T>   {@link Collection} items type
     * @return stream consisting of elements from input {@link Collection} or empty stream
     * @since 0.0.1
     */
    public static <T> Stream<T> safeStreamOf(Collection<T> input) {
        return isEmpty(input) ? Stream.empty() : input.stream();
    }

    /**
     * Returns filtered with {@link Predicate} stream of objects
     * from input {@link Collection} if it is not null or empty, or empty stream
     *
     * @param input  source collection
     * @param filter {@link Predicate} for input type objects
     * @param <T>    {@link Collection} items type
     * @return stream consisting of elements from input {@link Collection} or empty stream
     * @since 0.0.1
     */
    public static <T> Stream<T> safeFilteredStreamOf(Collection<T> input, Predicate<T> filter) {
        return Objects.isNull(input) ? Stream.empty() : input.stream().filter(filter);
    }

    /**
     * Returns stream from input array of objects if it is not null, or empty stream
     *
     * @param input source array
     * @param <T>   array items type
     * @return stream consisting of elements from input array or empty stream
     * @since 0.0.1
     */
    public static <T> Stream<T> safeStreamOf(T[] input) {
        return Objects.isNull(input) ? Stream.empty() : Stream.of(input);
    }

    /**
     * Returns stream of {@link Map.Entry} from input map if it is not null or empty, or empty stream
     *
     * @param input source map
     * @param <K>   key type
     * @param <V>   value type
     * @return stream of {@link Map.Entry} from input map or empty stream
     * @since 0.0.1
     */
    public static <K, V> Stream<Map.Entry<K, V>> safeStreamOf(Map<K, V> input) {
        return isEmpty(input) ? Stream.empty() : input.entrySet().stream();
    }

    /**
     * Iterates through source {@link Map} if it is not null or empty
     *
     * @param input          source map
     * @param valuesConsumer The consumer for each entry
     * @param <K>            key type
     * @param <V>            value type
     * @since 0.0.1
     */
    public static <K, V> void safeForEach(Map<? extends K, ? extends V> input,
                                          BiConsumer<? super K, ? super V> valuesConsumer) {
        if (isNotEmpty(input)) {
            input.forEach(valuesConsumer);
        }
    }

    /**
     * Iterates through source {@link Collection} if it is not null or empty
     *
     * @param input          source collection
     * @param valuesConsumer The consumer for each item
     * @param <V>            value type
     * @since 0.0.1
     */
    public static <V> void safeForEach(Collection<? extends V> input, Consumer<? super V> valuesConsumer) {
        if (isNotEmpty(input)) {
            input.forEach(valuesConsumer);
        }
    }

    /**
     * Returns parallel stream from input {@link Collection} if it is not null or empty, or empty stream
     *
     * @param input source {@link Collection}
     * @param <T>   {@link Collection} items type
     * @return parallel stream consisting of elements from input {@link Collection} or empty stream
     * @since 0.0.1
     */
    public static <T> Stream<T> safeParallelStreamOf(Collection<T> input) {
        return isEmpty(input) ? Stream.empty() : input.parallelStream();
    }

    /**
     * Maps input {@link Collection} with provided mapper
     * {@link Function} or returns empty list if input {@link Collection} is null
     *
     * @param input  source {@link Collection}
     * @param mapper mapping {@link Function}
     * @param <I>    input type
     * @param <O>    output type
     * @return {@link ArrayList} of mapped entities
     * @since 0.0.1
     */
    public static <I, O> List<O> map(Collection<? extends I> input, Function<? super I, ? extends O> mapper) {
        return safeStreamOf(input)
                .map(mapper)
                .collect(Collectors.toList());
    }

    /**
     * Maps input {@link Collection} with provided mapper {@link Function} and sorts elements
     * according to the provided {@link Comparator} or returns empty list if input {@link Collection} is null
     *
     * @param input      source {@link Collection}
     * @param mapper     mapping {@link Function}
     * @param comparator {@link Comparator} for output type objects
     * @param <I>        input type
     * @param <O>        output type
     * @return {@link ArrayList} of mapped entities
     * @since 0.0.1
     */
    public static <I, O> List<O> mapSorted(Collection<? extends I> input,
                                           Function<? super I, ? extends O> mapper,
                                           Comparator<? super O> comparator) {
        return safeStreamOf(input)
                .map(mapper)
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    /**
     * Filters input {@link Collection} with provided {@link Predicate} and maps
     * with provided mapper {@link Function} or returns empty list if input {@link Collection} is null
     *
     * @param input  source {@link Collection}
     * @param mapper mapping {@link Function}
     * @param filter {@link Predicate} for input type objects
     * @param <I>    input type
     * @param <O>    output type
     * @return {@link ArrayList} of filtered and mapped entities
     * @since 0.0.1
     */
    public static <I, O> List<O> filterAndMap(Collection<? extends I> input,
                                              Predicate<? super I> filter,
                                              Function<? super I, ? extends O> mapper) {
        return safeStreamOf(input)
                .filter(filter)
                .map(mapper)
                .collect(Collectors.toList());
    }

    /**
     * Maps input {@link Collection} with provided mapper {@link Function}
     * or provides empty list if input {@link Collection} is null
     *
     * @param input    source {@link Collection}
     * @param mapper   mapping {@link Function}
     * @param consumer {@link Consumer} of {@link ArrayList} of mapped values
     * @param <I>      input type
     * @param <O>      output type
     * @since 0.0.1
     */
    public static <I, O> void map(Collection<? extends I> input,
                                  Function<? super I, ? extends O> mapper,
                                  Consumer<List<? extends O>> consumer) {
        consumer.accept(map(input, mapper));
    }

    /**
     * Maps input {@link Collection} with provided mapper {@link Function} or
     * provides empty list if input {@link Collection} is null
     *
     * @param mapper mapping {@link Function}
     * @param <I>    input type
     * @param <O>    output type
     * @return converting {@link Function}
     * @since 0.0.1
     */
    public static <I, O> Function<Collection<I>, List<O>> map(Function<I, O> mapper) {
        return collection -> map(collection, mapper);
    }

    /**
     * Filters input {@link Collection} with provided {@link Predicate}
     * or returns empty list if input {@link Collection} is null
     *
     * @param input     {@link Collection} that should be filtered
     * @param predicate {@link Predicate} for input type objects
     * @param <I>       input type
     * @return {@link ArrayList} of filtered entities
     * @since 0.0.1
     */
    public static <I> List<I> filter(Collection<? extends I> input, Predicate<? super I> predicate) {
        return safeStreamOf(input)
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * Conducts null-safe check if left input {@link Collection}
     * contains all elements from right input {@link Collection}
     *
     * @param left  {@link Collection} that should be checked if contains all elements from right collection
     * @param right {@link Collection} that should be tested
     * @param <I>   input type
     * @return true if left {@link Collection} contains all elements from right {@link Collection}
     * or both collections are null, {@code false} if left {@link Collection} does not contains all elements
     * from right {@link Collection} or one of the collections is null
     * @since 0.0.1
     */
    public static <I> boolean safeContainsAll(Collection<I> left, Collection<I> right) {
        if (Objects.isNull(left) || Objects.isNull(right)) {
            return Objects.isNull(left) && Objects.isNull(right);
        }
        return left.containsAll(right);
    }

    /**
     * Calculates null-safe size of input {@link Collection}
     *
     * @param collection input {@link Collection}
     * @return size of input {@link Collection} or 0 if input {@link Collection} is null
     * @since 0.0.1
     */
    public static int safeSize(Collection<?> collection) {
        return Objects.isNull(collection) ? 0 : collection.size();
    }

    /**
     * Creates null-safe union of two input {@link Collection}
     *
     * @param left  fist input {@link Collection}
     * @param right second input {@link Collection}
     * @param <I>   input collection elements type
     * @return {@link ArrayList} consisting of elements from both collections
     * @since 0.0.1
     */
    public static <I> List<I> union(Collection<? extends I> left, Collection<? extends I> right) {
        return Stream.concat(safeStreamOf(left), safeStreamOf(right))
                .collect(Collectors.toList());
    }

    /**
     * Returns first non-null element from input {@link Collection}
     *
     * @param collection input {@link Collection}
     * @param <I>        input collection elements type
     * @return first element in collection or null if collections is empty or null
     * @since 0.0.1
     */
    public static <I> I first(Collection<? extends I> collection) {
        return isEmpty(collection) ? null : collection.stream()
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    /**
     * Conducts null-safe check if left input {@link Collection}
     * and right input {@link Collection} have all same elements,
     * order of elements is ignored
     *
     * @param left  fist input {@link Collection}
     * @param right second input {@link Collection}
     * @param <I>   input collection elements type
     * @return true if both {@link Collection} have same elements inside,
     * {@code false} if there is a difference in elements
     * @since 0.0.1
     */
    public static <I> boolean haveSameElements(Collection<I> left, Collection<I> right) {
        if (Objects.isNull(left) || Objects.isNull(right)) {
            return false;
        }
        return left.containsAll(right) && right.containsAll(left);
    }

    /**
     * Conducts null-safe subtraction of right input {@link Collection} from left input {@link Collection},
     * order of elements is ignored
     *
     * @param left  minuend input {@link Collection}
     * @param right subtrahend input {@link Collection}
     * @param <I>   input collection elements type
     * @return {@link ArrayList} consisting of elements that are present in left input {@link Collection}
     * but missing in right input {@link Collection} or right input {@link Collection} is null,
     * or empty immutable list if left collection is null
     * @since 0.0.1
     */
    public static <I> List<I> subtract(Collection<I> left, Collection<I> right) {
        if (Objects.isNull(left) || Objects.isNull(right)) {
            return Objects.isNull(left) ? Collections.emptyList() : new ArrayList<>(left);
        }
        return left.stream()
                .distinct()
                .filter(v -> !right.contains(v))
                .collect(Collectors.toList());
    }

    /**
     * Provides null-safe list of common elements in left input {@link Collection} and right input {@link Collection}
     *
     * @param left  input {@link Collection}
     * @param right input {@link Collection}
     * @param <I>   input collection elements type
     * @return {@link ArrayList} consisting of common elements of input collections,
     * or empty immutable list if one of the collections is null
     * @since 0.0.1
     */
    public static <I> List<I> intersection(Collection<I> left, Collection<I> right) {
        if (Objects.isNull(left) || Objects.isNull(right)) {
            return Collections.emptyList();
        }
        return left.stream()
                .distinct()
                .filter(right::contains)
                .collect(Collectors.toList());
    }

    /**
     * Supplies differences between left input {@link Collection} and right input {@link Collection}
     *
     * @param left               input {@link Collection}
     * @param right              input {@link Collection}
     * @param differenceConsumer {@link BiConsumer} consumer of two {@link ArrayList},
     *                           first contains elements that are present in the left input {@link Collection}
     *                           but missing in the right input {@link Collection},
     *                           second contains elements that are present in the right input {@link Collection}
     *                           but missing in the left input {@link Collection}
     * @param <I>                input collection elements type
     * @since 0.0.1
     */
    public static <I> void getDifferences(Collection<I> left,
                                          Collection<I> right,
                                          BiConsumer<Collection<I>, Collection<I>> differenceConsumer) {
        differenceConsumer.accept(subtract(left, right), subtract(right, left));
    }

    /**
     * Provides grouping collector which allows null key
     *
     * @param keyExtractor {@link Function} for key extraction
     * @param <T>          input collection elements type
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
     * @param <T>            input collection elements type
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
     * Conducts null-safe conversion of input {@link Collection} to {@link Map} using provided key extractor {@link Function}
     *
     * @param values        input {@link Collection}
     * @param keyExtractor  {@link Function} for key extraction
     * @param mergeFunction {@link BinaryOperator} for merging entities with same key
     * @param <K>           {@link Map} key type
     * @param <V>           {@link Map} and  {@link Collection} value type
     * @return resulting {@link Map}
     * @since 0.0.1
     */
    public static <K, V> Map<K, V> toMap(Collection<? extends V> values,
                                         Function<? super V, ? extends K> keyExtractor,
                                         BinaryOperator<V> mergeFunction) {
        return safeStreamOf(values)
                .filter(value -> Objects.nonNull(keyExtractor.apply(value)))
                .collect(Collectors.toMap(keyExtractor, Function.identity(), mergeFunction));
    }

    /**
     * Conducts null-safe conversion of input {@link Collection} to {@link Map}
     * using provided key extractor {@link Function}
     * and value extractor {@link Function}
     *
     * @param values         input {@link Collection}
     * @param keyExtractor   {@link Function} for key extraction
     * @param valueExtractor {@link Function} for value extraction
     * @param <K>            {@link Map} key type
     * @param <V>            {@link Collection} value type
     * @param <X>            {@link Map} value type
     * @return resulting {@link Map}
     * @since 0.0.1
     */
    public static <K, V, X> Map<K, X> toMap(Collection<? extends V> values,
                                            Function<? super V, ? extends K> keyExtractor,
                                            Function<? super V, ? extends X> valueExtractor) {
        return safeStreamOf(values)
                .filter(value -> Objects.nonNull(keyExtractor.apply(value)))
                .collect(Collectors.toMap(keyExtractor, valueExtractor));
    }

    /**
     * Conducts null-safe conversion of input {@link Collection} to {@link Map}
     * using provided key extractor {@link Function}, ignores values with key duplicates
     *
     * @param values       input {@link Collection}
     * @param keyExtractor {@link Function} for key extraction
     * @param <K>          {@link Map} key type
     * @param <V>          {@link Map} and  {@link Collection} value type
     * @return resulting {@link Map}
     * @since 0.0.1
     */
    public static <K, V> Map<K, V> toMap(Collection<? extends V> values,
                                         Function<? super V, ? extends K> keyExtractor) {
        return toMap(values, keyExtractor, (a, b) -> a);
    }

    /**
     * Conducts null-safe conversion of input {@link Collection} to {@link Map}
     * with multiple values with duplicating keys using provided key extractor {@link Function}
     *
     * @param values       input {@link Collection}
     * @param keyExtractor {@link Function} for key extraction
     * @param <K>          {@link Map} key type
     * @param <V>          {@link Map} and  {@link Collection} value type
     * @return resulting {@link Map}
     * @since 0.0.1
     */
    public static <K, V> Map<K, List<V>> toMultiMap(Collection<? extends V> values,
                                                    Function<? super V, ? extends K> keyExtractor) {
        return safeStreamOf(values)
                .filter(value -> Objects.nonNull(keyExtractor.apply(value)))
                .collect(Collectors.groupingBy(keyExtractor, Collectors.toList()));
    }

    /**
     * Conducts null-safe key-value swapping of input {@link Map} with filtration
     *
     * @param input          input {@link Map}
     * @param valuePredicate {@link Predicate} for input {@link Map} values
     * @param <K>            {@link Map} key type
     * @param <V>            {@link Map} value type
     * @return filtered {@link Map} with swapped keys and values
     * @since 0.0.1
     */
    public static <K, V> Map<V, K> reverseMap(Map<? extends K, ? extends V> input,
                                              Predicate<? super V> valuePredicate) {
        return safeStreamOf(input)
                .filter(e -> valuePredicate.test(e.getValue()))
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    /**
     * Conducts null-safe key-value swapping of input {@link Map}
     *
     * @param input input {@link Map}
     * @param <K>   {@link Map} key type
     * @param <V>   {@link Map} value type
     * @return {@link Map} with swapped keys and values
     * @since 0.0.1
     */
    public static <K, V> Map<V, K> reverseMap(Map<? extends K, ? extends V> input) {
        return safeStreamOf(input)
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }
}
