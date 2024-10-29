package com.mealmate.mealmate.dao;

import com.mealmate.mealmate.dto.Ingredient;

import java.util.List;

public interface IngredientDao {

    Ingredient getIngredient(int ingredientId);

    public List<Ingredient> getAllIngredients();

    Ingredient addIngredient(Ingredient ingredient);

    void deleteIngredient(Ingredient ingredient);

}
