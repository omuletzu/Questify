package com.example.questify.services;

import com.example.questify.models.JpaRepository.TagsRepository;
import com.example.questify.models.SimpleModels.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagsService {
    private TagsRepository tagsRepository;

    @Autowired
    public TagsService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public boolean addTag(Tags tag) {
        try {
            tagsRepository.save(tag);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public Optional<Tags> searchForTag(String tag) {
        return tagsRepository.findByTagName(tag);
    }

    public boolean deleteTagById(Long id) {
        try {
            tagsRepository.deleteById(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
