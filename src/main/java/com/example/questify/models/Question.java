package com.example.questify.models;

import java.time.LocalDateTime;

public class Question {

    private Long id;
    private Long userId;
    private String title;
    private String text;
    private String imageId;
    private int status;
    private LocalDateTime timestamp;
    private String tags;
    private Double score;

    public Question(Long id, Long userId, String title, String text, String imageId, int status, LocalDateTime timestamp, String tags, Double score) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.imageId = imageId;
        this.status = status;
        this.timestamp = timestamp;
        this.tags = tags;
        this.score = score;
    }

    public Question(Long userId, String title, String text, String imageId, int status, LocalDateTime timestamp, String tags, Double score) {
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.imageId = imageId;
        this.status = status;
        this.timestamp = timestamp;
        this.tags = tags;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
