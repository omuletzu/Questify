package com.example.app.services;

import com.example.app.jpaRepository.QuestionRepository;
import com.example.app.models.SimpleModels.Question;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
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

    public List<Question> getRecentNQuestions(int pageNumber, int limit) {
        Pageable pageable = PageRequest.of(pageNumber, limit, Sort.by(Sort.Order.desc("timestamp")));
        return questionRepository.findAllByOrderByTimestampDesc(pageable).getContent();
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

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public List<Question> filterBySubtitle(String subtitle) {
        return questionRepository.filterBySubtitle(subtitle);
    }

    public List<Question> filterByUserId(Long userId) {
        return questionRepository.findAllByUserId(userId);
    }

    public boolean editQuestion(Question question) {
        try {
            questionRepository.save(question);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public List<Question> getQuestionByUserId(Long userId) {
        return questionRepository.findAllByUserId(userId);
    }

    @Transactional
    @Modifying
    public void setQuestionStatusById(Long questionId, int status) {
        questionRepository.setQuestionStatusById(questionId, status);
    }
}
