package com.hijackermax.encoders;

import com.hijackermax.utils.encoders.Base32;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

class Base32Test extends AbstractEncoderTest {

    @Override
    Function<byte[], String> getEncoder() {
        return Base32::encode;
    }

    @Override
    Function<String, byte[]> getDecoder() {
        return Base32::decode;
    }

    @Override
    byte[] getSourceForEncode() {
        return "TestString124567890FooBar!@#$%^&*()[]".getBytes(StandardCharsets.UTF_8);
    }

    @Override
    String getEncodedSource() {
        return "AHJQ6X2KEHS6JVK764S38D9P6WW3JC26DXQM4RBJ45026915BRK2MA19BDEG";
    }
}
