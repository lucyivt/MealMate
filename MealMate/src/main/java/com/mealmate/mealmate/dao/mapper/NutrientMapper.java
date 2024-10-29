package com.mealmate.mealmate.dao.mapper;

import com.mealmate.mealmate.dto.Nutrient;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NutrientMapper implements RowMapper<Nutrient> {
    @Override
    public Nutrient mapRow(ResultSet rs, int index) throws SQLException {
        Nutrient nutrient = new Nutrient();
        nutrient.setNutrientId(rs.getInt("nutrientId"));
        nutrient.setLabel(rs.getString("label"));
        nutrient.setQuantity(rs.getDouble("quantity"));
        nutrient.setUnit(rs.getString("unit"));
        return nutrient;
    }
}
