package com.hijackermax.utils.functional;

import java.util.Objects;

/**
 * Represents an operation that accepts four input arguments and returns no result.
 *
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the operation
 * @param <J> the type of the third argument to the operation
 * @param <W> the type of the fourth argument to the operation
 * @since 0.0.3
 */
@FunctionalInterface
public interface QuadConsumer<T, U, J, W> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @param j the third input argument
     * @param w the fourth input argument
     */
    void accept(T t, U u, J j, W w);

    /**
     * Returns a composed {@link QuadConsumer} that performs, in sequence, this operation followed by
     * the "after" operation.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@link QuadConsumer} that performs in sequence this operation followed by
     * the "after" operation
     * @throws NullPointerException if "after" is null
     */
    default QuadConsumer<T, U, J, W> andThen(QuadConsumer<? super T, ? super U, ? super J, ? super W> after) {
        Objects.requireNonNull(after);
        return (t, u, j, w) -> {
            accept(t, u, j, w);
            after.accept(t, u, j, w);
        };
    }
}