package com.zooms.dean.auth.config.async;

public class AsyncException extends Exception {
    private int code;
    private String errorMessage;

    public AsyncException(int code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public AsyncException(String message, int code, String errorMessage) {
        super(message);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public AsyncException(String message, Throwable cause, int code, String errorMessage) {
        super(message, cause);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public AsyncException(Throwable cause, int code, String errorMessage) {
        super(cause);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public AsyncException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code, String errorMessage) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
