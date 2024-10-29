package com.mealmate.mealmate.service;

import com.mealmate.mealmate.App;
import com.mealmate.mealmate.dao.*;
import com.mealmate.mealmate.dto.Ingredient;
import com.mealmate.mealmate.dto.Recipe;
import com.mealmate.mealmate.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = App.class)
class RecipeServiceImplTest {
    @Autowired
    UserService userService;

    @Autowired
    RecipeServiceImpl recipeServiceImpl;

    @Autowired
    IngredientDao ingredientDao;

    @BeforeEach
    public void setUp() throws ZeroRowsAffectedException {
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            userService.removeUser(user.getUserId());
        }

        List<Ingredient> ingredients = ingredientDao.getAllIngredients();
        for (Ingredient ingredient : ingredients) {
            ingredientDao.deleteIngredient(ingredient);
        }
    }

    @Test
    void TestAddFindRecipe() throws RecipeTitleEmptyException {
        User user1 = new User();
        user1.setFirstName("Test First Name 1");
        user1.setLastName("Test Last Name 1");
        user1.setPassword("Test Password 1");
        userService.addUser(user1);

        Recipe recipe1 = new Recipe();
        recipe1.setTitle("Test Recipe 1");
        recipe1.setUrl("");
        recipe1.setSummary("");
        recipe1.setYield("");
        recipe1.setTime("");
        recipe1.setImg("");
        recipe1.setUserId(user1.getUserId());

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("Rice");
        ingredient1.setQuanity(1);
        ingredient1.setMeasure("kg");

        recipe1.setIngredients(List.of(ingredient1));

        recipeServiceImpl.addRecipe(recipe1);
        Recipe recipe1Dup = recipeServiceImpl.findRecipeById(recipe1.getRecipeId());

        assertEquals(recipe1, recipe1Dup);
    }

    @Test
    void testNutrientAPICall() {
        List<Ingredient> ingredients = List.of(
                new Ingredient(1, 1, "cup", "rice"),
                new Ingredient(2, 10, "oz", "chickpeas")
        );
        recipeServiceImpl.nutrientAPICall(ingredients);
    }

    @Test
    void testGetAllRecipes() throws RecipeTitleEmptyException {
        User user1 = new User();
        user1.setFirstName("Test First Name 1");
        user1.setLastName("Test Last Name 1");
        user1.setPassword("Test Password 1");
        userService.addUser(user1);

        Recipe recipe1 = new Recipe();
        recipe1.setTitle("Test Recipe 1");
        recipe1.setUrl("");
        recipe1.setSummary("");
        recipe1.setYield("");
        recipe1.setTime("");
        recipe1.setImg("");
        recipe1.setUserId(user1.getUserId());

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("Rice");
        ingredient1.setQuanity(1);
        ingredient1.setMeasure("kg");

        recipe1.setIngredients(List.of(ingredient1));

        recipeServiceImpl.addRecipe(recipe1);

        User user2 = new User();
        user2.setFirstName("Test First Name 2");
        user2.setLastName("Test Last Name 2");
        user2.setPassword("Test Password 2");
        userService.addUser(user2);

        Recipe recipe2 = new Recipe();
        recipe2.setTitle("Test Recipe 2");
        recipe2.setUrl("");
        recipe2.setSummary("");
        recipe2.setYield("");
        recipe2.setTime("");
        recipe2.setImg("");
        recipe2.setUserId(user1.getUserId());

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("Pinto Bean");
        ingredient2.setQuanity(1);
        ingredient2.setMeasure("kg");

        recipe2.setIngredients(List.of(ingredient2));

        recipeServiceImpl.addRecipe(recipe2);

        List<Recipe> allRecipes = recipeServiceImpl.getAllRecipes(user1.getUserId());

        assertEquals(allRecipes.size(), 2);
        assertTrue(allRecipes.contains(recipe1));
        assertTrue(allRecipes.contains(recipe2));
    }
    
    @Test
    void testUpdateRecipe() throws RecipeTitleEmptyException, MismatchIdException, ZeroRowsAffectedException {
        User user1 = new User();
        user1.setFirstName("Test First Name 1");
        user1.setLastName("Test Last Name 1");
        user1.setPassword("Test Password 1");
        userService.addUser(user1);

        Recipe recipe1 = new Recipe();
        recipe1.setTitle("Test Recipe 1");
        recipe1.setUrl("");
        recipe1.setSummary("");
        recipe1.setYield("");
        recipe1.setTime("");
        recipe1.setImg("");
        recipe1.setUserId(user1.getUserId());

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("Rice");
        ingredient1.setQuanity(1);
        ingredient1.setMeasure("kg");

        recipe1.setIngredients(List.of(ingredient1));

        recipeServiceImpl.addRecipe(recipe1);

        Recipe recipe2 = new Recipe();
        recipe2.setRecipeId(recipe1.getRecipeId());
        recipe2.setTitle("Test Recipe 2");
        recipe2.setUrl("");
        recipe2.setSummary("");
        recipe2.setYield("");
        recipe2.setTime("");
        recipe2.setImg("");
        recipe2.setUserId(user1.getUserId());

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("Apple");
        ingredient2.setQuanity(1);
        ingredient2.setMeasure("kg");

        recipe2.setIngredients(List.of(ingredient2));

        recipeServiceImpl.updateRecipe(recipe1.getRecipeId(), recipe2);

        Recipe recipeUpdated = recipeServiceImpl.findRecipeById(recipe1.getRecipeId());
        List<Ingredient> ingredientListUpdated = recipeUpdated.getIngredients();

        assertEquals(recipeUpdated.getTitle(), "Test Recipe 2");
        assertEquals(ingredientListUpdated.get(0).getName(), "Apple");
        assertEquals(ingredientListUpdated.get(0).getQuantity(), 1);
        assertEquals(ingredientListUpdated.get(0).getMeasure(), "kg");
    }

    @Test
    void testDeleteRecipe() throws RecipeTitleEmptyException, ZeroRowsAffectedException {
        User user1 = new User();
        user1.setFirstName("Test First Name 1");
        user1.setLastName("Test Last Name 1");
        user1.setPassword("Test Password 1");
        userService.addUser(user1);

        Recipe recipe1 = new Recipe();
        recipe1.setTitle("Test Recipe 1");
        recipe1.setUrl("");
        recipe1.setSummary("");
        recipe1.setYield("");
        recipe1.setTime("");
        recipe1.setImg("");
        recipe1.setUserId(user1.getUserId());

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("Rice");
        ingredient1.setQuanity(1);
        ingredient1.setMeasure("kg");

        recipe1.setIngredients(List.of(ingredient1));

        recipeServiceImpl.addRecipe(recipe1);

        User user2 = new User();
        user2.setFirstName("Test First Name 2");
        user2.setLastName("Test Last Name 2");
        user2.setPassword("Test Password 2");
        userService.addUser(user2);

        Recipe recipe2 = new Recipe();
        recipe2.setTitle("Test Recipe 2");
        recipe2.setUrl("");
        recipe2.setSummary("");
        recipe2.setYield("");
        recipe2.setTime("");
        recipe2.setImg("");
        recipe2.setUserId(user1.getUserId());

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("Pinto Bean");
        ingredient2.setQuanity(1);
        ingredient2.setMeasure("kg");

        recipe2.setIngredients(List.of(ingredient2));

        recipeServiceImpl.addRecipe(recipe2);

        Recipe recipe1Dup = recipeServiceImpl.findRecipeById(recipe1.getRecipeId());
        Recipe recipe2Dup = recipeServiceImpl.findRecipeById(recipe2.getRecipeId());

        assertEquals(recipe1, recipe1Dup);
        assertEquals(recipe2, recipe2Dup);

        recipeServiceImpl.deleteRecipe(recipe1.getRecipeId());

        List<Recipe> allRecipes = recipeServiceImpl.getAllRecipes(user1.getUserId());

        assertEquals(allRecipes.size(), 1);
        assertFalse(allRecipes.contains(recipe1));
        assertTrue(allRecipes.contains(recipe2));

        assertThrowsExactly(RecipeNotFoundException.class, () -> {
            recipeServiceImpl.findRecipeById(recipe1.getRecipeId());
        });
    }
}