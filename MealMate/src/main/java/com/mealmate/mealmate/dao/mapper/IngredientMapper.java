package com.mealmate.mealmate.dao.mapper;

import com.mealmate.mealmate.dto.Ingredient;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientMapper implements RowMapper<Ingredient> {
    @Override
    public Ingredient mapRow(ResultSet rs, int index) throws SQLException {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(rs.getInt("ingredientId"));
        ingredient.setName(rs.getString("ingredientName"));
        return ingredient;
    }
}
