package com.mealmate.mealmate.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Nutrient {

    private Integer nutrientId;
    private String label;
    private Double quantity;
    private String unit;

    public Nutrient() {
    }

    public Nutrient(String label, Double quantity, String unit) {
        this.label = label;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @JsonIgnore
    public Integer getNutrientId() {
        return nutrientId;
    }

    public void setNutrientId(Integer nutrientId) {
        this.nutrientId = nutrientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nutrient nutrient = (Nutrient) o;
        return Objects.equals(label, nutrient.label) && Objects.equals(quantity, nutrient.quantity) && Objects.equals(unit, nutrient.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, quantity, unit);
    }

    @Override
    public String toString() {
        return "Nutrient{" +
                "label='" + label + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                '}';
    }
}
