package com.hijackermax.switches;

import com.hijackermax.utils.entities.Single;
import com.hijackermax.utils.entities.Tuple;
import com.hijackermax.utils.switches.ClassDynamicSwitch;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClassDynamicSwitchTest {
    @Test
    void testClassDynamicSwitchInstance() {
        Single<String> wrapper = new Single<>();

        ClassDynamicSwitch.instanceBuilder()
                .addCase(Double.class, d -> wrapper.setValue(String.valueOf(d)))
                .addCase(Integer.class, i -> wrapper.setValue(String.valueOf(i)))
                .addCase(String.class, wrapper::setValue)
                .addDefault(() -> wrapper.setValue("N/A"))
                .doSwitch("Foo bar");

        assertEquals("Foo bar", wrapper.getValue());
    }

    @Test
    void testClassDynamicSwitchAssignable() {
        Single<String> wrapper = new Single<>();

        ClassDynamicSwitch.assignableBuilder()
                .addCase(List.class, d -> wrapper.setValue(String.valueOf(d.size())))
                .addCase(Number.class, i -> wrapper.setValue(String.valueOf(i)))
                .addCase(String.class, wrapper::setValue)
                .addDefault(() -> wrapper.setValue("N/A"))
                .doSwitch(128D);

        assertEquals("128.0", wrapper.getValue());
    }

    @Test
    void testClassDynamicSwitchInstanceDefault() {
        Single<String> wrapper = new Single<>();

        ClassDynamicSwitch.instanceBuilder()
                .addCase(Double.class, d -> wrapper.setValue(String.valueOf(d)))
                .addCase(Integer.class, i -> wrapper.setValue(String.valueOf(i)))
                .addCase(String.class, wrapper::setValue)
                .addDefault(() -> wrapper.setValue("N/A"))
                .doSwitch(new Tuple<>());

        assertEquals("N/A", wrapper.getValue());
    }

    @Test
    void testClassDynamicSwitchAssignableDefault() {
        Single<String> wrapper = new Single<>();

        ClassDynamicSwitch.assignableBuilder()
                .addCase(List.class, d -> wrapper.setValue(String.valueOf(d.size())))
                .addCase(Number.class, i -> wrapper.setValue(String.valueOf(i)))
                .addCase(String.class, wrapper::setValue)
                .addDefault(() -> wrapper.setValue("N/A"))
                .doSwitch(Collections.emptyMap());

        assertEquals("N/A", wrapper.getValue());
    }

    @Test
    void testClassDynamicSwitchInstanceThrow() {
        Single<String> wrapper = new Single<>();

        ClassDynamicSwitch classDynamicSwitch = ClassDynamicSwitch.instanceBuilder()
                .addCase(Double.class, d -> wrapper.setValue(String.valueOf(d)))
                .addCase(Integer.class, i -> wrapper.setValue(String.valueOf(i)))
                .addCase(String.class, wrapper::setValue);

        Tuple<Object, Object> value = new Tuple<>();
        assertThrows(IllegalArgumentException.class, () -> classDynamicSwitch.doSwitchOrThrow(value, IllegalArgumentException::new));
        assertThrows(NullPointerException.class, () -> classDynamicSwitch.doSwitchOrThrow(value, null));
    }

    @Test
    void testClassDynamicSwitchAssignableThrow() {
        Single<String> wrapper = new Single<>();

        ClassDynamicSwitch classDynamicSwitch = ClassDynamicSwitch.assignableBuilder()
                .addCase(List.class, d -> wrapper.setValue(String.valueOf(d.size())))
                .addCase(Number.class, i -> wrapper.setValue(String.valueOf(i)))
                .addCase(String.class, wrapper::setValue)
                .addDefault(() -> wrapper.setValue("N/A"));

        Map<Object, Object> value = Collections.emptyMap();
        assertThrows(IllegalArgumentException.class, () -> classDynamicSwitch.doSwitchOrThrow(value, IllegalArgumentException::new));
        assertThrows(NullPointerException.class, () -> classDynamicSwitch.doSwitchOrThrow(value, null));
    }
}
