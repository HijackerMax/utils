package com.hijackermax.utils.encoders;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

class Base58Test extends AbstractEncoderTest {

    @Override
    Function<byte[], String> getEncoder() {
        return Base58::encode;
    }

    @Override
    Function<String, byte[]> getDecoder() {
        return Base58::decode;
    }

    @Override
    byte[] getSourceForEncode() {
        return "TestString124567890FooBar!@#$%^&*()[]".getBytes(StandardCharsets.UTF_8);
    }

    @Override
    String getEncodedSource() {
        return "3q4DFPT8Jw8FG6jg9qdEpSaCvNBpejFY68kA6y6aacU2j8z18ig";
    }
}
