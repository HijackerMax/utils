package com.hijackermax.utils.misc;

import java.util.Enumeration;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Spliterator for Enumerations
 *
 * @param <V> type of enumeration elements
 * @since 0.0.8
 */
public class EnumerationSpliterator<V> extends Spliterators.AbstractSpliterator<V> {
    private final Enumeration<V> enumeration;

    private EnumerationSpliterator(long est, int additionalCharacteristics, Enumeration<V> enumeration) {
        super(est, additionalCharacteristics);
        this.enumeration = enumeration;
    }

    /**
     * Creates instance of {@link EnumerationSpliterator} with provided size, characteristics and enumeration
     *
     * @param est                       the estimated size of spliterator
     * @param additionalCharacteristics properties of the enumeration elements
     * @param enumeration               the enumeration that should be splited
     * @param <V>                       type of enumeration elements
     * @return instance of {@link EnumerationSpliterator} with provided size, characteristics and enumeration
     */
    public static <V> EnumerationSpliterator<V> of(long est, int additionalCharacteristics, Enumeration<V> enumeration) {
        return new EnumerationSpliterator<>(est, additionalCharacteristics, Objects.requireNonNull(enumeration));
    }

    /**
     * Creates instance of {@link EnumerationSpliterator} with unknown size ordered enumeration
     *
     * @param enumeration the enumeration that should be splited
     * @param <V>         type of enumeration elements
     * @return instance of {@link EnumerationSpliterator} with unknown size ordered enumeration
     */
    public static <V> EnumerationSpliterator<V> ofOrdered(Enumeration<V> enumeration) {
        return of(Long.MAX_VALUE, Spliterator.ORDERED, Objects.requireNonNull(enumeration));
    }

    /**
     * Creates a sequential {@link Stream} from current instance of {@link EnumerationSpliterator}
     *
     * @return a sequential {@link Stream}
     */
    public Stream<V> stream() {
        return StreamSupport.stream(this, false);
    }

    /**
     * Creates a parallel {@link Stream} from current instance of {@link EnumerationSpliterator}
     *
     * @return a parallel {@link Stream}
     */
    public Stream<V> parallelStream() {
        return StreamSupport.stream(this, true);
    }

    /**
     * If a remaining element exists, performs provided action on it returning true, if not returns false
     *
     * @param action The action
     * @return false if no remaining elements existed upon entry to this method, else true
     */
    @Override
    public boolean tryAdvance(Consumer<? super V> action) {
        if (!enumeration.hasMoreElements()) {
            return false;
        }
        action.accept(enumeration.nextElement());
        return true;
    }

    /**
     * Performs the provided action for each remaining element, sequentially in the current thread,
     * until all elements have been processed or the action throws an exception
     *
     * @param action The action
     */
    @Override
    public void forEachRemaining(Consumer<? super V> action) {
        while (enumeration.hasMoreElements()) {
            action.accept(enumeration.nextElement());
        }
    }
}
