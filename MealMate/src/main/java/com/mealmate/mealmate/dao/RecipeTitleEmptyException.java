package com.mealmate.mealmate.dao;

public class RecipeTitleEmptyException extends Exception {
  public RecipeTitleEmptyException(String message) {
    super(message);
  }

  public RecipeTitleEmptyException(String message, Throwable cause) {
    super(message, cause);
  }
}
