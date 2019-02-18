package com.zooms.dean.auth.common.crypto.keygen;

import com.zooms.dean.auth.common.crypto.codec.Hex;

final class HexEncodingStringKeyGenerator implements StringKeyGenerator {

    private final BytesKeyGenerator keyGenerator;

    public HexEncodingStringKeyGenerator(BytesKeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Override
    public String generateKey() {
        return new String(Hex.encode(keyGenerator.generateKey()));
    }

}
