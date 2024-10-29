package com.mealmate.mealmate.dao;

public class ZeroRowsAffectedException extends Exception {
    public ZeroRowsAffectedException(String message) {
        super(message);
    }

    public ZeroRowsAffectedException(String message, Throwable cause) {
        super(message, cause);
    }
}
