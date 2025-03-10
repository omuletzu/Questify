package com.example.questify.models;

import jakarta.persistence.*;

@Entity
@Table
public class Tags {
    @Id
    @SequenceGenerator(
            name = "tags_sequence",
            sequenceName = "tags_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tags_sequence"
    )
    private Long id;
    private String tagName;

    public Tags(Long id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }

    public Tags(String tagName) {
        this.tagName = tagName;
    }

    public Tags() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
