package com.example.questify.services;

import com.example.questify.models.BetweenModels.QuestionTags;
import com.example.questify.models.JpaRepository.QuestionTagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<QuestionTags> findAllQuestionTagsByQuestionId(Long questionId) {
        return questionTagsRepository.findAllByQuestionId(questionId);
    }

    public List<QuestionTags> findAllQuestionTagsByTagId(Long tagId) {
        return questionTagsRepository.findAllByTagIdIs(tagId);
    }
}
