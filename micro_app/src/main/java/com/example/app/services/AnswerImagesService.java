package com.example.app.services;

import com.example.app.jpaRepository.AnswerImagesRespository;
import com.example.app.models.BetweenModels.AnswerImages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerImagesService {
    private AnswerImagesRespository answerImagesRespository;

    @Autowired
    public AnswerImagesService(AnswerImagesRespository answerImagesRespository){
        this.answerImagesRespository = answerImagesRespository;
    }

    public boolean addAnswerImages(AnswerImages answerImages) {
        try{
            answerImagesRespository.save(answerImages);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public List<AnswerImages> getAllImagesId(Long answerId) {
        return answerImagesRespository.findByAnswerId(answerId);
    }

    public void deleteAllById(Long answerId) {
        answerImagesRespository.deleteAnswerImagesByAnswerId(answerId);
    }
}
