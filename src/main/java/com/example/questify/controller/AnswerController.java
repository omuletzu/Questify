package com.example.questify.controller;

import com.example.questify.models.SimpleModels.Answer;
import com.example.questify.services.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {
    private AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService){
        this.answerService = answerService;
    }

    @GetMapping("/getForQuestion")
    public List<Answer> getAnswersForQuestionId(@RequestParam(name = "id") Long id) {
        List<Answer> answers = answerService.getAnswersByQuestionId(id);
        answers.sort(Comparator.comparing(Answer::getTimestamp).reversed());
        return answers;
    }
}
