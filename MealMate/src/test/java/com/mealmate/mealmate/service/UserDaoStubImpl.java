package com.mealmate.mealmate.service;

import com.mealmate.mealmate.dao.UserDao;
import com.mealmate.mealmate.dto.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;

public class UserDaoStubImpl implements UserDao {
    public User onlyUser;

    public UserDaoStubImpl() {
        onlyUser = new User();
        onlyUser.setUserId(99);
        onlyUser.setFirstName("Abc");
        onlyUser.setLastName("Xyz");
        onlyUser.setPassword("12345");
    }

    @Override
    public User addUser(User user) {
        if (user.getFirstName().isEmpty() ||
                user.getLastName().isEmpty()) {
            user.setFirstName("First Name blank, user NOT added");
            user.setLastName("Last Name blank, user NOT added");
        }
        return user;
    }

    @Override
    public User getUserById(int id) {
        if (onlyUser.getUserId() != id) {
            onlyUser.setFirstName("User Not Found");
            onlyUser.setLastName("User Not Found");
        }
        return onlyUser;
    }

    @Override
    public User getUserByName(String firstName, String lastName) {
        if (onlyUser.getFirstName().equals(firstName) &&
                onlyUser.getLastName().equals(lastName)) {
            return onlyUser;
        }
        throw new EmptyResultDataAccessException(1);
    }

    @Override
    public void removeUser(int id) {
        //Pass through method
    }

    @Override
    public void editUser(User user) {
        onlyUser.setFirstName(user.getFirstName());
        onlyUser.setLastName(user.getLastName());
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(onlyUser);
        return userList;
    }
}
