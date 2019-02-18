package com.zooms.dean.auth.exceptions;

/**
 * @author slacrey
 * @since 2018/1/17
 */
public class InternalException extends RuntimeException {

    public InternalException(String message) {
        super(message);
    }

    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
