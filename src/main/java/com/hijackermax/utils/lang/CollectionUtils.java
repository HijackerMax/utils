package com.hijackermax.utils.lang;

import com.hijackermax.utils.entities.Triple;
import com.hijackermax.utils.entities.Tuple;
import com.hijackermax.utils.misc.EnumerationSpliterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

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
        return Objects.isNull(input) ? Stream.empty() : Arrays.stream(input);
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
     * Checks if left input {@link Collection} contains all elements from right input {@link Collection}
     *
     * @param left  {@link Collection} that should be checked if contains all elements from right collection
     * @param right {@link Collection} that should be tested
     * @param <I>   input type
     * @return true if left {@link Collection} contains all elements from right {@link Collection}
     * or both collections are null, false if left {@link Collection} does not contain all elements
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
     * Creates null-safe distinct union of two input {@link Collection}
     *
     * @param left  fist input {@link Collection}
     * @param right second input {@link Collection}
     * @param <I>   input collection elements type
     * @return {@link ArrayList} consisting of distinct elements from both collections
     * @since 0.0.4
     */
    public static <I> List<I> distinctUnion(Collection<? extends I> left, Collection<? extends I> right) {
        return Stream.concat(safeStreamOf(left), safeStreamOf(right))
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Creates null-safe union of input {@link Collection} that contains multiple {@link Collection}
     *
     * @param collections input {@link Collection} of collections
     * @param <I>         input collection children collections elements type
     * @return {@link ArrayList} consisting of elements from child collections
     * @since 0.0.4
     */
    public static <I> List<I> union(Collection<Collection<? extends I>> collections) {
        return safeStreamOf(collections)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * Creates null-safe distinct union of input {@link Collection} that contains multiple {@link Collection}
     *
     * @param collections input {@link Collection} of collections
     * @param <I>         input collection children collections elements type
     * @return {@link ArrayList} consisting of distinct elements from child collections
     * @since 0.0.4
     */
    public static <I> List<I> distinctUnion(Collection<Collection<? extends I>> collections) {
        return safeStreamOf(collections)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .distinct()
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
     * @return true if both {@link Collection} have same elements inside, false if there is a difference in elements
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
    public static <I> List<I> subtract(Collection<? extends I> left, Collection<? extends I> right) {
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
    public static <I> void getDifferences(Collection<? extends I> left,
                                          Collection<? extends I> right,
                                          BiConsumer<Collection<I>, Collection<I>> differenceConsumer) {
        differenceConsumer.accept(subtract(left, right), subtract(right, left));
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

    /**
     * Conducts null-safe removal of elements that satisfy the given predicate from provided {@link Collection}
     *
     * @param collection input {@link Collection}
     * @param filter     {@link Predicate} for values that should be removed if satisfied
     * @param <V>        value type
     * @return true if any elements were removed, false if collection is null or no elements were removed
     * @since 0.0.2
     */
    public static <V> boolean safeRemoveIf(Collection<? extends V> collection, Predicate<? super V> filter) {
        return Objects.nonNull(collection) && collection.removeIf(filter);
    }

    /**
     * Splits {@link Map.Entry} to key-value pair, so it can be processed by any compatible {@link BiFunction}
     *
     * @param processor {@link BiFunction} that supports types of key and value from {@link Map.Entry}
     * @param <K>       key type
     * @param <V>       value type
     * @param <R>       {@link BiFunction} result type
     * @return {@link Function} that accepts {@link Map.Entry} and returns result of {@link BiFunction} work
     * @since 0.0.3
     */
    public static <K, V, R> Function<Map.Entry<K, V>, R> split(BiFunction<? super K, ? super V, ? extends R> processor) {
        return entry -> processor.apply(entry.getKey(), entry.getValue());
    }

    /**
     * Splits {@link Map.Entry} to key-value pair, so it can be evaluated by any compatible {@link BiPredicate}
     *
     * @param predicate {@link BiPredicate} that supports types of key and value from {@link Map.Entry}
     * @param <K>       key type
     * @param <V>       value type
     * @return {@link Predicate} that accepts {@link Map.Entry} and returns result of {@link BiPredicate} evaluation
     * @since 0.0.3
     */
    public static <K, V> Predicate<Map.Entry<K, V>> splitPredicate(BiPredicate<? super K, ? super V> predicate) {
        return entry -> predicate.test(entry.getKey(), entry.getValue());
    }

    /**
     * Provides {@link Predicate} for {@link Map.Entry} that wraps {@link Predicate} for key
     *
     * @param predicate {@link Predicate} that supports key type from {@link Map.Entry}
     * @param <K>       key type
     * @param <V>       value type
     * @return {@link Predicate} that accepts {@link Map.Entry} and returns result of key {@link Predicate} evaluation
     * @since 0.0.4
     */
    public static <K, V> Predicate<Map.Entry<K, V>> keyPredicate(Predicate<? super K> predicate) {
        return entry -> predicate.test(entry.getKey());
    }

    /**
     * Provides {@link Predicate} for {@link Map.Entry} that wraps {@link Predicate} for value
     *
     * @param predicate {@link Predicate} that supports value type from {@link Map.Entry}
     * @param <K>       key type
     * @param <V>       value type
     * @return {@link Predicate} that accepts {@link Map.Entry} and returns result of value {@link Predicate} evaluation
     * @since 0.0.4
     */
    public static <K, V> Predicate<Map.Entry<K, V>> valuePredicate(Predicate<? super V> predicate) {
        return entry -> predicate.test(entry.getValue());
    }

    /**
     * Checks if input {@link Collection} contains provided value
     *
     * @param source {@link Collection} that should be checked
     * @param value  that can be present in input collection
     * @param <I>    input type
     * @return true if input {@link Collection} contains provided value,
     * false if input {@link Collection} does not contain provided value or one of the arguments is null
     * @since 0.0.5
     */
    public static <I> boolean safeContains(Collection<? super I> source, I value) {
        return Objects.nonNull(source) && Objects.nonNull(value) && source.contains(value);
    }

    /**
     * Provides input {@link Collection} if non-empty or gets fallback one from provided fallback {@link Supplier}
     *
     * @param source   {@link Collection} that can be empty or null
     * @param fallback {@link Collection} {@link Supplier} that will supply collection in case of
     *                 source one if empty or null
     * @param <T>      input type
     * @param <X>      input collection type
     * @return input {@link Collection} if not empty or null,
     * otherwise {@link Collection} supplied by provided fallback {@link Supplier}
     * @since 0.0.5
     */
    public static <T, X extends Collection<? super T>> X notEmptyOrElseGet(X source, Supplier<X> fallback) {
        Objects.requireNonNull(fallback, "Fallback supplier must not be null");
        return isEmpty(source) ? fallback.get() : source;
    }

    /**
     * Checks if source input {@link Collection} contains any of elements from vararg array
     *
     * @param source  {@link Collection} that should be checked if contains any of elements from vararg array
     * @param options vararg array that should be tested
     * @param <T>     input type
     * @return true if source {@link Collection} is not null and contains some elements from vararg array,
     * otherwise false
     * @since 0.0.5
     */
    @SafeVarargs
    public static <T> boolean safeContainsAnyArg(Collection<? super T> source, T... options) {
        return Objects.nonNull(source) && Arrays.stream(options)
                .anyMatch(source::contains);
    }

    /**
     * Checks if source input {@link Collection} contains all elements from vararg array
     *
     * @param source  {@link Collection} that should be checked if contains all elements from vararg array
     * @param options vararg array that should be tested
     * @param <T>     input type
     * @return true if source {@link Collection} is not null and contains all elements from vararg array,
     * otherwise false
     * @since 0.0.5
     */
    @SafeVarargs
    public static <T> boolean safeContainsAllArg(Collection<? super T> source, T... options) {
        return Objects.nonNull(source) && Arrays.stream(options)
                .allMatch(source::contains);
    }

    /**
     * Checks if source input {@link Collection} does not contain any of elements from vararg array
     *
     * @param source  {@link Collection} that should be checked if not contains any of elements from vararg array
     * @param options vararg array that should be tested
     * @param <T>     input type
     * @return true if source {@link Collection} is not null and does not contain any of elements from vararg array,
     * otherwise false
     * @since 0.0.5
     */
    @SafeVarargs
    public static <T> boolean safeNotContainsArg(Collection<? super T> source, T... options) {
        return Objects.nonNull(source) && Arrays.stream(options)
                .noneMatch(source::contains);
    }

    /**
     * Checks if source input {@link Collection} contains some elements from target input {@link Collection}
     *
     * @param source {@link Collection} that should be checked if contains all elements from target collection
     * @param target {@link Collection} that should be tested
     * @param <T>    input type
     * @return true if both {@link Collection} are not null and input {@link Collection} contains some elements
     * from target {@link Collection}, otherwise false
     * @since 0.0.5
     */
    public static <T> boolean safeContainsAny(Collection<? super T> source, Collection<? extends T> target) {
        return Objects.nonNull(source) && Objects.nonNull(target) && target.stream()
                .anyMatch(source::contains);
    }

    /**
     * Safely provides value from source {@link Map} that can be null or doesn't contain provided key or value is null
     *
     * @param source        {@link Map}
     * @param key           for required value
     * @param valueConsumer {@link Consumer} of existing non-null value
     * @param <K>           type of the map key
     * @param <V>           type of the map value
     * @since 0.0.8
     */
    public static <K, V> void safeProvide(Map<? extends K, ? extends V> source, K key, Consumer<? super V> valueConsumer) {
        ofNullable(source)
                .map(m -> m.get(key))
                .ifPresent(valueConsumer);
    }

    /**
     * Provides value {@link Consumer} which puts consumed value in provided map by provided key
     *
     * @param source {@link Map}
     * @param key    for provided value to put into the source {@link Map}
     * @param <K>    type of the map key
     * @param <V>    type of the map value
     * @return value {@link Consumer} which puts consumed value in provided map by provided key
     * @since 0.0.8
     */
    public static <K, V> Consumer<V> putWithValue(Map<? super K, ? super V> source, K key) {
        return value -> source.put(key, value);
    }

    /**
     * Provides key {@link Consumer} which puts provided value in provided map by consumed key
     *
     * @param source {@link Map}
     * @param value  to put into the source {@link Map}
     * @param <K>    type of the map key
     * @param <V>    type of the map value
     * @return key {@link Consumer} which puts provided value in provided map by consumed key
     * @since 0.0.8
     */
    public static <K, V> Consumer<K> putWithKey(Map<? super K, ? super V> source, V value) {
        return key -> source.put(key, value);
    }

    /**
     * Checks if {@link Enumeration} is empty or null
     *
     * @param value {@link Enumeration} that should be checked
     * @return true if {@link Enumeration} is empty or null
     * @since 0.0.8
     */
    public static boolean isEmpty(Enumeration<?> value) {
        return Objects.isNull(value) || !value.hasMoreElements();
    }

    /**
     * Returns stream from input {@link Enumeration} if it is not null or empty, or empty stream
     *
     * @param input source enumeration
     * @param <T>   {@link Enumeration} items type
     * @return stream consisting of elements from input {@link Enumeration} or empty stream
     * @since 0.0.8
     */
    public static <T> Stream<T> safeStreamOf(Enumeration<T> input) {
        return isEmpty(input) ? Stream.empty() : EnumerationSpliterator.ofOrdered(input).stream();
    }

    /**
     * Checks if any of input {@link Collection} elements satisfies provided {@link Predicate}
     *
     * @param values          collection that should be checked
     * @param valuesPredicate the predicate that should be applied to source elements collection
     * @param <T>             collection elements type
     * @return true if collection contains at least one element that satisfies provided predicate
     * @since 0.0.9
     */
    public static <T> boolean safeAnyMatch(Collection<? extends T> values, Predicate<? super T> valuesPredicate) {
        return safeStreamOf(values).anyMatch(valuesPredicate);
    }

    /**
     * Checks if all of input {@link Collection} elements satisfy provided {@link Predicate}
     *
     * @param values          collection that should be checked
     * @param valuesPredicate the predicate that should be applied to source elements collection
     * @param <T>             collection elements type
     * @return true if collection contains all elements that satisfy provided predicate
     * @since 0.0.9
     */
    public static <T> boolean safeAllMatch(Collection<? extends T> values, Predicate<? super T> valuesPredicate) {
        return isNotEmpty(values) && values.stream().allMatch(valuesPredicate);
    }

    /**
     * Checks if none of input {@link Collection} elements satisfy provided {@link Predicate}
     *
     * @param values          collection that should be checked
     * @param valuesPredicate the predicate that should be applied to source elements collection
     * @param <T>             collection elements type
     * @return true if collection contains no elements that satisfy provided predicate
     * @since 0.0.9
     */
    public static <T> boolean safeNoneMatch(Collection<? extends T> values, Predicate<? super T> valuesPredicate) {
        return isEmpty(values) || values.stream().noneMatch(valuesPredicate);
    }

    /**
     * Passes provided {@link Collection} if it is not empty to the provided {@link Consumer}
     *
     * @param values         the collection to check
     * @param valuesConsumer non-empty collection consumer
     * @param <T>            input collection value type
     * @param <C>            input collection type
     * @since 0.1.0
     */
    public static <T, C extends Collection<? extends T>> void ifNotEmpty(C values, Consumer<? super C> valuesConsumer) {
        if (isNotEmpty(values)) {
            valuesConsumer.accept(values);
        }
    }

    /**
     * Passes provided {@link Map} if it is not empty to the provided {@link Consumer}
     *
     * @param values         the map to check
     * @param valuesConsumer non-empty map consumer
     * @param <K>            input map key type
     * @param <V>            input map value type
     * @param <M>            input map type
     * @since 0.1.0
     */
    public static <K, V, M extends Map<? extends K, ? extends V>> void ifNotEmpty(M values, Consumer<? super M> valuesConsumer) {
        if (isNotEmpty(values)) {
            valuesConsumer.accept(values);
        }
    }

    /**
     * Calculates differences between left input {@link Map} and right input {@link Map}
     *
     * @param left  first input map
     * @param right second input map
     * @param <K>   input maps key type
     * @param <V>   input maps value type
     * @return map containing key - value pairs of differences, key set is union of input maps keys, values are tuples with left and right maps values respectively
     * @since 0.1.0
     */
    public static <K, V> Map<K, Tuple<V, V>> getDifferences(Map<? extends K, ? extends V> left,
                                                            Map<? extends K, ? extends V> right) {
        Map<? extends K, ? extends V> leftSafe = Objects.isNull(left) ? Collections.emptyMap() : left;
        Map<? extends K, ? extends V> rightSafe = Objects.isNull(right) ? Collections.emptyMap() : right;
        return Stream.of(leftSafe.keySet(), rightSafe.keySet())
                .flatMap(Collection::stream)
                .distinct()
                .map(k -> Triple.of(k, leftSafe.get(k), rightSafe.get(k)))
                .filter(t -> !Objects.equals(t.getMiddle(), t.getValue()))
                .collect(Collectors.toMap(Triple::getKey, t -> Tuple.of(t.getMiddle(), t.getValue())));
    }
}
