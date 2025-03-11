package com.example.questify.controller;

import com.example.questify.models.SimpleModels.Answer;
import com.example.questify.models.SimpleModels.Question;
import com.example.questify.models.SimpleModels.Users;
import com.example.questify.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private QuestionVotesService questionVotesService;
    private AnswerVotesService answerVotesService;
    private QuestionService questionService;
    private AnswerService answerService;

    @Autowired
    public UserController(UserService userService,
                          QuestionVotesService questionVotesService,
                          AnswerVotesService answerVotesService,
                          QuestionService questionService,
                          AnswerService answerService){
        this.userService = userService;
        this.questionVotesService = questionVotesService;
        this.answerVotesService = answerVotesService;
        this.questionService = questionService;
        this.answerService = answerService;
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

    @PutMapping("/banUser")
    public void banUserById(@RequestParam(name = "id") Long id) {
        userService.banUserById(id, true);
    }

    @PutMapping("/unbanUser")
    public void unbanUserById(@RequestParam(name = "id") Long id) {
        userService.banUserById(id, false);
    }

    @DeleteMapping("/remove")
    public boolean removeUserById(@RequestParam(name = "id") Long id) {return userService.removeUserById(id);}

    @GetMapping("/score")
    public Double computeUserScore(@RequestParam(name = "id") Long id) {
        List<Question> questionListByUser = questionService.getQuestionByUserId(id);
        List<Answer> answerListByUser = answerService.getAnswerByUserId(id);

        double finalScore = 0.0;

        for (Question question : questionListByUser) {
            finalScore += 2.5 * questionVotesService.countByUpDownTrueByQuestionId(question.getId());
            finalScore -= 1.5 * questionVotesService.countByUpDownFalseByQuestionId(question.getId());
        }

        for(Answer answer : answerListByUser) {
            finalScore += 5 * answerVotesService.countByUpDownTrueByAnswerId(answer.getId());
            finalScore -= 2.5 * answerVotesService.countByUpDownFalseByAnswerId(answer.getId());
        }

        long numberOfAnswerVotesDown = answerVotesService.countByUpDownFalseByUserId(id);
        finalScore -= 1.5 * numberOfAnswerVotesDown;

        return finalScore;
    }
}
