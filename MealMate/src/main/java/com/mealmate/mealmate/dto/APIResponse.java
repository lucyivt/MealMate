package com.mealmate.mealmate.dto;

import java.util.Map;

public class APIResponse {
    Map<String, Nutrient> totalNutrients; // map<conventional_label, nutrient>

    public Map<String, Nutrient> getTotalNutrients() {
        return totalNutrients;
    }

    public void setTotalNutrients(Map<String, Nutrient> totalNutrients) {
        this.totalNutrients = totalNutrients;
    }
}
