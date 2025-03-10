package com.example.questify.models.SimpleModels;

import jakarta.persistence.*;

@Entity
@Table
public class Users {
    @Id
    @SequenceGenerator(
            name = "users_sequence",
            sequenceName = "users_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_sequence"
    )
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Double score;
    private Boolean banned;
    private Boolean isAdmin;

    public Users(Long id, Boolean isAdmin, String username, String password, Double score, Boolean banned, String email, String phone) {
        this.id = id;
        this.isAdmin = isAdmin;
        this.username = username;
        this.password = password;
        this.score = score;
        this.banned = banned;
        this.email = email;
        this.phone = phone;
    }

    public Users(String username, Boolean isAdmin, String password, Double score, Boolean banned, String email, String phone) {
        this.isAdmin = isAdmin;
        this.username = username;
        this.password = password;
        this.score = score;
        this.banned = banned;
        this.email = email;
        this.phone = phone;
    }

    public Users() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
