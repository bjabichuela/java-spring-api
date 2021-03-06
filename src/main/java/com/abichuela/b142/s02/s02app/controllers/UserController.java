package com.abichuela.b142.s02.s02app.controllers;

import com.abichuela.b142.s02.s02app.exceptions.UserException;
import com.abichuela.b142.s02.s02app.models.User;
import com.abichuela.b142.s02.s02app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;

    // Create a new user
    @RequestMapping(value="/users", method = RequestMethod.POST)
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    // Retrieve all existing users
    @RequestMapping(value="/users", method = RequestMethod.GET)
    public ResponseEntity<Object> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    // Update an existing user
    @RequestMapping(value="/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateUser(id, user);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    // Delete an existing user
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestBody Map<String, String> body) throws UserException {
        String username = body.get("username");
        if (!userService.findByUsername(username).isEmpty()){
            throw new UserException("Username already exists.");
        } else {
            String password = body.get("password");
            String encryptedPassword = new BCryptPasswordEncoder().encode(password);
            User newUser = new User(username, encryptedPassword);
            userService.createUser(newUser);
            return new ResponseEntity<>("New user was registered.", HttpStatus.CREATED);
        }
    }
}
