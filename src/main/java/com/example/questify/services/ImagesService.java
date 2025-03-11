package com.example.questify.services;

import com.example.questify.models.JpaRepository.ImagesRepository;
import com.example.questify.models.SimpleModels.Images;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
