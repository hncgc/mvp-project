package com.pccb.newapp.net.http.exception;

/**
 * 网络异常
 */

public class ApiException extends RuntimeException {

    public final String code;
    public final String message;

    public ApiException(String code) {
        this(code, null);
    }

    public ApiException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
