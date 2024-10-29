package com.mealmate.mealmate.dao.mapper;

import com.mealmate.mealmate.dto.Recipe;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeMapper implements RowMapper<Recipe> {
    @Override
    public Recipe mapRow(ResultSet rs, int index) throws SQLException {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(rs.getInt("recipeId"));
        recipe.setTitle(rs.getString("title"));
        recipe.setUrl(rs.getString("url"));
        recipe.setSummary(rs.getString("summary"));
        recipe.setYield(rs.getString("yield"));
        recipe.setTime(rs.getString("time"));
        recipe.setImg(rs.getString("img"));
        recipe.setUserId(rs.getInt("userId"));
        return recipe;
    }
}
