package com.example.questify.services;

import com.example.questify.models.BetweenModels.QuestionImages;
import com.example.questify.models.JpaRepository.QuestionImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
