package com.mealmate.mealmate.dao;

public class MismatchIdException extends Exception {
    public MismatchIdException(String message) {
        super(message);
    }

    public MismatchIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
