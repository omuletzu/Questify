package com.example.questify.models.JpaRepository;

import com.example.questify.models.SimpleModels.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagsRepository extends JpaRepository<Tags, Long> {
    public Optional<Tags> findByTagName(String tag);
}
