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
    public ResponseEntity<String> auth(@RequestBody AuthRequest authRequest) {

        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        String action = authRequest.getAction();

        Optional<Users> searchForUsername = userService.getUserByUsername(username);

        if(action.equals("login")){
            if(searchForUsername.isEmpty()) {
                return ResponseEntity.badRequest().body("User not found");
            }
            else{
                boolean loginResult = authService.verifyPassword(password,
                        searchForUsername.get().getPassword(),
                        searchForUsername.get().getSalt());

                if(loginResult){
                    String jwtToken = jwtUtil.generateToken(username);
                    return ResponseEntity.ok(jwtToken);
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
                String email = authRequest.getEmail();
                String phone = authRequest.getPhone();

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
                    return ResponseEntity.ok(jwtToken);
                }
                else{
                    return ResponseEntity.badRequest().body("Failed to create new user");
                }
            }
        }
    }
}
