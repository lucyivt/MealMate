package com.mealmate.mealmate.dao;

import com.mealmate.mealmate.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDaoImplTest {
    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;

    @Autowired
    public UserDaoImplTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        userDao = new UserDaoImpl(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        //Clear the user table
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            userDao.removeUser(user.getUserId());
        }
    }

    @Test
    public void addUserTest() {
        User newUser = new User();
        newUser.setFirstName("FN");
        newUser.setLastName("LN");
        newUser.setPassword("123");
        userDao.addUser(newUser);
        User anotherUser = new User();
        anotherUser.setFirstName("FN2");
        anotherUser.setLastName("LN2");
        anotherUser.setPassword("123");
        userDao.addUser(anotherUser);
        List<User> newList = userDao.getAllUsers();
        assertNotNull(newList);
        assertEquals(2, newList.size());
        newList.forEach(user -> System.out.println(user.toString()));
    }

    @Test
    public void getUserByIdTest() {
        User newUser = new User();
        newUser.setFirstName("FN");
        newUser.setLastName("LN");
        newUser.setPassword("123");
        userDao.addUser(newUser);
        User anotherUser = userDao.getUserById(newUser.getUserId());
        assertNotNull(anotherUser);
        assertEquals("FN", anotherUser.getFirstName());
        assertEquals("LN", anotherUser.getLastName());
    }

    @Test
    public void removeUserTest() {
        User newUser = new User();
        newUser.setFirstName("FN");
        newUser.setLastName("LN");
        newUser.setPassword("123");
        userDao.addUser(newUser);
        assertNotNull(userDao.getAllUsers());
        assertEquals(1, userDao.getAllUsers().size());
        userDao.removeUser(newUser.getUserId());
        assertNotNull(userDao.getAllUsers());
        assertEquals(0, userDao.getAllUsers().size());
    }

    @Test
    public void editUserTest() {
        User newUser = new User();
        newUser.setFirstName("FN_1");
        newUser.setLastName("LN_1");
        newUser.setPassword("123");
        userDao.addUser(newUser);

        newUser.setFirstName("FN_2");
        newUser.setLastName("LN_2");
        newUser.setPassword("567");
        userDao.editUser(newUser);
        List<User> newList = userDao.getAllUsers();
        assertNotNull(newList);
        int i = 0;
        for (User user : newList) {
            if(user.getFirstName().contains("FN_2")) {
                i++;
            }
        }
        assertTrue(i != 0);
    }

    @Test
    public void getAllUsersTest() {
        User newUser = new User();
        newUser.setUserId(1);
        newUser.setFirstName("FN_1");
        newUser.setLastName("LN_1");
        newUser.setPassword("123");
        userDao.addUser(newUser);

        List<User> userList = userDao.getAllUsers();
        assertNotNull(userList);
        assertEquals(1, userList.size());
    }
}