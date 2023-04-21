package com.hijackermax.encoders;

import com.hijackermax.utils.encoders.Base85;

import java.util.function.Function;

class Base85Test extends AbstractEncoderTest {
    @Override
    Function<byte[], String> getEncoder() {
        return Base85::encode;
    }

    @Override
    Function<String, byte[]> getDecoder() {
        return Base85::decode;
    }
}
