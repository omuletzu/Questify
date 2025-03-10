package com.example.questify.models;

import java.time.LocalDateTime;

public class Question {
    private Long id;
    private Long userId;
    private String title;
    private String text;
    private int status;
    private LocalDateTime timestamp;
    private int upVotes;
    private int downVotes;

    public Question(Long id, Long userId, String title, String text, int status, LocalDateTime timestamp, int upVotes, int downVotes) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.status = status;
        this.timestamp = timestamp;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
    }

    public Question(Long userId, String title, String text, int status, LocalDateTime timestamp, int upVotes, int downVotes) {
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.status = status;
        this.timestamp = timestamp;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
    }

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
}
