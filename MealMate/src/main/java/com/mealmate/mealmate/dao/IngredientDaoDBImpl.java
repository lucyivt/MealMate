package com.mealmate.mealmate.dao;

import com.mealmate.mealmate.dao.mapper.IngredientMapper;
import com.mealmate.mealmate.dto.Ingredient;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class IngredientDaoDBImpl implements IngredientDao {

    private final JdbcTemplate jdbcTemplate;

    public IngredientDaoDBImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Ingredient getIngredient(int ingredientId) {
        try {
            final String GET_INGREDIENT = "SELECT * FROM ingredient WHERE ingredientId = ?";
            return jdbcTemplate.queryForObject(GET_INGREDIENT, new IngredientMapper(), ingredientId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        final String GET_ALL_INGREDIENT = "SELECT * FROM ingredient";
        return jdbcTemplate.query(GET_ALL_INGREDIENT, new IngredientMapper());
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        try {
            final String EXISTING_INGREDIENT = "SELECT * FROM ingredient WHERE ingredientName = ?";
            Ingredient existing = jdbcTemplate.queryForObject(EXISTING_INGREDIENT, new IngredientMapper(), ingredient.getName());
            assert existing != null;
            ingredient.setIngredientId(existing.getIngredientId());
            return existing;
        } catch (Exception e) {
            final String addIngredient = "INSERT INTO ingredient(ingredientName) VALUES(?)";

            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(addIngredient, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, ingredient.getName());
                return ps;
            }, keyHolder);
            int newId = keyHolder.getKey().intValue();

            ingredient.setIngredientId(newId);
            return ingredient;
        }
    }

    @Override
    public void deleteIngredient(Ingredient ingredient) {
        String REMOVE_INGREDIENT = "DELETE FROM ingredient where ingredientId = ?";
        jdbcTemplate.update(REMOVE_INGREDIENT, ingredient.getIngredientId());
    }
}
