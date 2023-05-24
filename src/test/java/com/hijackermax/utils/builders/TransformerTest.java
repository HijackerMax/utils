package com.hijackermax.utils.builders;

import com.hijackermax.utils.entities.Single;
import com.hijackermax.utils.lang.MathUtils;
import com.hijackermax.utils.lang.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransformerTest {
    @Test
    void testStringTransformation() {
        String result = Transformer.of(" Hello World Foo Bar 123456 ")
                .run(StringUtils::trimToEmpty)
                .run(StringUtils::removeWhitespaces)
                .run(StringUtils::removeDigits)
                .run(StringUtils.replace(Map.of("o", "0", "l", "1")))
                .result();
        assertFalse(StringUtils.isEmpty(result));
        assertEquals("He110W0r1dF00Bar", result);
    }

    @Test
    void testNumberTransformation() {
        Integer result = Transformer.of(256)
                .run(MathUtils.subtract(206))
                .run(MathUtils.multiply(2))
                .run(MathUtils.subtract(99))
                .result();
        assertEquals(1, result);
    }

    @Test
    void testProvideTransformationResult() {
        Single<String> resultWrapper = new Single<>();
        Transformer.of(" Hello World Foo Bar 123456 ")
                .run(StringUtils::trimToEmpty)
                .run(StringUtils::removeWhitespaces)
                .run(StringUtils::removeDigits)
                .provide(resultWrapper::setValue);
        assertTrue(resultWrapper.containsValue());
        String result = resultWrapper.getValue();
        assertFalse(StringUtils.isEmpty(result));
        assertEquals("HelloWorldFooBar", result);
    }

    @Test
    void testNullChecks() {
        assertThrows(NullPointerException.class, () -> Transformer.of(null));
        assertThrows(NullPointerException.class, () -> Transformer.of("Test").run(s -> null));
    }

    @Test
    void testTransformationEvolve() {
        Integer result = Transformer.of(" Hello World Foo Bar 123456 ")
                .run(StringUtils::trimToEmpty)
                .run(StringUtils::removeWhitespaces)
                .run(StringUtils::removeDigits)
                .evolve(String::length)
                .result();
        assertEquals(16, result);
    }
}
