package com.mealmate.mealmate.service;

import com.mealmate.mealmate.dao.DuplicatedUserException;
import com.mealmate.mealmate.dao.UserNotFoundException;
import com.mealmate.mealmate.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        //Clear the user table
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            userService.removeUser(user.getUserId());
        }
    }

    @Test
    @DisplayName("User register")
    void userRegister() throws DuplicatedUserException, UserNotFoundException {
        User newUser = new User();
        newUser.setPassword("12345");
        newUser.setFirstName("New FN");
        newUser.setLastName("New LN");
        int newUserId = userService.register(newUser);

        List<User> newList = userService.getAllUsers();
        newList.forEach(user -> System.out.println(user.toString()));

        User reg = userService.getUserById(newUserId);
        assertNotNull(reg);
        assertEquals(newUser.getUserId(), reg.getUserId());
        assertEquals("New FN", reg.getFirstName());
        assertEquals("New LN", reg.getLastName());

        //Make sure the raw password matches its hash
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        assertTrue(encoder.matches("12345", reg.getPassword()));

        //Test register with failure
//        User newUser2 = new User();
//        newUser2.setPassword("54321");
//        newUser2.setFirstName("New FN2");
//        newUser2.setLastName("New LN2");
//        newUser2.setUserId(reg.getUserId()); //Try to register with a user object which has an existing ID
//        assertThrows(DuplicatedUserException.class, () -> userService.register(newUser2));
    }

    @Test
    @DisplayName("User login")
    void userLogin() throws DuplicatedUserException, UserNotFoundException {
        User newUser = new User();
        newUser.setPassword("12345");
        newUser.setFirstName("New FN");
        newUser.setLastName("New LN");
        int newUserId = userService.register(newUser);

        //Set back the raw password for simulating a login behavior
        newUser.setPassword("12345");
        try {
            assertEquals(userService.login(newUser), newUser.getUserId(), "User logged in with password 12345 and id 1");
        } catch (Exception e) {
            fail("User should be logged in with password 12345 and id 1");
        }

        newUser.setPassword("abcde");
        try {
            userService.login(newUser);
            fail("User should not be logged in with password abcde and id 1");
        } catch (UnauthorizedException e) {
            assertTrue(true, "User failed to log in with password abcde");
        }
    }
}
