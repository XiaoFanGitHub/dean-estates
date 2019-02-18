package com.zooms.dean.auth.exceptions;

public class RequestValidateException extends RuntimeException {

    public RequestValidateException(String message) {
        super(message);
    }

    public RequestValidateException(String message, Throwable cause) {
        super(message, cause);
    }

}
