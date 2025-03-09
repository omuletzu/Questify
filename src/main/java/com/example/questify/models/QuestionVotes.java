package com.example.questify.models;

public class QuestionVotes {
    private Long userId;
    private Long questionId;
    private Boolean upOrDown;

    public QuestionVotes(Long userId, Long questionId, Boolean upOrDown) {
        this.userId = userId;
        this.questionId = questionId;
        this.upOrDown = upOrDown;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getquestionId() {
        return questionId;
    }

    public void setquestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Boolean getUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(Boolean upOrDown) {
        this.upOrDown = upOrDown;
    }
}
