package com.example.questify.models;

public class Images {
    private int id;
    private byte[] imageData;

    public Images(int id, byte[] imageData) {
        this.id = id;
        this.imageData = imageData;
    }

    public Images(byte[] imageData) {
        this.imageData = imageData;
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
