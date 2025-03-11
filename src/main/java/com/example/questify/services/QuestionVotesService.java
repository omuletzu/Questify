package com.example.questify.services;

import com.example.questify.models.BetweenModels.QuestionVotes;
import com.example.questify.models.JpaRepository.QuestionVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionVotesService {
    private QuestionVotesRepository questionVotesRepository;

    @Autowired
    public QuestionVotesService(QuestionVotesRepository questionVotesRepository) {
        this.questionVotesRepository = questionVotesRepository;
    }

    public Optional<QuestionVotes> findQuestionVote(Long questionId, Long userId) {
        return questionVotesRepository.findByQuestionIdAndUserId(questionId, userId);
    }

    public boolean addQuestionVote(QuestionVotes questionVotes) {
        try {
            questionVotesRepository.save(questionVotes);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean deleteQuestionVote(QuestionVotes questionVotes) {
        try {
            questionVotesRepository.delete(questionVotes);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public long countByUpOrDownTrueByUserId(Long userId){
        return questionVotesRepository.countByUpOrDownTrueAndUserId(userId);
    }

    public long countByUpOrDownFalseByUserId(Long userId){
        return questionVotesRepository.countByUpOrDownFalseAndUserId(userId);
    }

    public long countByUpOrDownTrueByQuestionId(Long questionId) {
        return questionVotesRepository.countByUpOrDownTrueAndQuestionId(questionId);
    }

    public long countByUpOrDownFalseByQuestionId(Long questionId) {
        return questionVotesRepository.countByUpOrDownFalseAndQuestionId(questionId);
    }
}
