package com.example.auth.controllers;

import com.example.auth.models.Users;
import com.example.auth.models.requests.AuthRequest;
import com.example.auth.models.requests.AuthTokenRequest;
import com.example.auth.services.AuthService;
import com.example.auth.services.UserService;
import com.example.auth.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
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

    @PostMapping("/connect")
    public ResponseEntity<Object> auth(@RequestBody AuthRequest authRequest){
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        String action = authRequest.getAction();

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
            String email = authRequest.getEmail();
            String phone = authRequest.getPhone();

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

    @GetMapping("/checkToken")
    public ResponseEntity<Boolean> checkToken(@RequestParam(name = "token") String token) {
        if(jwtUtil.isValid(token)) {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
}
