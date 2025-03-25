package com.example.app.services;

import com.example.app.jpaRepository.QuestionVotesRepository;
import com.example.app.models.BetweenModels.QuestionVotes;
import com.example.app.models.Requests.QuestionVoteRequest;
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

    public void flipQuestionVote(Long questionId) {
        questionVotesRepository.flipById(questionId);
    }

    public void setQuestionVote(QuestionVotes questionVotes) {
        questionVotesRepository.save(questionVotes);
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

    public long countByUpDownTrueByUserId(Long userId){
        return questionVotesRepository.countByUpDownTrueAndUserId(userId);
    }

    public long countByUpDownFalseByUserId(Long userId){
        return questionVotesRepository.countByUpDownFalseAndUserId(userId);
    }

    public long countByUpDownTrueByQuestionId(Long questionId) {
        return questionVotesRepository.countByUpDownTrueAndQuestionId(questionId);
    }

    public long countByUpDownFalseByQuestionId(Long questionId) {
        return questionVotesRepository.countByUpDownFalseAndQuestionId(questionId);
    }
}
