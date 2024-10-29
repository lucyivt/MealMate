package com.mealmate.mealmate.dao;


import com.mealmate.mealmate.dto.Nutrient;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface NutrientDao {

    List<Nutrient> createNutrientsForRecipe(List<Nutrient> nutrients, int recipeId)
            throws DataAccessException;

    List<Nutrient> getNutrientsForRecipe(int recipeId) throws DataAccessException;

    void updateNutrientsForRecipe(List<Nutrient> nutrients, int recipeId);
}
