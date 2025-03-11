package com.example.questify.services;

import com.example.questify.models.JpaRepository.QuestionRepository;
import com.example.questify.models.SimpleModels.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public boolean addQuestion(Question question) {
        try{
            questionRepository.save(question);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean deleteQuestionById(Long id) {
        try{
            questionRepository.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public List<Question> filterBySubtitle(String subtitle) {
        return questionRepository.filterBySubtitle(subtitle);
    }

    public List<Question> filterByUserId(Long userId) {
        return questionRepository.findAllById(userId);
    }
}
