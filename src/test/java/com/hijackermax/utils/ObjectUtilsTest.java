package com.hijackermax.utils;

import com.hijackermax.utils.lang.ObjectUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ObjectUtilsTest {
    @Test
    void testValueOrDefault() {
        assertEquals(5, ObjectUtils.valueOrDefault(null, 5));
        assertEquals(2, ObjectUtils.valueOrDefault(2, 5));
        assertEquals(7, ObjectUtils.valueOrDefault(7, null));
        assertNull(ObjectUtils.valueOrDefault(null, null));
    }
}
