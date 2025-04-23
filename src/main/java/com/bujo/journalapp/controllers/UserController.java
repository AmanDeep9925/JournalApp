package com.bujo.journalapp.controllers;

import com.bujo.journalapp.entity.User;
import com.bujo.journalapp.repository.UserRepository;
import com.bujo.journalapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> createUser(@RequestBody User userBody) {
        User user = userService.saveUser(userBody);
        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @GetMapping("/get-user/{user-name}")
    public ResponseEntity<User> getUser(@PathVariable("user-name") String userName) {
        User user = userService.getUserByUsername(userName);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(user, HttpStatus.FOUND);
    }

    @DeleteMapping("/delete-user/{user-name}")
    public ResponseEntity<?> removeUser(@PathVariable("user-name") String userName) {
        userService.deleteUserByUsername(userName);
        return new ResponseEntity<>("User Removed", HttpStatus.OK);
    }

    @PutMapping("/update-user/{user-name}")
    public ResponseEntity<?> updateUser(@PathVariable("user-name") String userName, @RequestBody User user) {
        User updateUser = userService.updateUser(userName, user);
        if (updateUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }
}
