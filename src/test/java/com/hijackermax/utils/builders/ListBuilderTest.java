package com.hijackermax.utils.builders;

import com.hijackermax.utils.entities.Single;
import com.hijackermax.utils.lang.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListBuilderTest {
    @Test
    void testAdd() {
        List<String> list = ListBuilder.<String>of()
                .add("Foo")
                .add("Bar")
                .build();
        assertFalse(CollectionUtils.isEmpty(list));
        assertLinesMatch(List.of("Foo", "Bar"), list);
    }

    @Test
    void testAddAll() {
        List<String> list = ListBuilder.of("Foo")
                .addAll(List.of("Bar", "Test"))
                .build();
        assertFalse(CollectionUtils.isEmpty(list));
        assertLinesMatch(List.of("Foo", "Bar", "Test"), list);
    }

    @Test
    void testAddFromSupplier() {
        List<String> list = ListBuilder.of(List.of("Foo", "Bar"))
                .add(() -> "Test")
                .build();
        assertFalse(CollectionUtils.isEmpty(list));
        assertLinesMatch(List.of("Foo", "Bar", "Test"), list);
    }

    @Test
    void testUnmodifiable() {
        List<String> unmodifiableList = ListBuilder.<String>of()
                .add("Foo")
                .add("Bar")
                .buildUnmodifiable();
        assertFalse(CollectionUtils.isEmpty(unmodifiableList));
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add("Test"));
    }

    @Test
    void testModifiable() {
        List<String> modifiableList = ListBuilder.<String>of()
                .add("Foo")
                .add("Bar")
                .build();
        assertFalse(CollectionUtils.isEmpty(modifiableList));
        assertDoesNotThrow(() -> modifiableList.add("Test Add"));
    }

    @Test
    void testCustomConstructorSupplier() {
        List<String> list = ListBuilder.<String>with(LinkedList::new)
                .add("Foo")
                .add("Bar")
                .build();
        assertFalse(CollectionUtils.isEmpty(list));
        assertTrue(list instanceof LinkedList);
    }

    @Test
    void testDefaultConstructorSupplier() {
        List<String> list = ListBuilder.<String>of()
                .add("Foo")
                .add("Bar")
                .build();
        assertFalse(CollectionUtils.isEmpty(list));
        assertTrue(list instanceof ArrayList);
    }

    @Test
    void testProvideUnmodifiable() {
        Single<List<String>> wrapper = new Single<>();
        ListBuilder.<String>of()
                .add("Foo")
                .add("Bar")
                .provideUnmodifiable(wrapper::setValue);
        assertTrue(wrapper.containsValue());
        List<String> unmodifiableList = wrapper.getValue();
        assertFalse(CollectionUtils.isEmpty(unmodifiableList));
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add("Test"));
    }

    @Test
    void testProvideModifiable() {
        Single<List<String>> wrapper = new Single<>();
        ListBuilder.<String>of()
                .add("Foo")
                .add("Bar")
                .provide(wrapper::setValue);
        assertTrue(wrapper.containsValue());
        List<String> modifiableList = wrapper.getValue();
        assertFalse(CollectionUtils.isEmpty(modifiableList));
        assertDoesNotThrow(() -> modifiableList.add("Test Add"));
    }
}
