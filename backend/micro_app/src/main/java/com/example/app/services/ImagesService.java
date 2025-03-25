package com.example.app.services;

import com.example.app.jpaRepository.ImagesRepository;
import com.example.app.models.SimpleModels.Images;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImagesService {
    private ImagesRepository imagesRepository;

    @Autowired
    public ImagesService(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    public boolean addImage(Images image) {
        try{
            imagesRepository.save(image);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public void deleteById(Long id) {
        imagesRepository.deleteById(id);
    }

    public Optional<Images> findByImageId(Long imageId) {
        return imagesRepository.findById(imageId);
    }
}
