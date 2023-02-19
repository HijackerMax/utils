package com.hijackermax.utils.switches;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Values dynamic switch
 *
 * @param <T> type of case values
 * @param <V> type of evaluated expression
 * @param <R> type of result value
 * @since 0.0.2
 */
public class ValuesDynamicSwitch<T, V, R> {
    private final Map<T, R> cases = new HashMap<>();
    private final BiPredicate<V, T> valuesPredicate;
    private final R onDefault;

    private ValuesDynamicSwitch(R defaultValue) {
        this(Object::equals, defaultValue);
    }

    private ValuesDynamicSwitch(BiPredicate<V, T> valuesPredicate, R defaultValue) {
        this.valuesPredicate = Objects.requireNonNull(valuesPredicate);
        this.onDefault = defaultValue;
    }

    /**
     * Builds switch with default values {@link BiPredicate} which compares two objects with {@code Object.equals}
     *
     * @param <T> type of case values
     * @param <V> type of evaluated expression
     * @param <R> type of result value
     * @return {@link ValuesDynamicSwitch} with {@code Object.equals} {@link BiPredicate}
     */
    public static <T, V, R> ValuesDynamicSwitch<T, V, R> builder(R defaultValue) {
        return new ValuesDynamicSwitch<>(defaultValue);
    }

    /**
     * Builds switch with custom values {@link BiPredicate}
     *
     * @param valuesComparator {@link BiPredicate} which compares case values with expression
     * @param <T>              type of case values
     * @param <V>              type of evaluated expression
     * @param <R>              type of result value
     * @return {@link ValuesDynamicSwitch} with custom {@link BiPredicate}
     */
    public static <T, V, R> ValuesDynamicSwitch<T, V, R> builder(BiPredicate<V, T> valuesComparator, R defaultValue) {
        return new ValuesDynamicSwitch<>(valuesComparator, defaultValue);
    }

    /**
     * Adds new case or replaces existing one with the same case value
     *
     * @param value       case value
     * @param resultValue value that should be returned if provided case value and expression satisfy switch {@link BiPredicate}
     * @return {@link  ValuesDynamicSwitch}
     */
    public ValuesDynamicSwitch<T, V, R> addCase(T value, R resultValue) {
        cases.put(Objects.requireNonNull(value), resultValue);
        return this;
    }

    /**
     * Conducts switch operation on provided expression, if provided expression is null,
     * or it does not satisfy any of case values default value will be returned
     *
     * @param value expression that should be switched
     */
    public R doSwitch(V value) {
        Predicate<T> casesPredicate = k ->
                Objects.nonNull(value) && valuesPredicate.test(value, k);

        return cases.keySet().stream()
                .filter(casesPredicate)
                .findFirst()
                .map(cases::get)
                .orElse(onDefault);
    }
}
