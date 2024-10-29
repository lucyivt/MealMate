package com.mealmate.mealmate.service;

import com.mealmate.mealmate.dao.DuplicatedUserException;
import com.mealmate.mealmate.dao.UserNotFoundException;
import com.mealmate.mealmate.dto.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    User getUserById(int id) throws UserNotFoundException;

    void removeUser(int id);

    void editUser(int userId, User user);

    List<User> getAllUsers();

    void encodePassword(User user);

    int register(User user) throws DuplicatedUserException;

    int login(User user) throws UserNotFoundException, UnauthorizedException;
}
