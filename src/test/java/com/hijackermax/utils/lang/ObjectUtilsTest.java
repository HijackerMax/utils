package com.hijackermax.utils.lang;

import com.hijackermax.utils.encoders.Base122;
import com.hijackermax.utils.entities.Single;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObjectUtilsTest {
    @Test
    void testValueOrDefault() {
        assertEquals(5, ObjectUtils.valueOrDefault(null, 5));
        assertEquals(2, ObjectUtils.valueOrDefault(2, 5));
        assertEquals(7, ObjectUtils.valueOrDefault(7, null));
        assertNull(ObjectUtils.valueOrDefault(null, null));
    }

    @Test
    void testMapValueOrDefault() {
        assertEquals("5", ObjectUtils.mapValueOrDefault(null, String::valueOf, "5"));
        assertEquals("2", ObjectUtils.mapValueOrDefault(2, String::valueOf, "5"));
        assertEquals("7", ObjectUtils.mapValueOrDefault(7, String::valueOf, null));
        assertNull(ObjectUtils.mapValueOrDefault(null, String::valueOf, null));
        assertThrows(NullPointerException.class, () -> ObjectUtils.mapValueOrDefault(2, null, 3));
    }

    @Test
    void testCompressDecompress() throws IOException {
        int length = 2000;
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz<>%!@#$%^&*()_+-='\"/.[]ÆØÅÖÄåæøІіЇїЄєҐґ$€¥¢£₴".toCharArray();
        String source = RandomUtils.randomStringSequence(length, chars);
        byte[] sourceBytes = source.getBytes(StandardCharsets.UTF_8);
        String compressedBytes = ObjectUtils.compress(sourceBytes);
        byte[] decompressedBytes = ObjectUtils.decompress(compressedBytes);
        assertArrayEquals(sourceBytes, decompressedBytes);
    }

    @Test
    void testCompressDecompressCustom() throws IOException {
        int length = 2000;
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz<>%!@#$%^&*()_+-='\"/.[]ÆØÅÖÄåæøІіЇїЄєҐґ$€¥¢£₴".toCharArray();
        String source = RandomUtils.randomStringSequence(length, chars);
        byte[] sourceBytes = source.getBytes(StandardCharsets.UTF_8);
        String compressedBytes = ObjectUtils.compress(sourceBytes, Base122::encode);
        byte[] decompressedBytes = ObjectUtils.decompress(compressedBytes, Base122::decode);
        assertArrayEquals(sourceBytes, decompressedBytes);
    }

    @Test
    void testValueOrGetDefault() {
        assertEquals(5, ObjectUtils.valueOrGetDefault(null, () -> 5));
        assertEquals(2, ObjectUtils.valueOrGetDefault(2, () -> 5));
        assertEquals(7, ObjectUtils.valueOrGetDefault(7, () -> null));
        assertNull(ObjectUtils.valueOrGetDefault(null, () -> null));
    }

    @Test
    void testIfNotNull() {
        Single<String> holder = new Single<>("Foo");
        ObjectUtils.ifNotNull(null, holder::setValue);
        assertTrue(holder.containsValue());
        assertEquals("Foo", holder.getValue());
        ObjectUtils.ifNotNull("Bar", holder::setValue);
        assertTrue(holder.containsValue());
        assertEquals("Bar", holder.getValue());
    }
}
