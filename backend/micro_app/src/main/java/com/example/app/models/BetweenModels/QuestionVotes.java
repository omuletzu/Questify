package com.example.app.models.BetweenModels;

import com.example.app.models.CompositePK.QuestionVotesPK;
import jakarta.persistence.*;

@Entity
@Table(name = "questionvotes")
@IdClass(QuestionVotesPK.class)
public class QuestionVotes {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "up_down")
    private Boolean upDown;

    public QuestionVotes(Long userId, Long questionId, Boolean upOrDown) {
        this.userId = userId;
        this.questionId = questionId;
        this.upDown = upOrDown;
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

    public Boolean getUpDown() {
        return upDown;
    }

    public void setUpDown(Boolean upOrDown) {
        this.upDown = upOrDown;
    }
}
