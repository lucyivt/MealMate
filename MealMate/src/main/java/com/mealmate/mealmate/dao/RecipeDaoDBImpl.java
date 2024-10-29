package com.mealmate.mealmate.dao;

import com.mealmate.mealmate.dao.mapper.IngredientMapper;
import com.mealmate.mealmate.dao.mapper.IngredientWithQuantityMapper;
import com.mealmate.mealmate.dao.mapper.RecipeMapper;
import com.mealmate.mealmate.dto.Ingredient;
import com.mealmate.mealmate.dto.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class RecipeDaoDBImpl implements RecipeDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public RecipeDaoDBImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        final String sql = "INSERT INTO recipe" +
                "(title, url, summary, yield, time, img, userId) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, recipe.getTitle());
            ps.setString(2, recipe.getUrl());
            ps.setString(3, recipe.getSummary());
            ps.setString(4, recipe.getYield());
            ps.setString(5, recipe.getTime());
            ps.setString(6, recipe.getImg());
            ps.setInt(7, recipe.getUserId());
            return ps;
        }, keyHolder);
        recipe.setRecipeId(keyHolder.getKey().intValue());
        return recipe;
    }

    @Override
    public Recipe getRecipe(int id)
            throws RecipeNotFoundException {
        final String sql = "SELECT * FROM recipe WHERE recipeId = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new RecipeMapper(), id);
        } catch (DataAccessException e) {
            throw new RecipeNotFoundException("Recipe #" + id + " not found");
        }
    }

    @Override
    public List<Recipe> getAllRecipes(int userId) {
        final String sql = "SELECT * FROM recipe WHERE userId = ?";
        return jdbcTemplate.query(sql, new RecipeMapper(), userId);
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        final String sql = "Update recipe SET \n" +
                "title = ?,\n" +
                "url = ?,\n" +
                "summary = ?,\n" +
                "yield = ?,\n" +
                "time = ?,\n" +
                "img = ?\n" +
                "WHERE recipeId = ?";

        jdbcTemplate.update(sql,
                recipe.getTitle(),
                recipe.getUrl(),
                recipe.getSummary(),
                recipe.getYield(),
                recipe.getTime(),
                recipe.getImg(),
                recipe.getRecipeId());
    }

    @Override
    public boolean deleteRecipe(int id) {
        final String sql = "DELETE FROM recipe WHERE recipeId = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    // ----------------------------- ALTERS RECIPEINGREDIENT TABLE ---------------------

    @Override
    public void addIngredientToRecipe(int recipeId, Ingredient ingredient) {
        try {
            final String ADD_INGREDIENT_TO_RECIPE = "INSERT INTO recipeIngredient(recipeId, ingredientId, quantity, unit) VALUES (?, ?, ?, ?)";

            jdbcTemplate.update(ADD_INGREDIENT_TO_RECIPE,
                    recipeId,
                    ingredient.getIngredientId(),
                    ingredient.getQuantity(),
                    ingredient.getMeasure());

        } catch (DataAccessException e) {
            System.out.println("Ingredient must be stored in ingredient first");
        }
    }

    @Override
    public List<Ingredient> getRecipeIngredients(int recipeId)
            throws DataAccessException {
        final String RECIPE_INGREDIENTS = """
                SELECT i.ingredientId, i.ingredientName, ri.quantity, ri.unit
                FROM recipeIngredient ri
                JOIN ingredient i ON i.ingredientId = ri.ingredientId
                WHERE recipeId = ?
                """;
        return jdbcTemplate.query(RECIPE_INGREDIENTS, new IngredientWithQuantityMapper(), recipeId);
    }

    @Override
    public void deleteAllIngredientsFromRecipe(int recipeId) {
        final String sql = "DELETE FROM recipeIngredient WHERE recipeId = ?";
        jdbcTemplate.update(sql, recipeId);
    }

}
