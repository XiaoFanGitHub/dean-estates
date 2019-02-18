package com.zooms.dean.auth.common.crypto.keygen;

public interface BytesKeyGenerator {

	/**
	 * Get the length, in bytes, of keys created by this generator. Most unique keys are
	 * at least 8 bytes in length.
	 */
	int getKeyLength();

	/**
	 * Generate a new key.
	 */
	byte[] generateKey();

}
