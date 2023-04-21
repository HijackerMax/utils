package com.hijackermax.encoders;

import com.hijackermax.utils.encoders.Base58;

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
}
