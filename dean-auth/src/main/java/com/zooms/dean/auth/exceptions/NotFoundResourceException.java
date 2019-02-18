package com.zooms.dean.auth.exceptions;

/**
 * @author linfeng
 */
public class NotFoundResourceException extends RuntimeException {

    private static final long serialVersionUID = -7220299142306865870L;

    public NotFoundResourceException(String message) {
        super(message);
    }

    public NotFoundResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
