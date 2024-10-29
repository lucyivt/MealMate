package com.mealmate.mealmate.controller;

import com.mealmate.mealmate.dao.DuplicatedUserException;
import com.mealmate.mealmate.dao.UserNotFoundException;
import com.mealmate.mealmate.dto.User;
import com.mealmate.mealmate.dto.UserIdResponse;
import com.mealmate.mealmate.service.UnauthorizedException;
import com.mealmate.mealmate.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @GetMapping("/user/{id}")
    public User getStudentById(@PathVariable(name = "id") int id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<UserIdResponse> login(@RequestBody User user)
            throws UserNotFoundException, UnauthorizedException {
        int userId = userService.login(user);
        return ResponseEntity.ok(new UserIdResponse(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<UserIdResponse> register(@RequestBody User user)
            throws DuplicatedUserException {
        int userId = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserIdResponse(userId));
    }

    @ExceptionHandler(DuplicatedUserException.class)
    public final ResponseEntity<Error> handleDuplicatedUserException(
            DuplicatedUserException e, WebRequest request) {
        Error err = new Error();
        err.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<Error> handleUnauthorizedException(
            UnauthorizedException e, WebRequest request) {
        Error err = new Error();
        err.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Error> handleUserNotFoundException(
            UserNotFoundException e, WebRequest request) {
        Error err = new Error();
        err.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Error> handleUnknownException(
            Exception e, WebRequest request) {
        log.error("An unhandled error occurred", e);
        Error err = new Error();
        err.setMessage("Something went wrong. Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

}
