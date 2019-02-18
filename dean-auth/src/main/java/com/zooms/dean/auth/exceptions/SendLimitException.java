package com.zooms.dean.auth.exceptions;

public class SendLimitException extends RuntimeException {

    public SendLimitException(String message) {
        super(message);
    }

    public SendLimitException(String message, Throwable cause) {
        super(message, cause);
    }

}
