package com.mealmate.mealmate.dao;

public class NutrientNotFoundException extends RuntimeException {
    public NutrientNotFoundException(String message) {
        super(message);
    }

    public NutrientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
