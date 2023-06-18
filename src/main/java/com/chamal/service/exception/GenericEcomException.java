package com.chamal.service.exception;

public class GenericEcomException extends RuntimeException{
    public GenericEcomException(String message) {
        super(message);
    }

    public GenericEcomException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericEcomException(Throwable cause) {
        super(cause);
    }

    protected GenericEcomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
