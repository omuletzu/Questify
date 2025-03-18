package com.example.auth.controllers;

import com.example.auth.models.Users;
import com.example.auth.models.requests.UserGetByUsernameRequest;
import com.example.auth.models.requests.UserGetRequest;
import com.example.auth.services.EmailService;
import com.example.auth.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private RestTemplate restTemplate;
    private EmailService emailService;

    @Autowired
    public UserController(UserService userService, RestTemplate restTemplate, EmailService emailService){
        this.userService = userService;
        this.restTemplate = restTemplate;
        this.emailService = emailService;
    }

    @GetMapping("getAll")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> usersList = userService.getAllUsers();
        return ResponseEntity.ok(usersList);
    }

    @GetMapping("/get")
    public ResponseEntity<Object> getUserById(@RequestParam(name = "userId") Long userId) {
        Optional<Users> user = userService.getUserById(userId);
        return user.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body("Error"));
    }

    @GetMapping("/getUsernameByUserId")
    public ResponseEntity<String> getUsernameById(@RequestParam(name = "userId") Long userId) {
        Optional<Users> user = userService.getUserById(userId);
        return user.map(users -> ResponseEntity.ok(users.getUsername())).orElseGet(() -> ResponseEntity.badRequest().body("User not found"));

    }

    @GetMapping("/getByUsername")
    public ResponseEntity<List<Users>> getUsersByUsername(@RequestParam(name = "username") String username) {
        List<Users> usersList = userService.findAllUsersByUsername(username);
        return ResponseEntity.ok(usersList);
    }

    @GetMapping("/getIdsBySubusername")
    public ResponseEntity<List<Long>> getUsersBySubusername(@RequestParam(name = "username") String username) {
        List<Users> usersList = userService.findAllUsersBySubusername(username);
        List<Long> userIds = new ArrayList<Long>();

        for(Users user : usersList) {
            userIds.add(user.getId());
        }

        return ResponseEntity.ok(userIds);
    }

    @PutMapping("/banUser")
    public void banUserById(@RequestBody UserGetRequest userGetRequest) {
        Long userId = userGetRequest.getUserId();

        userService.banUserById(userId, true);

        Optional<Users> user = userService.getUserById(userId);

        //user.ifPresent(users -> emailService.sendMessage(users.getEmail(), "Questify User Ban", "You have been banned by an admin."));
    }

    @PutMapping("/unbanUser")
    public void unbanUserById(@RequestBody UserGetRequest userGetRequest) {
        Long userId = userGetRequest.getUserId();
        userService.banUserById(userId, false);
    }

    @DeleteMapping("/removeById")
    public ResponseEntity<Boolean> removeUserById(@RequestParam(name = "userId") Long userId) {
        boolean status = userService.removeUserById(userId);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/getUserIdByUsername")
    public ResponseEntity<List<Long>> getUserIdByUsername(@RequestParam(name = "username") String username) {
        List<Long> userIds = userService.getUsersIdByUsername(username);
        return ResponseEntity.ok(userIds);
    }

    @GetMapping("/scoreById")
    public ResponseEntity<Double> computeUserScore(@RequestParam(name = "userId") Long userId, HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        HttpHeaders httpHeaders = new HttpHeaders();

        if(token != null) {
            httpHeaders.set("Authorization", token);
        }

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        String urlQuestionScore = "http://localhost:8080/question/getQuestionScoreById?userId=" + userId;
        String urlAnswerScore = "http://localhost:8080/answers/getAnswerScoreById?userId=" + userId;

        ResponseEntity<Double> questionScore = restTemplate.exchange(
                urlQuestionScore,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<Double>() {}
        );

        ResponseEntity<Double> answerScore = restTemplate.exchange(
                urlAnswerScore,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<Double>() {}
        );

        Double finalScore = questionScore.getBody() + answerScore.getBody();

        return ResponseEntity.ok(finalScore);
    }
}
