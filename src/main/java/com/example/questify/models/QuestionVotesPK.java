package com.example.questify.models;

import java.io.Serializable;
import java.util.Objects;

public class QuestionVotesPK implements Serializable {
    private Long userId;
    private Long questionId;

    public QuestionVotesPK() {}

    public QuestionVotesPK(Long userId, Long questionId) {
        this.userId = userId;
        this.questionId = questionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }

        if(!(obj instanceof QuestionVotesPK questionVotesPK)){
            return false;
        }

        return Objects.equals(questionId, questionVotesPK.questionId) && Objects.equals(userId, questionVotesPK.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, userId);
    }
}
