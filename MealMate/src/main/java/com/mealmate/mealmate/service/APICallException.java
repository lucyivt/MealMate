package com.mealmate.mealmate.service;

public class APICallException extends RuntimeException {
    public APICallException(String message) {
        super(message);
    }

    public APICallException(String message, Throwable cause) {
        super(message, cause);
    }
}
