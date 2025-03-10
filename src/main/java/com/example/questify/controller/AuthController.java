package com.example.questify.controller;

import com.example.questify.models.SimpleModels.Users;
import com.example.questify.services.AuthService;
import com.example.questify.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/app")
public class AuthController {
    private AuthService authService;
    private UserService userService;

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> auth(@RequestParam(name = "username") String username,
                               @RequestParam(name = "password") String password,
                               @RequestParam(name = "action") String action) {

        Optional<Users> searchForUsername = userService.getUserByUsername(username);

        if(action.equals("login")){
            if(searchForUsername.isEmpty()) {
                return ResponseEntity.badRequest().body("User not found");
            }
            else{

            }
        }
        else{
            if(searchForUsername.isPresent()) {
                return ResponseEntity.badRequest().body("User already existing");
            }
        }

        return ResponseEntity.ok("");
    }
}
