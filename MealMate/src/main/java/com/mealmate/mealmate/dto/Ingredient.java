package com.mealmate.mealmate.dto;


import java.util.List;
import java.util.Objects;


public class Ingredient
{
    //FIELDS
    private int ingredientId;

    private float quantity; //default is measured in kg

    private String measure;
    private String name;

    //CONSTRUCTORS
    public Ingredient()
    {
    }

    public Ingredient(int ingredientId, float quantity, String measure, String name)
    {
        this.ingredientId = ingredientId;
        this.quantity = quantity;
        this.measure = measure;
        this.name = name;
    }


    //ACCESSOR METHODS
    public int getIngredientId()
    {
        return ingredientId;
    }


    public float getQuantity()
    {
        return quantity;
    }

    public String getMeasure()
    {
        return measure;
    }

    public String getName()
    {
        return name;
    }


    //MUTATOR METHODS
    public void setIngredientId(int ingredientId)
    {
        this.ingredientId = ingredientId;
    }

    public void setQuanity(float quantity)
    {
        this.quantity = quantity;
    }

    public void setMeasure(String measure)
    {
        this.measure = measure;
    }

    public void setName(String name)
    {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return ingredientId == that.ingredientId && Float.compare(quantity, that.quantity) == 0 && Objects.equals(measure, that.measure) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId, quantity, measure, name);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredientId=" + ingredientId +
                ", quantity=" + quantity +
                ", measure='" + measure + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String toAPIStringFormat() {
        return String.format("\"%s %s %s\"", quantity, measure, name);
    }
}
