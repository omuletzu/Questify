package com.example.questify.models.JpaRepository;

import com.example.questify.models.BetweenModels.AnswerVotes;
import com.example.questify.models.BetweenModels.QuestionVotes;
import com.example.questify.models.CompositePK.AnswerVotesPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerVoteRepository extends JpaRepository<AnswerVotes, AnswerVotesPK> {
    public Optional<AnswerVotes> findByAnswerIdAndUserId(Long answerId, Long userId);

    public long countByUpOrDownTrueAndUserId(Long userId);
    public long countByUpOrDownFalseAndUserId(Long userId);

    public long countByUpOrDownTrueAndAnswerId(Long questionId);

    public long countByUpOrDownFalseAndAnswerId(Long questionId);
}