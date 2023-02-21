package com.hijackermax.utils.switches;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Class dynamic switch
 *
 * @since 0.0.3
 */
public class ClassDynamicSwitch {
    private final Map<Class<?>, Consumer<?>> cases = new HashMap<>();
    private final BiPredicate<Class<?>, Object> valuesPredicate;
    private Runnable onDefault = () -> {
    };

    private ClassDynamicSwitch(BiPredicate<Class<?>, Object> valuesPredicate) {
        this.valuesPredicate = valuesPredicate;
    }

    /**
     * Builds switch with isInstance {@link BiPredicate} which checks is evaluated object instance of class
     *
     * @return {@link ClassDynamicSwitch} with {@code Class.isInstance} {@link BiPredicate}
     */
    public static ClassDynamicSwitch instanceBuilder() {
        return new ClassDynamicSwitch(Class::isInstance);
    }

    /**
     * Builds switch with isAssignableFrom {@link BiPredicate} which checks is evaluated object's class assignable from class
     *
     * @return {@link ClassDynamicSwitch} with {@code Class.isAssignableFrom} {@link BiPredicate}
     */
    public static ClassDynamicSwitch assignableBuilder() {
        return new ClassDynamicSwitch((c, o) -> c.isAssignableFrom(o.getClass()));
    }

    /**
     * Adds new case or replaces existing one with the same case value
     *
     * @param value    possible class of evaluated object
     * @param consumer {@link Consumer} that should be invoked with provided value if it satisfies class compare
     * @param <T>      possible type
     * @return this instance of {@link ClassDynamicSwitch}
     */
    public <T> ClassDynamicSwitch addCase(Class<T> value, Consumer<? super T> consumer) {
        cases.put(Objects.requireNonNull(value), consumer);
        return this;
    }

    /**
     * Adds default case or replaces existing one
     *
     * @param runnable {@link Runnable} that should be executed if none of the provided classes is related to provided object
     * @return {@link ClassDynamicSwitch}
     */
    public ClassDynamicSwitch addDefault(Runnable runnable) {
        this.onDefault = Objects.requireNonNull(runnable);
        return this;
    }

    /**
     * Conducts switch operation on provided expression, if provided expression is null,
     * or it does not satisfy any of case values default {@link Runnable} will be invoked
     *
     * @param <T>   type of value
     * @param value expression that should be switched
     */
    @SuppressWarnings("unchecked")
    public <T> void doSwitch(T value) {
        Predicate<Class<?>> casesPredicate = k ->
                Objects.nonNull(value) && valuesPredicate.test(k, value);

        cases.keySet().stream()
                .filter(casesPredicate)
                .findFirst()
                .map(cases::get)
                .map(v -> (Consumer<? super T>) v)
                .ifPresentOrElse(v -> v.accept(value), onDefault);
    }

    /**
     * Conducts switch operation on provided expression, if provided expression is null,
     * or it does not satisfy any of case values throws an exception produced by the exception supplying function.
     *
     * @param value             expression that should be switched
     * @param exceptionSupplier the supplying function that produces an exception to be thrown
     * @param <T>               type of value
     * @param <E>               type of the exception to be thrown
     * @throws E                    if no value is present
     * @throws NullPointerException if no value is present and the "exceptionSupplier" is null
     */
    @SuppressWarnings("unchecked")
    public <T, E extends Throwable> void doSwitchOrThrow(T value, Supplier<? extends E> exceptionSupplier) throws E {
        Predicate<Class<?>> casesPredicate = k ->
                Objects.nonNull(value) && valuesPredicate.test(k, value);

        cases.keySet().stream()
                .filter(casesPredicate)
                .findFirst()
                .map(cases::get)
                .map(v -> (Consumer<? super T>) v)
                .orElseThrow(exceptionSupplier)
                .accept(value);
    }
}
