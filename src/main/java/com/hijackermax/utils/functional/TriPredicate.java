package com.hijackermax.utils.functional;

import java.util.Objects;

/**
 * Represents a predicate of three arguments
 *
 * @param <T> the type of the first argument to the predicate
 * @param <U> the type of the second argument the predicate
 * @param <J> the type of the third argument the predicate
 * @since 0.0.3
 */
@FunctionalInterface
public interface TriPredicate<T, U, J> {

    /**
     * Evaluates this predicate on the given arguments
     *
     * @param t the first input argument
     * @param u the second input argument
     * @param j the third input argument
     * @return true if the input arguments match the predicate, otherwise false
     */
    boolean test(T t, U u, J j);

    /**
     * Returns a composed predicate that represents a logical conjunction of this predicate and another. Behaves similar to boolean AND
     *
     * @param other a predicate that will be logically-conjugated with this predicate
     * @return a composed predicate that represents the logical conjunction of this predicate and the "other" predicate
     * @throws NullPointerException if other is null
     */
    default TriPredicate<T, U, J> and(TriPredicate<? super T, ? super U, ? super J> other) {
        Objects.requireNonNull(other);
        return (t, u, j) -> test(t, u, j) && other.test(t, u, j);
    }

    /**
     * Returns a predicate that represents the logical inversion of this predicate
     *
     * @return a predicate that represents the logical inversion of this predicate
     */
    default TriPredicate<T, U, J> negate() {
        return (t, u, j) -> !test(t, u, j);
    }

    /**
     * Returns a composed predicate that represents a logical disjunction of this predicate and another. Behaves similar to boolean OR
     *
     * @param other a predicate that will be used to conduct disjunction with this predicate
     * @return a composed predicate that represents the logical disjunction of this predicate and the "other" predicate
     * @throws NullPointerException if other is null
     */
    default TriPredicate<T, U, J> or(TriPredicate<? super T, ? super U, ? super J> other) {
        Objects.requireNonNull(other);
        return (t, u, j) -> test(t, u, j) || other.test(t, u, j);
    }

    /**
     * Returns a composed predicate that represents a logical exclusive disjunction of this predicate and another. Behaves similar to boolean XOR
     *
     * @param other a predicate that will be used to conduct exclusive disjunction with this predicate
     * @return a composed predicate that represents the logical exclusive disjunction of this predicate and the "other" predicate
     * @throws NullPointerException if other is null
     */
    default TriPredicate<T, U, J> xor(TriPredicate<? super T, ? super U, ? super J> other) {
        Objects.requireNonNull(other);
        return (t, u, j) -> test(t, u, j) ^ other.test(t, u, j);
    }
}