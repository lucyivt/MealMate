package com.mealmate.mealmate.service;

import java.util.List;

import com.mealmate.mealmate.dao.RecipeNotFoundException;
import com.mealmate.mealmate.dao.RecipeTitleEmptyException;
import com.mealmate.mealmate.dao.ZeroRowsAffectedException;
import com.mealmate.mealmate.dao.MismatchIdException;
import com.mealmate.mealmate.dto.Ingredient;
import com.mealmate.mealmate.dto.Recipe;
import org.springframework.dao.DataAccessException;

public interface RecipeService {

    Recipe addRecipe(Recipe recipe) throws RecipeTitleEmptyException;

    List<Recipe> getAllRecipes(int userId);

    Recipe findRecipeById(int recipeId) throws RecipeNotFoundException;

    Recipe updateRecipe(int id, Recipe recipe)
            throws DataAccessException, MismatchIdException, RecipeNotFoundException;

    void deleteRecipe(int id) throws RecipeNotFoundException;

}
