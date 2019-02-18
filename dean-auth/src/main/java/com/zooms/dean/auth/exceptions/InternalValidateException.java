package com.zooms.dean.auth.exceptions;

public class InternalValidateException extends RuntimeException {

    public InternalValidateException(String message) {
        super(message);
    }

    public InternalValidateException(String message, Throwable cause) {
        super(message, cause);
    }
}
