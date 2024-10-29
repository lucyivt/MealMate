package com.mealmate.mealmate.service;

import com.mealmate.mealmate.dao.DuplicatedUserException;
import com.mealmate.mealmate.dao.UserDao;
import com.mealmate.mealmate.dao.UserNotFoundException;
import com.mealmate.mealmate.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User addUser(User user) {
        // Check if FirstName or LastName is blank
        if (user.getFirstName().trim().isEmpty()) {
            // If FirstName is blank, set FirstName = "First Name blank, user NOT added"
            user.setFirstName("First Name blank, user NOT added");
        } else if (user.getLastName().trim().isEmpty()) {
            // If LastName is blank, set LastName = "Last Name blank, user NOT added"
            user.setLastName("Last Name blank, user NOT added");
        }
        return userDao.addUser(user);
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException {
        // Create a new instance of the User class to hold data
        User user = new User();
        try {
            // Attempt to find the user by its ID using the userDao
            user = userDao.getUserById(id);
            return user;
        } catch (NoSuchElementException e) {
            // Throw exception if user is not found
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }
    }

    @Override
    public void removeUser(int id) {
        userDao.removeUser(id);
    }

    @Override
    public void editUser(int userId, User user) {
        //Validate the user ID
        if (user.getUserId() != userId) {
            // Update names if IDs cannot match
            user.setFirstName("IDs do not match, edit failed");
            user.setLastName("IDs do not match, edit failed");
        }
        // Update the user data in DB using the userDao
        userDao.editUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    @Override
    public int register(User user) throws DuplicatedUserException {
        // Try to find the user in DB
        try {
            userDao.getUserByName(user.getFirstName(), user.getLastName()); // should throw if no user found
            throw new DuplicatedUserException("User already exists.");
        } catch (EmptyResultDataAccessException e) {
            // No existing user found, add the user to DB
            encodePassword(user);
            userDao.addUser(user);
        } catch (DuplicatedUserException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to register user due to unknown error", e);
        }

        // Return the userId for login purpose
        return user.getUserId();
    }

    @Override
    public int login(User user)
            throws UserNotFoundException, UnauthorizedException {
        try {
            User foundUser = userDao.getUserByName(user.getFirstName(), user.getLastName());
            boolean match = passwordEncoder.matches(user.getPassword(), foundUser.getPassword());
            if (!match) {
                throw new UnauthorizedException("Invalid credentials");
            }
            return foundUser.getUserId();
        } catch (EmptyResultDataAccessException e) {
            // No existing user found, add the user to DB
            throw new UserNotFoundException("User not found");
        }
    }
}
