package com.example.questify.controller;

import com.example.questify.models.SimpleModels.Users;
import com.example.questify.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("getAll")
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get")
    public Optional<Users> getUserById(@RequestParam(name = "id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/getByUsername")
    public Optional<Users> getUsersByUsername(@RequestParam(name = "username") String username) {return userService.getUsersByUsername(username);}

    @DeleteMapping("/remove")
    public boolean removeUserById(@RequestParam(name = "id") Long id) {return userService.removeUserById(id);}
}
