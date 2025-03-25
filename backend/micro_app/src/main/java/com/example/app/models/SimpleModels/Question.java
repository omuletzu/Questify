package com.example.app.models.SimpleModels;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @SequenceGenerator(
            name = "question_sequence",
            sequenceName = "question_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "question_sequence"
    )

    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "status")
    private int status;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public Question(Long id, Long userId, String title, String text, int status, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Question(Long userId, String title, String text, int status, LocalDateTime timestamp) {
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Question() {

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
}
