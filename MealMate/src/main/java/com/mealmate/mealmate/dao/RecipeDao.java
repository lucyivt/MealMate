package com.mealmate.mealmate.dao;

import com.mealmate.mealmate.dto.Ingredient;
import com.mealmate.mealmate.dto.Recipe;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface RecipeDao {

    Recipe addRecipe(Recipe recipe);

    Recipe getRecipe(int id) throws RecipeNotFoundException;

    List<Recipe> getAllRecipes(int userId);

    void updateRecipe(Recipe recipe) throws DataAccessException;

    boolean deleteRecipe(int id);

    // -------- Alter recipeIngredient table -----------------

    void addIngredientToRecipe(int recipeId, Ingredient ingredient);

    List<Ingredient> getRecipeIngredients(int recipeId) throws DataAccessException;

    void deleteAllIngredientsFromRecipe(int recipeId);

}
