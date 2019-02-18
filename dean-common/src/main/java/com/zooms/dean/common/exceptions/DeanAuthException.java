package com.zooms.dean.common.exceptions;

/**
 * @author slacrey
 * @since 2018/1/20
 */
public class DeanAuthException extends RuntimeException {

    private static final long serialVersionUID = 4425505139268329864L;

    public DeanAuthException(String message) {
        super(message);
    }

    public DeanAuthException(String message, Throwable cause) {
        super(message, cause);
    }

}
