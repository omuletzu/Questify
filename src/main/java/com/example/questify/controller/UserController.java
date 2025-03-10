package com.example.questify.controller;

import com.example.questify.models.User;
import com.example.questify.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("getAll")
    public List<User> getAllUsers() {

    }

    @GetMapping("/get")
    public User getUserById(Long id) {

    }
}
