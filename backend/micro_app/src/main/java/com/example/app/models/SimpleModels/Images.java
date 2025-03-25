package com.example.app.models.SimpleModels;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Images {
    @Id
    @SequenceGenerator(
            name = "images_sequence",
            sequenceName = "images_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "images_sequence"
    )

    @Column(name = "id")
    private Long id;

    @Column(name = "image_data")
    private String imageData;

    public Images(Long id, String imageData) {
        this.id = id;
        this.imageData = imageData;
    }

    public Images(String imageData) {
        this.imageData = imageData;
    }

    public Images() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
