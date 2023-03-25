package com.hijackermax.switches;

import com.hijackermax.utils.entities.Single;
import com.hijackermax.utils.switches.ActionsDynamicSwitch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActionsDynamicSwitchTest {
    @Test
    void testEqualsDynamicSwitch() {
        Single<String> wrapper = new Single<>();
        ActionsDynamicSwitch.builder()
                .addCase(1, () -> wrapper.setValue("FooBar"))
                .addCase("Foo", () -> wrapper.setValue("Test"))
                .addCase("Bar", () -> wrapper.setValue("Void"))
                .doSwitch("Foo");

        assertEquals("Test", wrapper.getValue());
    }

    @Test
    void testCustomDynamicSwitch() {
        Single<String> wrapper = new Single<>();
        ActionsDynamicSwitch.builder(String::contains)
                .addDefault(() -> wrapper.setValue("N/A"))
                .addCase("oo", () -> wrapper.setValue("Test"))
                .addCase("ar", () -> wrapper.setValue("Void"))
                .doSwitch("Foo");

        assertEquals("Test", wrapper.getValue());
    }

    @Test
    void testDefaultDynamicSwitch() {
        Single<String> wrapper = new Single<>();
        ActionsDynamicSwitch.builder(String::contains)
                .addDefault(() -> wrapper.setValue("N/A"))
                .addCase("ar", () -> wrapper.setValue("Void"))
                .addCase("oo", () -> wrapper.setValue("Test"))
                .doSwitch("Hello");

        assertEquals("N/A", wrapper.getValue());
    }
}
