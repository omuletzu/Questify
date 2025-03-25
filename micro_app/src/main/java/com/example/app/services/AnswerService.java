package com.example.app.services;

import com.example.app.jpaRepository.AnswerRepository;
import com.example.app.models.SimpleModels.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository){
        this.answerRepository = answerRepository;
    }

    public List<Answer> getAnswersByQuestionId(Long id) {
        return answerRepository.findAnswerByQuestionId(id);
    }

    public boolean addAnswerForQuestionId(Answer answer) {
        try{
            answerRepository.save(answer);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public Optional<Answer> getAnswerById(Long id) {
        return answerRepository.findById(id);
    }

    public boolean deleteAnswerById(Long id) {
        try {
            answerRepository.deleteById(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public List<Answer> getAnswerByUserId(Long userId) {
        return answerRepository.findAllByUserId(userId);
    }
}
