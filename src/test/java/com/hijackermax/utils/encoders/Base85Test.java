package com.hijackermax.utils.encoders;

import java.nio.charset.StandardCharsets;
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

    @Override
    byte[] getSourceForEncode() {
        return "TestString124567890FooBar!@#$%^&*()[]".getBytes(StandardCharsets.UTF_8);
    }

    @Override
    String getEncodedSource() {
        return "<+U,m;fm%oDJ([Z1c70M3&rZ^Df7sNEZm[m,UHbD.OZ`M>l";
    }
}
