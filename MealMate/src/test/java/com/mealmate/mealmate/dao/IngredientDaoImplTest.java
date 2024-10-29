package com.mealmate.mealmate.dao;

import com.mealmate.mealmate.dto.Ingredient;
import com.mealmate.mealmate.dto.Recipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IngredientDaoImplTest
{

    //MAKE SURE TO RUN test_data.sql in mySQL BEFORE RUNNING TESTS
    private JdbcTemplate jdbcTemplate;
    private IngredientDao ingrDao;
    private RecipeDao recDao;

    @Autowired
    public IngredientDaoImplTest(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
        ingrDao = new IngredientDaoDBImpl(jdbcTemplate);
        recDao = new RecipeDaoDBImpl(jdbcTemplate);
    }

    @AfterEach
    void cleanUp()
    {
        List<Ingredient> ingrLst = ingrDao.getAllIngredients();
        for(Ingredient ing : ingrLst)
        {
            if(ing.getIngredientId() > 10) ingrDao.deleteIngredient(ing);
        }

    }

    @Test
    @DisplayName("get all ingredients")
    void getAllIngredients()
    {
        List<Ingredient> get = ingrDao.getAllIngredients();
        assertEquals(10, get.size());
    }

    @Test
    @DisplayName("add+get ingredient")
    void getIngredient()
    {
        Ingredient ing1 = new Ingredient(11, 0.25f, "kg", "rice");
        Ingredient ing2 = new Ingredient(12, 0.5f, "cups", "tomatoes");

        ingrDao.addIngredient(ing1);
        assertEquals(11, ingrDao.getAllIngredients().size());

        ingrDao.addIngredient(ing2);
        assertEquals(12, ingrDao.getAllIngredients().size());


        Ingredient get = ingrDao.getIngredient(ing1.getIngredientId());
        assertEquals(get.getName(), ing1.getName());
        get = ingrDao.getIngredient(ing2.getIngredientId());
        assertEquals(get.getName(), ing2.getName());
    }

    @Test
    @DisplayName("delete ingredient")
    void deleteIngredient()
    {
        Ingredient ing1 = new Ingredient(11, 0.25f, "kg", "rice");
        Ingredient ing2 = new Ingredient(12, 0.5f, "cups", "tomatoes");

        ingrDao.addIngredient(ing1);
        assertEquals(11, ingrDao.getAllIngredients().size());

        ingrDao.addIngredient(ing2);
        assertEquals(12, ingrDao.getAllIngredients().size());

        ingrDao.deleteIngredient(ing1);
        assertEquals(11, ingrDao.getAllIngredients().size());

        ingrDao.deleteIngredient(ing2);
        assertEquals(10, ingrDao.getAllIngredients().size());
    }

}