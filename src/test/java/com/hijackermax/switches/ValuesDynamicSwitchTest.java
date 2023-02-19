package com.hijackermax.switches;

import com.hijackermax.utils.switches.ValuesDynamicSwitch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValuesDynamicSwitchTest {
    @Test
    void testEqualsDynamicSwitch() {
        String value = ValuesDynamicSwitch.builder("N/A")
                .addCase(1, "FooBar")
                .addCase("Foo", "Test")
                .addCase("Bar", "Void")
                .doSwitch("Foo");

        assertEquals("Test", value);
    }

    @Test
    void testCustomDynamicSwitch() {
        String value = ValuesDynamicSwitch.builder(String::contains, "N/A")
                .addCase("oo", "Test")
                .addCase("ar", "Void")
                .doSwitch("Foo");

        assertEquals("Test", value);
    }

    @Test
    void testDefaultDynamicSwitch() {
        String value = ValuesDynamicSwitch.builder(String::contains, "N/A")
                .addCase("ar", "Void")
                .addCase("oo", "Test")
                .doSwitch("Hello");

        assertEquals("N/A", value);
    }

    @Test
    void testEqualsObjectDynamicSwitch() {
        Object value = ValuesDynamicSwitch.builder(null)
                .addCase("Double", 3.0)
                .addCase(1, "FooBar")
                .addCase("Foo", 2)
                .addCase("Bar", "Void")
                .doSwitch("Foo");

        assertEquals(2, value);
    }
}
