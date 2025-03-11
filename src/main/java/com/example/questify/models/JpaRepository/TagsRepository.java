package com.example.questify.models.JpaRepository;

import com.example.questify.models.SimpleModels.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagsRepository extends JpaRepository<Tags, Long> {
    public List<Tags> findAllByTagName(String tag);
}
