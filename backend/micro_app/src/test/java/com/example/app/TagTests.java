package com.example.app;

import com.example.app.jpaRepository.TagsRepository;
import com.example.app.models.SimpleModels.Tags;
import com.example.app.services.TagsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
@SpringBootTest
class TagTests {
    @InjectMocks
    private TagsService tagsService;

    @Mock
    private TagsRepository tagsRepository;

    @Test
    public void addTag() {
        Long id = 1L;

        Tags tag = new Tags(
                id,
                "tag"
        );

        when(tagsRepository.save(tag)).thenReturn(tag);

        Tags addedTag = tagsService.addTag(tag);

        assertEquals(addedTag.getId(), id);
    }

    @Test
    public void searchForTag() {
        String tagName = "tag";

        Tags tag = new Tags(
                1L,
                tagName
        );

        when(tagsRepository.findByTagName(tagName)).thenReturn(Optional.of(tag));

        Optional<Tags> fetchedTag = tagsService.searchForTag(tagName);

        assertTrue(fetchedTag.isPresent());
        assertEquals(fetchedTag.get().getTagName(), tagName);
    }

    @Test
    public void deleteTagById() {
        Long id = 1L;

        doNothing().when(tagsRepository).deleteById(id);

        tagsService.deleteTagById(id);

        verify(tagsRepository, times(1)).deleteById(id);
    }

    @Test
    public void findTagNameByTagId() {
        Long id = 1L;

        Tags tag = new Tags(
                id,
                "tag"
        );

        when(tagsRepository.findById(id)).thenReturn(Optional.of(tag));

        Optional<Tags> fetchedTag = tagsService.findTagNameByTagId(id);

        assertTrue(fetchedTag.isPresent());
        assertEquals(fetchedTag.get().getTagName(), tag.getTagName());
    }
}
