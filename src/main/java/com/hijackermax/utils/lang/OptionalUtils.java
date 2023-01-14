package com.hijackermax.utils.lang;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Optional.empty;

/**
 * Useful optional providers
 */
public final class OptionalUtils {
    private OptionalUtils() {
    }

    /**
     * Returns an {@link Optional} describing the given value, if
     * value is instance of provided class, otherwise returns an empty {@link Optional}.
     *
     * @param value       the possible instance of type <T>
     * @param targetClass {@link Class} that should be used to check {@param value}
     * @param <T>         target class type
     * @return an {@link Optional} with a present value if the specified value
     * is instance of type <T>, otherwise an empty {@link Optional}
     * @since 0.0.1
     */
    public static <T> Optional<T> ofInstance(Object value, Class<T> targetClass) {
        return targetClass.isInstance(value) ? Optional.of(targetClass.cast(value)) : empty();
    }

    /**
     * Returns {@link Function} that accepts value and returns an {@link Optional} describing the given value, if
     * value is instance of provided class, otherwise returns an empty {@link Optional}.
     *
     * @param targetClass {@link Class} that should be used to check input value
     * @param <T>         input value type
     * @return {@link Function} that accepts value and returns an {@link Optional} with a present value if the specified value
     * is instance of type <T>, otherwise an empty {@link Optional}
     * @since 0.0.1
     */
    public static <T> Function<Object, Optional<T>> ofInstance(Class<T> targetClass) {
        return value -> ofInstance(value, targetClass);
    }

    /**
     * Returns an {@code Optional} describing the first non-{@code null} element of given {@link Collection}, if
     * {@link Collection} has first non-{@code null} element, otherwise returns an empty {@link Optional}.
     *
     * @param collection {@link Collection} which first non-{@code null} element is requested
     * @param <T>        input collection elements type
     * @return an {@link Optional} with a present first non-{@code null} element if the specified input collection
     * is not empty or {@code null} or has some non-{@code null} element, otherwise an empty {@link Optional}
     * @since 0.0.1
     */
    public static <T> Optional<T> ofFirst(Collection<T> collection) {
        return CollectionUtils.isEmpty(collection) ? Optional.empty() : collection.stream()
                .filter(Objects::nonNull)
                .findFirst();
    }
}
