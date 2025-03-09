package com.example.questify.models;

import java.time.LocalDateTime;

public class Answer {
    private Long id;
    private Long userId;
    private Long question_id;
    private String text;
    private String imageId;
    private LocalDateTime timestamp;
    private Double score;

    public Answer(Long id, Long userId, Long question_id, String text, String imageId, LocalDateTime timestamp, Double score) {
        this.id = id;
        this.userId = userId;
        this.question_id = question_id;
        this.text = text;
        this.imageId = imageId;
        this.timestamp = timestamp;
        this.score = score;
    }

    public Answer(Long userId, Long question_id, String text, String imageId, LocalDateTime timestamp, Double score) {
        this.userId = userId;
        this.question_id = question_id;
        this.text = text;
        this.imageId = imageId;
        this.timestamp = timestamp;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getuserId() {
        return userId;
    }

    public void setuserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getimageId() {
        return imageId;
    }

    public void setimageId(String imageId) {
        this.imageId = imageId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
