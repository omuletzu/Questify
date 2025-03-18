package com.example.app.services;

import com.example.app.jpaRepository.AnswerVoteRepository;
import com.example.app.models.BetweenModels.AnswerVotes;
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

    public long countByUpDownTrueByUserId(Long userId){
        return answerVoteRepository.countByUpDownTrueAndUserId(userId);
    }

    public long countByUpDownFalseByUserId(Long userId){
        return answerVoteRepository.countByUpDownFalseAndUserId(userId);
    }

    public long countByUpDownTrueByAnswerId(Long answerId) {
        return answerVoteRepository.countByUpDownTrueAndAnswerId(answerId);
    }

    public long countByUpDownFalseByAnswerId(Long answerId) {
        return answerVoteRepository.countByUpDownFalseAndAnswerId(answerId);
    }
}
