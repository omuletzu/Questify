package com.example.app.jpaRepository;

import com.example.app.models.BetweenModels.AnswerVotes;
import com.example.app.models.CompositePK.AnswerVotesPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerVoteRepository extends JpaRepository<AnswerVotes, AnswerVotesPK> {
    public Optional<AnswerVotes> findByAnswerIdAndUserId(Long answerId, Long userId);

    public long countByUpDownTrueAndUserId(Long userId);
    public long countByUpDownFalseAndUserId(Long userId);

    public long countByUpDownTrueAndAnswerId(Long questionId);

    public long countByUpDownFalseAndAnswerId(Long questionId);
}