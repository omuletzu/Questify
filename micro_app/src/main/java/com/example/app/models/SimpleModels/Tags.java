package com.example.app.models.SimpleModels;

import jakarta.persistence.*;

@Entity
@Table(name = "tags")
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

    @Column(name = "id")
    private Long id;

    @Column(name = "tag_name")
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
