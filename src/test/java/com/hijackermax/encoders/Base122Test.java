package com.hijackermax.encoders;

import com.hijackermax.utils.encoders.Base122;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

class Base122Test extends AbstractEncoderTest {
    @Override
    Function<byte[], String> getEncoder() {
        return Base122::encode;
    }

    @Override
    Function<String, byte[]> getDecoder() {
        return Base122::decode;
    }

    @Override
    byte[] getSourceForEncode() {
        return "TestString124567890FooBar!@#$%^&*()[]".getBytes(StandardCharsets.UTF_8);
    }

    @Override
    String getEncodedSource() {
        return "*\u0019.7ύhr4[Ls\tHh5\u001B˧\u0003IA\fo7PL\u0017\u0011\u0005£\u0012\t+b1(P)-W ";
    }
}
