package com.example.questify.models.BetweenModels;

import com.example.questify.models.CompositePK.QuestionVotesPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table
@IdClass(QuestionVotesPK.class)
public class QuestionVotes {
    @Id
    private Long userId;
    @Id
    private Long questionId;
    private Boolean upOrDown;

    public QuestionVotes(Long userId, Long questionId, Boolean upOrDown) {
        this.userId = userId;
        this.questionId = questionId;
        this.upOrDown = upOrDown;
    }

    public QuestionVotes() {

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
