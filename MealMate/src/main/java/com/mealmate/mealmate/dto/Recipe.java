package com.mealmate.mealmate.dto;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Recipe {
    private int recipeId;
    private String title;
    private String url;
    private String summary;
    private String img;
    private String yield;
    private String time;
    private int userId;
    private List<Ingredient> ingredients;
    private List<Nutrient> nutrients;


    public Recipe() {
        ingredients = new ArrayList<>();
        nutrients = new ArrayList<>();
    }

    public List<Nutrient> getNutrients() {
        return nutrients;
    }

    public void setNutrients(List<Nutrient> nutrients) {
        this.nutrients = nutrients;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getYield() {
        return yield;
    }

    public void setYield(String yield) {
        this.yield = yield;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return recipeId == recipe.recipeId && Objects.equals(title, recipe.title) && Objects.equals(url, recipe.url) && Objects.equals(summary, recipe.summary) && Objects.equals(img, recipe.img);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, title, url, summary, img);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId=" + recipeId +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", summary='" + summary + '\'' +
                ", img='" + img + '\'' +
                ", ingredients='" + ingredients.toString() + '\'' +
                '}';
    }
}
