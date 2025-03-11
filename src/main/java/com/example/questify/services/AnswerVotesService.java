package com.example.questify.services;

import com.example.questify.models.BetweenModels.AnswerVotes;
import com.example.questify.models.BetweenModels.QuestionVotes;
import com.example.questify.models.JpaRepository.AnswerVoteRepository;
import com.example.questify.models.JpaRepository.QuestionVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerVotesService {
    private AnswerVoteRepository answerVoteRepository;

    @Autowired
    public AnswerVotesService(AnswerVoteRepository answerVoteRepository) {
        this.answerVoteRepository = answerVoteRepository;
    }

    public Optional<AnswerVotes> findAnswerVote(Long answerId, Long userId) {
        return answerVoteRepository.findByAnswerIdAndUserId(answerId, userId);
    }

    public boolean addAnswerVote(AnswerVotes answerVotes) {
        try {
            answerVoteRepository.save(answerVotes);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean deleteAnswerVote(AnswerVotes answerVotes) {
        try {
            answerVoteRepository.delete(answerVotes);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public long countByUpOrDownTrueByUserId(Long userId){
        return answerVoteRepository.countByUpOrDownTrueAndUserId(userId);
    }

    public long countByUpOrDownFalseByUserId(Long userId){
        return answerVoteRepository.countByUpOrDownFalseAndUserId(userId);
    }

    public long countByUpOrDownTrueByAnswerId(Long questionId) {
        return answerVoteRepository.countByUpOrDownTrueAndAnswerId(questionId);
    }

    public long countByUpOrDownFalseByAnswerId(Long questionId) {
        return answerVoteRepository.countByUpOrDownFalseAndAnswerId(questionId);
    }
}
