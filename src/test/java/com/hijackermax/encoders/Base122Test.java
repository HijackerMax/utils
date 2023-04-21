package com.hijackermax.encoders;

import com.hijackermax.utils.encoders.Base122;

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
}
