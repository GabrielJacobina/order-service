package com.order.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private HttpStatus status;

    public CustomException(String message, HttpStatus status) {
        this(message, status, null);
    }

    public CustomException(String message, HttpStatus status, Exception e) {
        super(message, e);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
