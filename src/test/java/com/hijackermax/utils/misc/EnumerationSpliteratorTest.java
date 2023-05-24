package com.hijackermax.utils.misc;

import com.hijackermax.utils.lang.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Spliterator;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hijackermax.utils.misc.EnumerationSpliterator.of;
import static com.hijackermax.utils.misc.EnumerationSpliterator.ofOrdered;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnumerationSpliteratorTest {
    @Test
    void testStream() {
        Vector<Integer> numberVector = IntStream.range(0, 100)
                .boxed()
                .collect(Collectors.toCollection(Vector::new));
        boolean isParallel = of(numberVector.size(), Spliterator.ORDERED, numberVector.elements()).stream()
                .isParallel();
        assertFalse(isParallel);
        List<Integer> collectedNumbers = of(numberVector.size(), Spliterator.ORDERED, numberVector.elements()).stream()
                .collect(Collectors.toList());
        assertEquals(49, collectedNumbers.get(49));
        assertTrue(CollectionUtils.safeContainsAll(numberVector, collectedNumbers));
    }

    @Test
    void testParallelStream() {
        Vector<Integer> numberVector = IntStream.range(0, 100)
                .boxed()
                .collect(Collectors.toCollection(Vector::new));
        boolean isParallel = ofOrdered(numberVector.elements()).parallelStream()
                .isParallel();
        assertTrue(isParallel);
        List<Integer> collectedNumbers = ofOrdered(numberVector.elements()).parallelStream()
                .collect(Collectors.toList());
        assertEquals(49, collectedNumbers.get(49));
        assertTrue(CollectionUtils.safeContainsAll(numberVector, collectedNumbers));
    }
}
