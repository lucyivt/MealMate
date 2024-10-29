package com.mealmate.mealmate.dao.mapper;

import com.mealmate.mealmate.dto.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int index) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("userId"));
        user.setFirstName(rs.getString("firstName"));
        user.setLastName(rs.getString("lastName"));
        user.setPassword(rs.getString("password"));
        return user;
    }
}
