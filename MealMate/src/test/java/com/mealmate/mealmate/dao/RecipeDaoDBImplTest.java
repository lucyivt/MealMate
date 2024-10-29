package com.mealmate.mealmate.dao;

import com.mealmate.mealmate.dto.Ingredient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.mealmate.mealmate.dto.Recipe;

@SpringBootTest

public class RecipeDaoDBImplTest {
    @Autowired
    RecipeDao recipeDao;

    @Autowired
    IngredientDao ingredientDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    Recipe recipe;

    @BeforeEach
    void setUp() {
        String sql2 = "DELETE FROM recipe";
        jdbcTemplate.update(sql2);

        String sql3 = "DELETE FROM user";
        jdbcTemplate.update(sql3);

        String sql = "INSERT INTO user (firstName, lastName, password) VALUES (?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "fn");
            ps.setString(2, "ln");
            ps.setString(3, "pass");
            
            return ps;
        }, keyHolder);

        Recipe recipe = new Recipe();
        recipe.setTitle("Pancakes");
        recipe.setYield("2");
        recipe.setTime("30");
        recipe.setImg("url");
        recipe.setUrl("url");
        recipe.setSummary("Summary");
        recipe.setUserId(keyHolder.getKey().intValue());
        this.recipe = recipe;

    }

    @AfterEach
    void tearDown() {
        recipeDao = null;
    }

    @Test
    @DisplayName("Add New recipe Test")
    void testAddRecipe() {

        recipeDao.addRecipe(recipe);
        System.out.println(recipe.getRecipeId());
        List<Recipe> recipes = recipeDao.getAllRecipes(recipe.getUserId());
        assertEquals(1, recipes.size());
        
    }

    @Test
    @DisplayName("Add Get recipe Test")
    void testGetRecipe() {
        
        recipeDao.addRecipe(recipe);
        Recipe retrievedRecipe = recipeDao.getRecipe(recipe.getRecipeId());
    
        assertEquals(recipe.getTitle(), retrievedRecipe.getTitle());
        assertEquals(recipe.getYield(), retrievedRecipe.getYield());
    }

    @Test
    @DisplayName("Update Recipe Test")
    void testUpdateRecipe() {

        recipeDao.addRecipe(recipe);
        
        recipe.setTitle("Updated Pancakes");
        recipeDao.updateRecipe(recipe);  
    
        Recipe updatedRecipe = recipeDao.getRecipe(recipe.getRecipeId());
        assertEquals("Updated Pancakes", updatedRecipe.getTitle());
    }
    @Test
    @DisplayName("Delete recipe Test")
    void testDeleteRecipe() {
        recipeDao.addRecipe(recipe);
        List<Recipe> recipesBeforeDelete = recipeDao.getAllRecipes(recipe.getUserId());
        assertEquals(1, recipesBeforeDelete.size());
    
        recipeDao.deleteRecipe(recipe.getRecipeId());  // Assume this method deletes a recipe by ID
        List<Recipe> recipesAfterDelete = recipeDao.getAllRecipes(recipe.getUserId());
        assertEquals(0, recipesAfterDelete.size());
    }

    @Test
    @DisplayName("add RECIPE ingredient")
    void addIngredientToRecipe()
    {
        int initialSize = this.recipe.getIngredients().size();
        recipeDao.addRecipe(this.recipe);

        Ingredient ing1 = new Ingredient(1, 1.0F, "cup", "chickpea");
        ingredientDao.addIngredient(ing1);
        this.recipe.setIngredients(List.of(ing1));
        recipeDao.addIngredientToRecipe(this.recipe.getRecipeId(), ing1);
        List<Ingredient> recipeIngredients = recipeDao.getRecipeIngredients(this.recipe.getRecipeId());

        assertNotNull(recipeIngredients);
        assertEquals(initialSize+1, recipeIngredients.size());

    }

    @Test
    @DisplayName("remove RECIPE ingredient")
    void removeIngredientFromRecipe()
    {
        int initialSize = this.recipe.getIngredients().size();
        recipeDao.addRecipe(this.recipe);

        Ingredient ing1 = new Ingredient(1, 1.0F, "cup", "chickpea");
        ingredientDao.addIngredient(ing1);
        this.recipe.setIngredients(List.of(ing1));
        recipeDao.addIngredientToRecipe(this.recipe.getRecipeId(), ing1);
        List<Ingredient> recipeIngredients = recipeDao.getRecipeIngredients(this.recipe.getRecipeId());

        assertNotNull(recipeIngredients);
        assertEquals(initialSize+1, recipeIngredients.size());

        recipeDao.deleteAllIngredientsFromRecipe(this.recipe.getRecipeId());
        List<Ingredient> recipeIngredients2 = recipeDao.getRecipeIngredients(this.recipe.getRecipeId());


        assertNotNull(recipeIngredients2);
        assertEquals(0, recipeIngredients2.size());
    }

}
