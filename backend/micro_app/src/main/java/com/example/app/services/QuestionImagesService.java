package com.example.app.services;

import com.example.app.jpaRepository.QuestionImagesRepository;
import com.example.app.models.BetweenModels.QuestionImages;
import com.example.app.models.CompositePK.QuestionImagesPK;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionImagesService {
    private QuestionImagesRepository questionImagesRepository;

    @Autowired
    public QuestionImagesService(QuestionImagesRepository questionImagesRepository) {
        this.questionImagesRepository = questionImagesRepository;
    }

    public boolean addQuestionImage(QuestionImages questionImages) {
        try{
            questionImagesRepository.save(questionImages);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public List<QuestionImages> findAllQuestionImagesByQuestionId(Long questionId) {
        return questionImagesRepository.findAllByQuestionId(questionId);
    }

    @Transactional
    public void deleteAllQuestionImagesByQuestionId(Long questionId) {
        questionImagesRepository.deleteAllByQuestionId(questionId);
    }

    @Transactional
    public void deleteByQuestionImageId(QuestionImagesPK questionImagesPK) {
        questionImagesRepository.deleteById(questionImagesPK);
    }
}
