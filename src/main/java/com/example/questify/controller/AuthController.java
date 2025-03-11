package com.example.questify.controller;

import com.example.questify.models.SimpleModels.AuthRequest;
import com.example.questify.models.SimpleModels.Users;
import com.example.questify.services.AuthService;
import com.example.questify.services.UserService;
import com.example.questify.utils.JwtUtil;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/app")
public class AuthController {
    private AuthService authService;
    private UserService userService;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthService authService, UserService userService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestParam(name = "username") String username,
                                       @RequestParam(name = "password") String password,
                                       @RequestParam(name = "action") String action,
                                       @RequestParam(name = "email", required = false) String email,
                                       @RequestParam(name = "phone", required = false) String phone) {
        Optional<Users> searchForUsername = userService.getUserByUsername(username);

        if(action.equals("login")){
            if(searchForUsername.isEmpty()) {
                return ResponseEntity.badRequest().body("User not found");
            }
            else{

                if(searchForUsername.get().getBanned()){
                    return ResponseEntity.badRequest().body("User banned");
                }

                boolean loginResult = authService.verifyPassword(password,
                        searchForUsername.get().getPassword(),
                        searchForUsername.get().getSalt());

                if(loginResult){
                    String jwtToken = jwtUtil.generateToken(username);
                    Object returnData = List.of(jwtToken, searchForUsername.get());
                    return ResponseEntity.ok(returnData);
                }
                else{
                    return ResponseEntity.badRequest().body("Wrong password");
                }
            }
        }
        else{
            if(searchForUsername.isPresent()) {
                return ResponseEntity.badRequest().body("User already existing");
            }
            else{
                String[] hashedPasswordAndSalt = authService.getHashedPasswordAndSalt(password);

                Users newUser = new Users(
                        username,
                        false,
                        hashedPasswordAndSalt[0],
                        hashedPasswordAndSalt[1],
                        false,
                        email,
                        phone
                );

                boolean saveUserStatus = userService.createNewUser(newUser);

                if(saveUserStatus){
                    String jwtToken = jwtUtil.generateToken(username);
                    Object returnData = List.of(jwtToken, newUser);
                    return ResponseEntity.ok(returnData);
                }
                else{
                    return ResponseEntity.badRequest().body("Failed to create new user");
                }
            }
        }
    }
}
