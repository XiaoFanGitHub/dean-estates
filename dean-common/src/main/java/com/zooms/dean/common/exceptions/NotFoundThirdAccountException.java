package com.zooms.dean.common.exceptions;

/**
 * @author zhaolijin
 */
public class NotFoundThirdAccountException extends RuntimeException {

    private static final long serialVersionUID = -7220299142306865870L;

    public NotFoundThirdAccountException(String message) {
        super(message);
    }

    public NotFoundThirdAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
