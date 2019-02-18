package com.zooms.dean.common.web;

import java.io.Serializable;

public class View<T> implements Serializable {

    private static final long serialVersionUID = 7180264501891460760L;

    private int code;
    private String message;
    private T data;

    public static final int STATE_OK = 20000;
    public static final int STATE_NOT_FOUND = 40004;
    public static final int STATE_NOT_AUTH = 40001;
    public static final int STATE_NO_BINDING = 40002;
    public static final int STATE_ERROR = 50000;
    public static final int STATE_ERROR_PARAMS = 50001;

    public View(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public View(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public View() {
    }

    public static <T> View<T> ofOk() {
        return new View<>(STATE_OK, "OK");
    }

    public static <T> View<T> ofOk(T data) {
        return new View<>(STATE_OK, "OK", data);
    }

    public static <T> View<T> ofOk(String message) {
        return new View<>(STATE_OK, message);
    }

    public static <T> View<T> ofOk(String message, T data) {
        return new View<>(STATE_OK, message, data);
    }

    public static <T> View<T> ofError(String message) {
        return new View<>(STATE_ERROR, message);
    }

    public static <T> View<T> ofError(int code, String message) {
        return new View<>(code, message);
    }

    public static <T> View<T> of404Error(String message) {
        return new View<>(STATE_NOT_FOUND, message);
    }

    public static <T> View<T> of401Error(String message) {
        return new View<>(STATE_NOT_AUTH, message);
    }

    public static <T> View<T> of500Error(String message) {
        return new View<>(STATE_ERROR, message);
    }

    public static <T> View<T> ofParamsError(String message) {
        return new View<>(STATE_ERROR_PARAMS, message);
    }
    
    public static View<Void> ofNoBindError(String message) {
        return new View<>(STATE_NO_BINDING, message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
