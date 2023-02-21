package com.hijackermax.utils.functional;

import java.util.Objects;
import java.util.function.Function;

/**
 * Represents a function that accepts four arguments and produces a result.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <J> the type of the third argument to the function
 * @param <W> the type of the fourth argument to the function
 * @param <R> the type of the result of the function
 * @since 0.0.3
 */
@FunctionalInterface
public interface QuadFunction<T, U, J, W, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @param j the third function argument
     * @param w the fourth function argument
     * @return the function result
     */
    R apply(T t, U u, J j, W w);

    /**
     * Returns a composed function that first applies this function to its input,
     * and then applies the "after" function to the result.
     *
     * @param <V>   the type of output of the "after" function, and of the composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     * applies the "after" function
     * @throws NullPointerException if after is null
     */
    default <V> QuadFunction<T, U, J, W, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (t, u, j, w) -> after.apply(apply(t, u, j, w));
    }
}