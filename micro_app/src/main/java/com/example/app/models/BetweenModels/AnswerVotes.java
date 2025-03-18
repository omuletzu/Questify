package com.example.app.models.BetweenModels;

import com.example.app.models.CompositePK.AnswerVotesPK;
import jakarta.persistence.*;

@Entity
@Table(name = "answervotes")
@IdClass(AnswerVotesPK.class)
public class AnswerVotes {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "answer_id")
    private Long answerId;

    @Column(name = "up_down")
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
