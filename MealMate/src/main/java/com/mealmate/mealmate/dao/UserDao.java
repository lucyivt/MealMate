package com.mealmate.mealmate.dao;

import com.mealmate.mealmate.dto.User;
import java.util.List;

public interface UserDao {
    User addUser(User user);

    User getUserById(int id);

    User getUserByName(String firstName, String lastName);

    void removeUser(int id);

    void editUser(User user);

    List<User> getAllUsers();
}
