package com.mealmate.mealmate.dao;

import com.mealmate.mealmate.dao.mapper.UserMapper;
import com.mealmate.mealmate.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    //Using BCrypt to encode the password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUser(User user) {
        // Define the SQL query
        final String ADD_USER = "INSERT INTO user(firstName, lastName, password) "
                + "VALUES(?,?,?)";
        // Create a holder to retrieve the generated key (userId) after insertion
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        // Execute the update operation using the jdbcTemplate which handles the database connection
        jdbcTemplate.update((Connection conn) -> {
            // Use a PreparedStatement for security purpose, and specifying that we want the generated keys
            PreparedStatement stmt = conn.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS);
            // Set the parameters for the SQL statement using the student object's properties
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getPassword());
            return stmt;
        }, keyHolder); // Pass the keyHolder to capture the generated key
        // Set the generated student ID back into the student object
        user.setUserId(keyHolder.getKey().intValue());

        return user;
    }

    @Override
    public User getUserById(int id) {
        // Define the SQL query
        final String GET_USER_BY_ID = "SELECT * FROM user WHERE userId = ?;";
        // Execute the query using jdbcTemplate to fetch a single User object based on the provided ID
        return jdbcTemplate.queryForObject(GET_USER_BY_ID, new UserMapper(), id);
    }

    @Override
    public User getUserByName(String firstName, String lastName) {
        final String GET_USER_BY_NAME = "SELECT * FROM user WHERE firstName = ? AND lastName = ?;";
        return jdbcTemplate.queryForObject(GET_USER_BY_NAME, new UserMapper(), firstName, lastName);
    }

    @Override
    public void removeUser(int id) {
        // Define the SQL query
        final String REMOVE_USER = "DELETE FROM user WHERE userId = ?;";
        // Execute the delete operation using jdbcTemplate, passing the userId to specify which user to remove
        jdbcTemplate.update(REMOVE_USER, id);
    }

    @Override
    public void editUser(User user) {
        // Define the SQL query
        final String EDIT_USER = "UPDATE user SET "
                + "firstName = ?, "
                + "lastName = ?, "
                + "password = ? "
                + "WHERE userId = ?;";
        // Execute the update operation using jdbcTemplate with the user object's properties
        jdbcTemplate.update(EDIT_USER,
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getUserId());
    }

    @Override
    public List<User> getAllUsers() {
        // Define the SQL query
        final String GET_ALL_USERS = "SELECT * FROM user;";
        // Execute the query using jdbcTemplate and map the results to a list of User objects
        return jdbcTemplate.query(GET_ALL_USERS, new UserMapper());
    }
}
