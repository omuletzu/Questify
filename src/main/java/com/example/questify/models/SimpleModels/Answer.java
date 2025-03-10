package com.example.questify.models.SimpleModels;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Answer {
    @Id
    @SequenceGenerator(
            name = "answer_sequence",
            sequenceName = "answer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "answer_sequence"
    )
    private Long id;
    private Long userId;
    private Long questionId;
    private String text;
    private LocalDateTime timestamp;
    private int upVotes;
    private int downVotes;
    @Transient
    private int score;

    public Answer(Long id, Long userId, Long questionId, String text, LocalDateTime timestamp, int upVotes, int downVotes) {
        this.id = id;
        this.userId = userId;
        this.questionId = questionId;
        this.text = text;
        this.timestamp = timestamp;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
    }

    public Answer(Long userId, Long questionId, String text, LocalDateTime timestamp, int upVotes, int downVotes) {
        this.userId = userId;
        this.questionId = questionId;
        this.text = text;
        this.timestamp = timestamp;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
    }

    public Answer() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    public int getScore() {
        return upVotes - downVotes;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
