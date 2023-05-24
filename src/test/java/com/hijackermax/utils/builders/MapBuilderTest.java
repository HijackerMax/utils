package com.hijackermax.utils.builders;

import com.hijackermax.utils.aux.Colors;
import com.hijackermax.utils.entities.Single;
import com.hijackermax.utils.lang.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapBuilderTest {
    @Test
    void testPut() {
        Map<String, Integer> map = MapBuilder.<String, Integer>of()
                .put("One", 1)
                .put("Two", 2)
                .put("Three", 3)
                .build();
        assertFalse(CollectionUtils.isEmpty(map));
        assertEquals(3, map.size());
        assertEquals(map.get("One"), 1);
        assertEquals(map.get("Two"), 2);
        assertEquals(map.get("Three"), 3);
    }

    @Test
    void testPutAll() {
        Map<String, Integer> map = MapBuilder.of("One", 1)
                .putAll(Map.of("Two", 2, "Three", 3))
                .build();
        assertFalse(CollectionUtils.isEmpty(map));
        assertEquals(3, map.size());
        assertEquals(map.get("One"), 1);
        assertEquals(map.get("Two"), 2);
        assertEquals(map.get("Three"), 3);
    }

    @Test
    void testPutIfAbsent() {
        Map<String, Integer> map = MapBuilder.of(Map.of("One", 10))
                .putIfAbsent("One", 1)
                .putIfAbsent("Two", 2)
                .putIfAbsent("Two", 20)
                .putIfAbsent("Three", 3)
                .build();
        assertFalse(CollectionUtils.isEmpty(map));
        assertEquals(3, map.size());
        assertEquals(map.get("One"), 10);
        assertEquals(map.get("Two"), 2);
        assertEquals(map.get("Three"), 3);
    }

    @Test
    void testUnmodifiable() {
        Map<String, Integer> unmodifiableMap = MapBuilder.<String, Integer>of()
                .put("One", 1)
                .put("Two", 2)
                .put("Three", 3)
                .buildUnmodifiable();
        assertFalse(CollectionUtils.isEmpty(unmodifiableMap));
        assertFalse(CollectionUtils.isEmpty(unmodifiableMap));
        assertEquals(3, unmodifiableMap.size());
        assertEquals(unmodifiableMap.get("One"), 1);
        assertEquals(unmodifiableMap.get("Two"), 2);
        assertEquals(unmodifiableMap.get("Three"), 3);
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableMap.put("Four", 4));
    }

    @Test
    void testModifiable() {
        Map<String, Integer> unmodifiableMap = MapBuilder.<String, Integer>of()
                .put("One", 1)
                .put("Two", 2)
                .put("Three", 3)
                .build();
        assertFalse(CollectionUtils.isEmpty(unmodifiableMap));
        assertDoesNotThrow(() -> unmodifiableMap.put("Four", 4));
    }

    @Test
    void testCustomConstructorSupplier() {
        Map<String, Integer> map = MapBuilder.<String, Integer>with(TreeMap::new)
                .put("One", 1)
                .put("Two", 2)
                .put("Three", 3)
                .build();
        assertFalse(CollectionUtils.isEmpty(map));
        assertEquals(3, map.size());
        assertEquals(map.get("One"), 1);
        assertEquals(map.get("Two"), 2);
        assertEquals(map.get("Three"), 3);
        assertTrue(map instanceof TreeMap);
    }

    @Test
    void testEnumMapConstructorSupplier() {
        Map<Colors, String> map = MapBuilder.<Colors, String>ofEnum(Colors.class)
                .put(Colors.RED, "FF0000")
                .put(Colors.GREEN, "00FF00")
                .put(Colors.BLUE, "0000FF")
                .build();
        assertFalse(CollectionUtils.isEmpty(map));
        assertEquals(3, map.size());
        assertEquals(map.get(Colors.RED), "FF0000");
        assertEquals(map.get(Colors.GREEN), "00FF00");
        assertEquals(map.get(Colors.BLUE), "0000FF");
        assertTrue(map instanceof EnumMap);
    }

    @Test
    void testDefaultConstructorSupplier() {
        Map<String, Integer> map = MapBuilder.<String, Integer>of()
                .put("One", 1)
                .put("Two", 2)
                .put("Three", 3)
                .build();
        assertFalse(CollectionUtils.isEmpty(map));
        assertEquals(3, map.size());
        assertEquals(map.get("One"), 1);
        assertEquals(map.get("Two"), 2);
        assertEquals(map.get("Three"), 3);
        assertTrue(map instanceof HashMap);
    }

    @Test
    void testProvideUnmodifiable() {
        Single<Map<String, Integer>> wrapper = new Single<>();
        MapBuilder.<String, Integer>of()
                .put("One", 1)
                .put("Two", 2)
                .put("Three", 3)
                .provideUnmodifiable(wrapper::setValue);
        assertTrue(wrapper.containsValue());
        Map<String, Integer> unmodifiableMap = wrapper.getValue();
        assertFalse(CollectionUtils.isEmpty(unmodifiableMap));
        assertEquals(3, unmodifiableMap.size());
        assertEquals(unmodifiableMap.get("One"), 1);
        assertEquals(unmodifiableMap.get("Two"), 2);
        assertEquals(unmodifiableMap.get("Three"), 3);
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableMap.put("Four", 4));
    }

    @Test
    void testProvideModifiable() {
        Single<Map<String, Integer>> wrapper = new Single<>();
        MapBuilder.<String, Integer>of()
                .put("One", 1)
                .put("Two", 2)
                .put("Three", 3)
                .provide(wrapper::setValue);
        assertTrue(wrapper.containsValue());
        Map<String, Integer> modifiableMap = wrapper.getValue();
        assertFalse(CollectionUtils.isEmpty(modifiableMap));
        assertEquals(3, modifiableMap.size());
        assertEquals(modifiableMap.get("One"), 1);
        assertEquals(modifiableMap.get("Two"), 2);
        assertEquals(modifiableMap.get("Three"), 3);
        assertDoesNotThrow(() -> modifiableMap.put("Four", 4));
    }
}
