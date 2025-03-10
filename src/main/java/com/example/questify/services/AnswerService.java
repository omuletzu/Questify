package com.example.questify.services;

import com.example.questify.models.JpaRepository.AnswerRepository;
import com.example.questify.models.SimpleModels.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository){
        this.answerRepository = answerRepository;
    }

    public List<Answer> getAnswers() {
        return null;
    }
}
