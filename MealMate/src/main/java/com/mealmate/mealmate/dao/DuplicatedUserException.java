package com.mealmate.mealmate.dao;

public class DuplicatedUserException extends Exception{
    public DuplicatedUserException(String message) {
        super(message);
    }
}
