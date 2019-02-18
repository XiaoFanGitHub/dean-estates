package com.zooms.dean.auth.common.crypto.password;

import com.zooms.dean.auth.common.crypto.codec.Hex;
import com.zooms.dean.auth.common.crypto.codec.Utf8;
import com.zooms.dean.auth.common.crypto.keygen.BytesKeyGenerator;
import com.zooms.dean.auth.common.crypto.keygen.KeyGenerators;
import com.zooms.dean.auth.common.crypto.util.EncodingUtils;

public final class StandardPasswordEncoder implements PasswordEncoder {
    private final Digester digester;
    private final byte[] secret;
    private final BytesKeyGenerator saltGenerator;
    private static final int DEFAULT_ITERATIONS = 1024;

    public StandardPasswordEncoder() {
        this("");
    }

    public StandardPasswordEncoder(CharSequence secret) {
        this("SHA-256", secret);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return this.encode(rawPassword, this.saltGenerator.generateKey());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        byte[] digested = this.decode(encodedPassword);
        byte[] salt = EncodingUtils.subArray(digested, 0, this.saltGenerator.getKeyLength());
        return this.matches(digested, this.digest(rawPassword, salt));
    }

    private StandardPasswordEncoder(String algorithm, CharSequence secret) {
        this.digester = new Digester(algorithm, 1024);
        this.secret = Utf8.encode(secret);
        this.saltGenerator = KeyGenerators.secureRandom();
    }

    private String encode(CharSequence rawPassword, byte[] salt) {
        byte[] digest = this.digest(rawPassword, salt);
        return new String(Hex.encode(digest));
    }

    private byte[] digest(CharSequence rawPassword, byte[] salt) {
        byte[] digest = this.digester.digest(EncodingUtils.concatenate(new byte[][]{salt, this.secret, Utf8.encode(rawPassword)}));
        return EncodingUtils.concatenate(new byte[][]{salt, digest});
    }

    private byte[] decode(CharSequence encodedPassword) {
        return Hex.decode(encodedPassword);
    }

    private boolean matches(byte[] expected, byte[] actual) {
        if (expected.length != actual.length) {
            return false;
        } else {
            int result = 0;

            for (int i = 0; i < expected.length; ++i) {
                result |= expected[i] ^ actual[i];
            }

            return result == 0;
        }
    }
}
