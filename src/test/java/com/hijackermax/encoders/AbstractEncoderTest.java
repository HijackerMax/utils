package com.hijackermax.encoders;

import com.hijackermax.utils.lang.RandomUtils;
import com.hijackermax.utils.lang.StringUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractEncoderTest {
    @Test
    void testEncodeDecode() {
        runVariableLengthEncodeDecode(2000);
    }

    @Test
    void testEncodeDecodeEmpty() {
        String source = StringUtils.EMPTY;
        byte[] sourceBytes = source.getBytes(StandardCharsets.UTF_8);
        String encoded = getEncoder().apply(sourceBytes);
        assertTrue(encoded.isEmpty());
        byte[] decodedBytes = getDecoder().apply(encoded);
        String decoded = new String(decodedBytes, StandardCharsets.UTF_8);
        assertEquals(source, decoded);
    }

    @Test
    void testVariableLengthEncodeDecode() {
        IntStream.range(1, 32).forEach(this::runVariableLengthEncodeDecode);
    }

    private void runVariableLengthEncodeDecode(int length) {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz<>%!@#$%^&*()_+-='\"/.[]ÆØÅÖÄåæøІіЇїЄєҐґ$€¥¢£₴".toCharArray();
        String source = RandomUtils.randomStringSequence(length, chars);
        byte[] sourceBytes = source.getBytes(StandardCharsets.UTF_8);
        String encoded = getEncoder().apply(sourceBytes);
        assertFalse(encoded.isEmpty());
        byte[] decodedBytes = getDecoder().apply(encoded);
        String decoded = new String(decodedBytes, StandardCharsets.UTF_8);
        assertEquals(source, decoded, String.format("Source and decode don't match, source length %d", length));
    }

    @Test
    void testEncodeDecodeStatic() {
        byte[] sourceBytes = getSourceForEncode();
        String encoded = getEncoder().apply(sourceBytes);
        assertFalse(encoded.isEmpty());
        assertEquals(getEncodedSource(), encoded);
        byte[] decodedBytes = getDecoder().apply(encoded);
        assertArrayEquals(sourceBytes, decodedBytes);
    }

    abstract Function<byte[], String> getEncoder();

    abstract Function<String, byte[]> getDecoder();

    abstract byte[] getSourceForEncode();

    abstract String getEncodedSource();
}
