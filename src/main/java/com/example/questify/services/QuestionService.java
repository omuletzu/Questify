package com.example.questify.services;

import com.example.questify.models.JpaRepository.QuestionRepository;
import com.example.questify.models.SimpleModels.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getRecentNQuestions(int limit) {
        return questionRepository.getRecentNQuestions(limit);
    }
}
