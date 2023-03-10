package com.hijackermax.utils.switches;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Actions dynamic switch
 *
 * @param <T> type of case values
 * @param <V> type of evaluated expression
 * @since 0.0.2
 */
public class ActionsDynamicSwitch<T, V> {
    private final Map<T, Runnable> cases = new HashMap<>();
    private final BiPredicate<V, T> valuesPredicate;
    private Runnable onDefault = () -> {
    };

    private ActionsDynamicSwitch() {
        this(Object::equals);
    }

    private ActionsDynamicSwitch(BiPredicate<V, T> valuesPredicate) {
        this.valuesPredicate = Objects.requireNonNull(valuesPredicate);
    }

    /**
     * Builds switch with default values {@link BiPredicate} which compares two objects with {@code Object.equals}
     *
     * @param <T> type of case values
     * @param <V> type of evaluated expression
     * @return {@link ActionsDynamicSwitch} with {@code Object.equals} {@link BiPredicate}
     */
    public static <T, V> ActionsDynamicSwitch<T, V> builder() {
        return new ActionsDynamicSwitch<>();
    }

    /**
     * Builds switch with custom values {@link BiPredicate}
     *
     * @param valuesComparator {@link BiPredicate} which compares case values with expression
     * @param <T>              type of case values
     * @param <V>              type of evaluated expression
     * @return {@link ActionsDynamicSwitch} with custom {@link BiPredicate}
     */
    public static <T, V> ActionsDynamicSwitch<T, V> builder(BiPredicate<V, T> valuesComparator) {
        return new ActionsDynamicSwitch<>(valuesComparator);
    }

    /**
     * Adds new case or replaces existing one with the same case value
     *
     * @param value    case value
     * @param runnable {@link Runnable} that should be executed if provided case value and expression satisfy switch {@link BiPredicate}
     * @return {@link ActionsDynamicSwitch}
     */
    public ActionsDynamicSwitch<T, V> addCase(T value, Runnable runnable) {
        cases.put(Objects.requireNonNull(value), runnable);
        return this;
    }

    /**
     * Adds default case or replaces existing one
     *
     * @param runnable {@link Runnable} that should be executed
     *                 if none of the provided case values and expression satisfy switch {@link BiPredicate}
     * @return {@link ActionsDynamicSwitch}
     */
    public ActionsDynamicSwitch<T, V> addDefault(Runnable runnable) {
        this.onDefault = Objects.requireNonNull(runnable);
        return this;
    }

    /**
     * Conducts switch operation on provided expression, if provided expression is null,
     * or it does not satisfy any of case values default {@link Runnable} will be invoked
     *
     * @param value expression that should be switched
     */
    public void doSwitch(V value) {
        Predicate<T> casesPredicate = k ->
                Objects.nonNull(value) && valuesPredicate.test(value, k);

        cases.keySet().stream()
                .filter(casesPredicate)
                .findFirst()
                .map(cases::get)
                .ifPresentOrElse(Runnable::run, onDefault);
    }
}
