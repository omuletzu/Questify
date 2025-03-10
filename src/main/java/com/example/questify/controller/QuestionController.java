package com.example.questify.controller;

import com.example.questify.models.SimpleModels.Question;
import com.example.questify.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/getRecent")
    public List<Question> getQuestions(@RequestParam(name = "limit") int limit) {
        return questionService.getRecentNQuestions(limit);
    }
}
