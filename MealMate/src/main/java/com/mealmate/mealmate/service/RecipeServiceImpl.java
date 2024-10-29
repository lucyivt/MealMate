package com.mealmate.mealmate.service;

import com.google.gson.Gson;
import com.mealmate.mealmate.dao.*;
import com.mealmate.mealmate.dto.APIResponse;
import com.mealmate.mealmate.dto.Ingredient;
import com.mealmate.mealmate.dto.Nutrient;
import com.mealmate.mealmate.dto.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    RecipeDao recipeDao;

    @Autowired
    IngredientDao ingredientDao;

    @Autowired
    NutrientDao nutrientDao;

    public RecipeServiceImpl(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    /**
     * adds a recipe to the recipe DB
     * if title of recipe is blank, the recipe won't be added
     * once recipe is added, add the corresponding ingredients and nutrients
     * into their respective DB also map the ingredients to a recipe
     */
    @Override
    public Recipe addRecipe(Recipe recipe) throws RecipeTitleEmptyException {
        String title = recipe.getTitle();
        if (title.isEmpty()) {
            recipe.setTitle("Title of recipe is blank, not added");
            throw new RecipeTitleEmptyException("Title of recipe is blank, not added");
        }
        recipeDao.addRecipe(recipe);
        addIngredients(recipe);
        List<Nutrient> nutrients = nutrientAPICall(recipe.getIngredients());
        recipe.setNutrients(nutrients);
        nutrientDao.createNutrientsForRecipe(nutrients, recipe.getRecipeId());

        return recipe;
    }

    public List<Nutrient> nutrientAPICall(List<Ingredient> ingredients)
            throws APICallException {
        List<Nutrient> nutrients = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();

        StringBuilder body = new StringBuilder();
        body.append("{\"ingr\":");
        body.append(ingredientsToJson(ingredients));
        body.append("}");
        System.out.println(body);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.edamam.com/api/nutrition-details?app_id=47379841&app_key=d28718060b8adfd39783ead254df7f92"))
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .header("Content-Type", "application/json")
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Take the response.body and parse the JSON to be able to extract the "totalNutrients" key
            String responseBody = response.body();
            Gson g = new Gson();
            APIResponse apiResponse = g.fromJson(responseBody, APIResponse.class);
            for (Map.Entry<String, Nutrient> entry : apiResponse.getTotalNutrients().entrySet()) {
                nutrients.add(entry.getValue());
            }

        } catch (Exception e) {
//             Handle later
            throw new APICallException("Failed to get nutrients from API", e);
        }

        return nutrients;
    }

    private String ingredientsToJson(List<Ingredient> ingredients) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Ingredient i : ingredients) {
            sb.append(i.toAPIStringFormat());
            sb.append(",");
        }
        // Remove trailing comma
        sb.setLength(sb.length() - 1);
        // Close array
        sb.append("]");
        return sb.toString();
    }

    /**
     * adds ingredients to ingredient DB
     * adds ingredients to recipeIngredient DB
     *
     * @param recipe
     */
    private void addIngredients(Recipe recipe) {
        List<Ingredient> ingredients = recipe.getIngredients();
        for (Ingredient i : ingredients) {
            ingredientDao.addIngredient(i); // ingredient DB
            addIngredientToRecipeIngredient(recipe, i); // recipeIngredient DB
        }
    }

    @Override
    public List<Recipe> getAllRecipes(int userId) {
        List<Recipe> recipes = recipeDao.getAllRecipes(userId);
        for (Recipe r : recipes) {
            List<Nutrient> nutrients = nutrientDao.getNutrientsForRecipe(r.getRecipeId());
            List<Ingredient> ingredients = recipeDao.getRecipeIngredients(r.getRecipeId());
            r.setNutrients(nutrients);
            r.setIngredients(ingredients);
        }

        return recipes;
    }

    @Override
    public Recipe findRecipeById(int recipeId) {
        Recipe recipe = recipeDao.getRecipe(recipeId);
        List<Nutrient> nutrients = nutrientDao.getNutrientsForRecipe(recipeId);
        List<Ingredient> ingredients = recipeDao.getRecipeIngredients(recipeId);
        recipe.setNutrients(nutrients);
        recipe.setIngredients(ingredients);

        return recipe;
    }

    @Transactional
    @Override
    public Recipe updateRecipe(int id, Recipe recipe)
            throws DataAccessException, MismatchIdException, RecipeNotFoundException {
        Recipe oldRecipe = recipeDao.getRecipe(id);

        if (id != recipe.getRecipeId()) {
            throw new MismatchIdException("IDs don't match, recipe not updated");
        }

        if (!Objects.equals(oldRecipe.getTitle(), recipe.getTitle())) {
            oldRecipe.setTitle(recipe.getTitle());
        }
        if (!Objects.equals(oldRecipe.getUrl(), recipe.getUrl())) {
            oldRecipe.setUrl(recipe.getUrl());
        }
        if (!Objects.equals(oldRecipe.getSummary(), recipe.getSummary())) {
            oldRecipe.setSummary(recipe.getSummary());
        }
        if (!Objects.equals(oldRecipe.getImg(), recipe.getImg())) {
            oldRecipe.setImg(recipe.getImg());
        }
        if (!Objects.equals(oldRecipe.getYield(), recipe.getYield())) {
            oldRecipe.setYield(recipe.getYield());
        }
        if (!Objects.equals(oldRecipe.getTime(), recipe.getTime())) {
            oldRecipe.setTime(recipe.getTime());
        }
        recipeDao.updateRecipe(oldRecipe);

        // Update ingredients and nutrients
        if (!recipe.getIngredients().equals(oldRecipe.getIngredients())) {
            oldRecipe.setIngredients(recipe.getIngredients());
            List<Ingredient> existingIngredients = recipeDao.getRecipeIngredients(recipe.getRecipeId());

            recipeDao.deleteAllIngredientsFromRecipe(recipe.getRecipeId());

            for (Ingredient ingredient : recipe.getIngredients()) {
                existingIngredients.stream()
                        .filter(i -> Objects.equals(i.getName(), ingredient.getName()))
                        .findFirst()
                        .ifPresentOrElse(
                                i -> {
                                    ingredient.setIngredientId(i.getIngredientId());
                                    recipeDao.addIngredientToRecipe(recipe.getRecipeId(), ingredient);
                                },
                                () -> {
                                    ingredientDao.addIngredient(ingredient);
                                    recipeDao.addIngredientToRecipe(recipe.getRecipeId(), ingredient);
                                }
                        );
            }


            List<Nutrient> newNutrients = nutrientAPICall(recipe.getIngredients());
            oldRecipe.setNutrients(newNutrients);
            nutrientDao.updateNutrientsForRecipe(newNutrients, recipe.getRecipeId());
        }

        return oldRecipe;
    }

    @Override
    public void deleteRecipe(int id) throws RecipeNotFoundException {
        if (!recipeDao.deleteRecipe(id)) {
            throw new RecipeNotFoundException("recipeId not found in database");
        }
    }

    private void addIngredientToRecipeIngredient(Recipe recipe, Ingredient ingredient) {
        try {
            recipeDao.addIngredientToRecipe(recipe.getRecipeId(), ingredient);
            System.out.println("Recipe: " + recipe.getRecipeId() + " Ingredient: " + ingredient.getIngredientId() + " added to recipeIngredient");
        } catch (Exception e) {
            System.out.println("Recipe: " + recipe.getRecipeId() + " Ingredient: " + ingredient.getIngredientId() + " not added to recipeIngredient");
        }
    }

}
