package com.zooms.dean.auth.exceptions;

/**
 * @author slacrey
 * @since 2018/1/17
 */
public class UnknownResourceException extends RuntimeException {

    public UnknownResourceException(String message) {
        super(message);
    }

    public UnknownResourceException(String message, Throwable cause) {
        super(message, cause);
    }

}
