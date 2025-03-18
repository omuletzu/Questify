package com.example.app.services;

import com.example.app.jpaRepository.TagsRepository;
import com.example.app.models.SimpleModels.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTML;
import java.util.Optional;

@Service
public class TagsService {
    private TagsRepository tagsRepository;

    @Autowired
    public TagsService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public Tags addTag(Tags tag) {
        return tagsRepository.save(tag);
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

    public Optional<Tags> findTagNameByTagId(Long tagId) {
        return tagsRepository.findById(tagId);
    }
}
