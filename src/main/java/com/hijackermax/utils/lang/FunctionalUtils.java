package com.hijackermax.utils.lang;

import com.hijackermax.utils.entities.Tuple;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Set of functional utility methods
 */
public final class FunctionalUtils {
    private FunctionalUtils() {
    }

    /**
     * Provides wrapper {@link Consumer} which catches {@link Exception} thrown by the input value {@link Consumer}
     * and supplies it to provided exception{@link BiConsumer}
     *
     * @param consumer          value {@link Consumer} that can throw {@link Exception}
     * @param exceptionConsumer input value and thrown {@link Exception} {@link BiConsumer}
     * @param <T>               input value type
     * @return wrapper {@link Consumer}
     * @since 0.0.6
     */
    public static <T> Consumer<T> failSafeStrategy(Consumer<T> consumer, BiConsumer<T, Exception> exceptionConsumer) {
        return value -> {
            try {
                consumer.accept(value);
            } catch (Exception e) {
                exceptionConsumer.accept(value, e);
            }
        };
    }

    /**
     * Provides wrapper {@link BiConsumer} which catches {@link Exception} thrown by the input value {@link BiConsumer}
     * and supplies it to provided exception{@link BiConsumer}
     *
     * @param consumer          values {@link BiConsumer} that can throw {@link Exception}
     * @param exceptionConsumer input values tuple and thrown {@link Exception} {@link BiConsumer}
     * @param <L>               left input value type
     * @param <R>               right input value type
     * @return wrapper {@link BiConsumer}
     * @since 0.1.3
     */
    public static <L, R> BiConsumer<L, R> failSafeStrategy(BiConsumer<L, R> consumer, BiConsumer<Tuple<L, R>, Exception> exceptionConsumer) {
        return (left, right) -> {
            try {
                consumer.accept(left, right);
            } catch (Exception e) {
                exceptionConsumer.accept(Tuple.of(left, right), e);
            }
        };
    }


    /**
     * Catches {@link Exception} thrown by provided {@link Runnable} and supplies it to provided exception{@link Consumer}
     *
     * @param runnable          {@link Runnable} that can throw {@link Exception}
     * @param exceptionConsumer thrown {@link Exception} {@link Consumer}
     * @since 0.0.6
     */
    public static void failSafeStrategy(Runnable runnable,
                                        Consumer<Exception> exceptionConsumer) {
        try {
            runnable.run();
        } catch (Exception e) {
            exceptionConsumer.accept(e);
        }
    }

    /**
     * Provides wrapper {@link Callable} which catches {@link Exception} thrown by the provided {@link Callable}
     * and supplies it to provided exception{@link Consumer},
     * in case of failure return invoke result of fallback {@link Supplier}
     *
     * @param callable          {@link Callable} that can throw {@link Exception}
     * @param fallback          value {@link Supplier} in case of failure of {@link Callable}
     * @param exceptionConsumer thrown {@link Exception} {@link Consumer}
     * @param <T>               result value type
     * @return invoke result of provided {@link Callable} or fallback {@link Callable} invoke result
     * @since 0.0.6
     */
    public static <T> T failSafeStrategy(Callable<T> callable,
                                         Supplier<T> fallback,
                                         Consumer<Exception> exceptionConsumer) {
        try {
            return callable.call();
        } catch (Exception e) {
            exceptionConsumer.accept(e);
        }
        return fallback.get();
    }

    /**
     * Provides {@link Predicate} which allows to do value transformation and validation of new value with provided {@link Predicate}
     *
     * @param transformer        {@link Function} which transforms provided value to different
     * @param extractedPredicate {@link Predicate} for transformed value
     * @param <T>                source value type
     * @param <U>                transformed value type
     * @return {@link Predicate} which allows to do value transformation and validation of new value with provided {@link Predicate},
     * if source value is null result will be false
     * @since 0.0.8
     */
    public static <T, U> Predicate<T> mappedPredicate(Function<T, U> transformer, Predicate<U> extractedPredicate) {
        return source -> Objects.nonNull(source) &&
                Objects.requireNonNull(transformer).andThen(Objects.requireNonNull(extractedPredicate)::test).apply(source);
    }

    /**
     * Provides empty {@link Consumer}
     *
     * @param <T> supplied value type
     * @return empty consumer
     * @since 0.0.9
     */
    public static <T> Consumer<T> emptyConsumer() {
        return t -> {
        };
    }

    /**
     * Provides empty {@link Runnable}
     *
     * @return empty runnable
     * @since 0.1.3
     */
    public static Runnable emptyRunnable() {
        return () -> {
        };
    }

    /**
     * Provides empty {@link BiConsumer}
     *
     * @param <L> left supplied value type
     * @param <R> right supplied value type
     * @return empty consumer
     * @since 0.1.3
     */
    public static <L, R> BiConsumer<L, R> emptyBiConsumer() {
        return (l, r) -> {
        };
    }

    /**
     * Provides wrapper {@link Function} which catches {@link Exception} thrown by the input value {@link Function}
     * and supplies it to provided exception{@link BiConsumer}
     *
     * @param function          {@link Function} that can throw {@link Exception}
     * @param fallback          value {@link Supplier} in case of failure of {@link Function}
     * @param exceptionConsumer input value and thrown {@link Exception} {@link BiConsumer}
     * @param <T>               input value type
     * @param <R>               result value type
     * @return wrapper {@link Function}
     * @since 0.1.3
     */
    public static <T, R> Function<T, R> failSafeStrategy(Function<T, R> function,
                                                         Supplier<R> fallback,
                                                         BiConsumer<T, Exception> exceptionConsumer) {
        return value -> {
            try {
                return function.apply(value);
            } catch (Exception e) {
                exceptionConsumer.accept(value, e);
            }
            return fallback.get();
        };
    }
}
