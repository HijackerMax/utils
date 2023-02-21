package com.hijackermax.utils.functional;

import java.util.Objects;

/**
 * Represents an operation that accepts three input arguments and returns no result.
 *
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the operation
 * @param <J> the type of the third argument to the operation
 * @since 0.0.3
 */
@FunctionalInterface
public interface TriConsumer<T, U, J> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @param j the third input argument
     */
    void accept(T t, U u, J j);

    /**
     * Returns a composed {@link TriConsumer} that performs, in sequence, this operation followed by
     * the "after" operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@link TriConsumer} that performs in sequence this operation followed by
     * the "after" operation
     * @throws NullPointerException if "after" is null
     */
    default TriConsumer<T, U, J> andThen(TriConsumer<? super T, ? super U, ? super J> after) {
        Objects.requireNonNull(after);
        return (t, u, j) -> {
            accept(t, u, j);
            after.accept(t, u, j);
        };
    }
}