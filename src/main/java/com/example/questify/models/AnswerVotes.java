package com.example.questify.models;

public class AnswerVotes {
    private Long userId;
    private Long answerId;
    private Boolean upOrDown;

    public AnswerVotes(Long userId, Long answerId, Boolean upOrDown) {
        this.userId = userId;
        this.answerId = answerId;
        this.upOrDown = upOrDown;
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

    public Boolean getUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(Boolean upOrDown) {
        this.upOrDown = upOrDown;
    }
}
