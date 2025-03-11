package com.example.questify.services;

import com.example.questify.models.BetweenModels.QuestionTags;
import com.example.questify.models.JpaRepository.QuestionTagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionTagsService {
    private QuestionTagsRepository questionTagsRepository;

    @Autowired
    public QuestionTagsService(QuestionTagsRepository questionTagsRepository) {
        this.questionTagsRepository = questionTagsRepository;
    }

    public boolean addQuestionTags(QuestionTags questionTags) {
        try {
            questionTagsRepository.save(questionTags);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
