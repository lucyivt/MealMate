package com.mealmate.mealmate.service;

import com.mealmate.mealmate.dao.DuplicatedUserException;
import com.mealmate.mealmate.dao.UserDao;
import com.mealmate.mealmate.dao.UserNotFoundException;
import com.mealmate.mealmate.dto.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceStubTest {
    private UserServiceImpl userService;
    private UserDao userDao;

    public UserServiceStubTest() {
        userDao = new UserDaoStubImpl();
        userService = new UserServiceImpl(userDao);
    }

    @Test
    @DisplayName("User Add Service Test")
    void userAddServiceTest() {
        User user = new User();
        user.setFirstName("New User First Name");
        user.setLastName("New User Last Name");

        User newUser = userService.addUser(user);
        assertNotNull(newUser);
        assertEquals("New User First Name", newUser.getFirstName());
        assertEquals("New User Last Name", newUser.getLastName());
    }

    @Test
    @DisplayName("User No Add Service Test")
    void userNoAddServiceTest() {
        User user = new User();
        user.setFirstName("");
        user.setLastName("");

        User newUser = userService.addUser(user);
        assertNotNull(newUser);
        assertEquals("First Name blank, user NOT added", newUser.getFirstName());
        assertEquals("Last Name blank, user NOT added", newUser.getLastName());
    }

    @Test
    @DisplayName("Find User Service Test")
    public void findUserServiceTest() throws UserNotFoundException {
        User returnUser = userService.getUserById(99);
        assertNotNull(returnUser);
        assertEquals("Abc", returnUser.getFirstName());
        assertEquals("Xyz", returnUser.getLastName());
    }

    @Test
    @DisplayName("User Not Found Service Test")
    public void userNotFoundServiceTest() throws UserNotFoundException {
        User notFound = userService.getUserById(11);
        assertNotNull(notFound);
        assertEquals("User Not Found", notFound.getFirstName());
        assertEquals("User Not Found", notFound.getLastName());
    }

    @Test
    @DisplayName("Edit User Service Test")
    public void editUserServiceTest() {
        User user = new User();
        user.setUserId(100);
        user.setPassword("12345");
        user.setFirstName("Edited User First Name");
        user.setLastName("Edited User Last Name");
        userService.encodePassword(user);

        userService.editUser(100, user);
        assertNotNull(user);
        assertEquals(100, user.getUserId());
        assertEquals("Edited User First Name", user.getFirstName());
        assertEquals("Edited User Last Name", user.getLastName());
        assertFalse(user.getPassword().equals("12345"));
    }

    @Test
    @DisplayName("User No Edit Service Test")
    public void userNoEditServiceTest() {
        User user = new User();
        user.setUserId(100);
        user.setPassword("12345");
        user.setFirstName("Edited User First Name");
        user.setLastName("Edited User Last Name");

        userService.editUser(99, user);
        assertNotNull(user);
        assertEquals(100, user.getUserId());
        assertEquals("IDs do not match, edit failed", user.getFirstName());
        assertEquals("IDs do not match, edit failed", user.getLastName());
    }

    @Test
    @DisplayName("Encode password")
    void encodePassword() throws UserNotFoundException {
        User user = userService.getUserById(99);
        System.out.println("Original password: " + user.getPassword());
        userService.encodePassword(user);
        System.out.println("Encoded password: " + user.getPassword());
        assertNotEquals("12345", user.getPassword());
    }

    @Test
    @DisplayName("User register")
    void userRegister() throws DuplicatedUserException, UserNotFoundException {
        User newUser = new User();
        newUser.setUserId(1);
        newUser.setPassword("12345");
        newUser.setFirstName("New Reg FN");
        newUser.setLastName("New Reg LN");
        int newUserId = userService.register(newUser);

        User reg = userService.getUserById(1);
        assertNotEquals(newUser.getUserId(), reg.getUserId());
        assertNotEquals("New Reg FN", reg.getFirstName());
        assertNotEquals("New Reg LN", reg.getLastName());
    }

    @Test
    @DisplayName("User login")
    void userLogin() throws DuplicatedUserException, UserNotFoundException {
        User newUser = userService.getUserById(99);
        userService.encodePassword(newUser);
        User loginUser = new User();
        loginUser.setPassword("12345");
        loginUser.setFirstName("Abc");
        loginUser.setLastName("Xyz");
        loginUser.setUserId(99);
        try {
            assertEquals(userService.login(loginUser), newUser.getUserId(), "User logged in with password 12345 and id " +
                    "99");
        } catch (Exception e) {
            fail("User should be logged in with password 12345 and id 99");
        }

        newUser.setPassword("abcde");
        try {
            userService.login(newUser);
            fail("User should not be logged in with password abcde and id 99");
        } catch (UnauthorizedException e) {
            assertTrue(true, "User failed to log in with password abcde");
        }
    }
}