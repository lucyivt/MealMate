package com.mealmate.mealmate.dao;

import com.mealmate.mealmate.dao.mapper.IngredientMapper;
import com.mealmate.mealmate.dao.mapper.NutrientMapper;
import com.mealmate.mealmate.dto.Ingredient;
import com.mealmate.mealmate.dto.Nutrient;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NutrientDaoDBImpl implements NutrientDao {

    private final JdbcTemplate jdbcTemplate;

    public NutrientDaoDBImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Nutrient> createNutrientsForRecipe(List<Nutrient> nutrients, int recipeId)
            throws DataAccessException {
        List<Nutrient> createdNutrients = new ArrayList<>();
        for (Nutrient nutrient : nutrients) {
               Nutrient insertedNutrient = createNutrientForRecipe(nutrient, recipeId);
               createdNutrients.add(insertedNutrient);
        }
        return createdNutrients;
    }

    @Override
    public List<Nutrient> getNutrientsForRecipe(int recipeId)
            throws DataAccessException {
        final String sql = "SELECT * FROM nutrient WHERE recipeId = ?";
        return jdbcTemplate.query(sql, new NutrientMapper(), recipeId);
    }

    @Override
    public void updateNutrientsForRecipe(List<Nutrient> nutrients, int recipeId) {
        // Delete all nutrients for this recipe
        deleteAllNutrientsFromRecipe(recipeId);

        // Create new nutrients
        createNutrientsForRecipe(nutrients, recipeId);
    }

    private Nutrient createNutrientForRecipe(Nutrient nutrient, int recipeId)
            throws DataAccessException {
        final String sql = "INSERT INTO nutrient (label, quantity, unit, recipeId) VALUES (?, ?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nutrient.getLabel());
            ps.setDouble(2, nutrient.getQuantity());
            ps.setString(3, nutrient.getUnit());
            ps.setInt(4, recipeId);
            return ps;
        }, keyHolder);
        nutrient.setNutrientId(keyHolder.getKey().intValue());

        return nutrient;
    }

    private void deleteAllNutrientsFromRecipe(int recipeId)
            throws DataAccessException {
        final String sql = "DELETE FROM nutrient WHERE recipeId = ?";
        jdbcTemplate.update(sql, recipeId);
    }
}
