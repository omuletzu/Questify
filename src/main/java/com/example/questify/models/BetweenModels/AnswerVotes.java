package com.example.questify.models.BetweenModels;

import com.example.questify.models.CompositePK.AnswerVotesPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table
@IdClass(AnswerVotesPK.class)
public class AnswerVotes {
    @Id
    private Long userId;
    @Id
    private Long answerId;
    private Boolean upDown;

    public AnswerVotes(Long userId, Long answerId, Boolean upOrDown) {
        this.userId = userId;
        this.answerId = answerId;
        this.upDown = upOrDown;
    }

    public AnswerVotes() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Boolean getUpDown() {
        return upDown;
    }

    public void setUpDown(Boolean upOrDown) {
        this.upDown = upOrDown;
    }
}
