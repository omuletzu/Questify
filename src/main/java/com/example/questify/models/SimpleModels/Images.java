package com.example.questify.models.SimpleModels;

import jakarta.persistence.*;

@Entity
@Table
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
    private int id;
    private byte[] imageData;

    public Images(int id, byte[] imageData) {
        this.id = id;
        this.imageData = imageData;
    }

    public Images(byte[] imageData) {
        this.imageData = imageData;
    }

    public Images() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
