package com.example.app.models.CompositePK;

import java.io.Serializable;
import java.util.Objects;

public class AnswerVotesPK implements Serializable {
    private Long answerId;
    private Long userId;

    public AnswerVotesPK() {}

    public AnswerVotesPK(Long answerId, Long userId) {
        this.answerId = answerId;
        this.userId = userId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }

        if(!(obj instanceof AnswerVotesPK answerVotesPK)){
            return false;
        }

        return Objects.equals(answerId, answerVotesPK.answerId) && Objects.equals(userId, answerVotesPK.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId, userId);
    }
}
