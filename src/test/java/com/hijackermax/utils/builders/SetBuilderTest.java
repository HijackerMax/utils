package com.hijackermax.utils.builders;

import com.hijackermax.utils.entities.Single;
import com.hijackermax.utils.lang.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SetBuilderTest {
    @Test
    void testAdd() {
        Set<String> set = SetBuilder.<String>of()
                .add("Foo")
                .add("Bar")
                .build();
        assertFalse(CollectionUtils.isEmpty(set));
        assertTrue(CollectionUtils.safeContainsAll(Set.of("Foo", "Bar"), set));
    }

    @Test
    void testAddAll() {
        Set<String> set = SetBuilder.of("Foo")
                .addAll(Set.of("Bar", "Test"))
                .build();
        assertFalse(CollectionUtils.isEmpty(set));
        assertTrue(CollectionUtils.safeContainsAll(Set.of("Foo", "Bar", "Test"), set));
    }

    @Test
    void testAddFromSupplier() {
        Set<String> set = SetBuilder.of(Set.of("Foo", "Bar"))
                .add(() -> "Test")
                .build();
        assertFalse(CollectionUtils.isEmpty(set));
        assertTrue(CollectionUtils.safeContainsAll(Set.of("Foo", "Bar", "Test"), set));
    }

    @Test
    void testUnmodifiable() {
        Set<String> unmodifiableset = SetBuilder.<String>of()
                .add("Foo")
                .add("Bar")
                .buildUnmodifiable();
        assertFalse(CollectionUtils.isEmpty(unmodifiableset));
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableset.add("Test"));
    }

    @Test
    void testModifiable() {
        Set<String> modifiableSet = SetBuilder.<String>of()
                .add("Foo")
                .add("Bar")
                .build();
        assertFalse(CollectionUtils.isEmpty(modifiableSet));
        assertDoesNotThrow(() -> modifiableSet.add("Test Add"));
    }

    @Test
    void testCustomConstructorSupplier() {
        Set<String> set = SetBuilder.<String>with(TreeSet::new)
                .add("Foo")
                .add("Bar")
                .build();
        assertFalse(CollectionUtils.isEmpty(set));
        assertTrue(set instanceof TreeSet);
    }

    @Test
    void testDefaultConstructorSupplier() {
        Set<String> set = SetBuilder.<String>of()
                .add("Foo")
                .add("Bar")
                .build();
        assertFalse(CollectionUtils.isEmpty(set));
        assertTrue(set instanceof HashSet);
    }

    @Test
    void testProvideUnmodifiable() {
        Single<Set<String>> wrapper = new Single<>();
        SetBuilder.<String>of()
                .add("Foo")
                .add("Bar")
                .provideUnmodifiable(wrapper::setValue);
        assertTrue(wrapper.containsValue());
        Set<String> unmodifiableSet = wrapper.getValue();
        assertFalse(CollectionUtils.isEmpty(unmodifiableSet));
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableSet.add("Test"));
    }

    @Test
    void testProvideModifiable() {
        Single<Set<String>> wrapper = new Single<>();
        SetBuilder.<String>of()
                .add("Foo")
                .add("Bar")
                .provide(wrapper::setValue);
        assertTrue(wrapper.containsValue());
        Set<String> modifiableSet = wrapper.getValue();
        assertFalse(CollectionUtils.isEmpty(modifiableSet));
        assertDoesNotThrow(() -> modifiableSet.add("Test Add"));
    }
}
