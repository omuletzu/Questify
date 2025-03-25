package com.example.app;

import com.example.app.jpaRepository.QuestionTagsRepository;
import com.example.app.models.BetweenModels.QuestionTags;
import com.example.app.services.QuestionTagsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class QuestionTagsTest {
    @InjectMocks
    private QuestionTagsService questionTagsService;

    @Mock
    private QuestionTagsRepository questionTagsRepository;

    @Test
    public void addQuestionTags() {
        QuestionTags questionTags = new QuestionTags(
                1L,
                1L
        );

        when(questionTagsRepository.save(questionTags)).thenReturn(questionTags);

        boolean addedQuestionTag = questionTagsService.addQuestionTags(questionTags);

        assertTrue(addedQuestionTag);
    }

    @Test
    public void findAllQuestionTagsByQuestionId() {
        Long id = 1L;

        List<QuestionTags> list = List.of(
                new QuestionTags(
                        1L,
                        id
                ),
                new QuestionTags(
                        1L,
                        id
                )
        );

        when(questionTagsRepository.findAllByQuestionId(id)).thenReturn(list);

        List<QuestionTags> fetchedList = questionTagsService.findAllQuestionTagsByQuestionId(id);

        assertEquals(fetchedList.size(), list.size());

        for(QuestionTags questionTags : fetchedList) {
            assertEquals(questionTags.getQuestionId(), id);
        }
    }

    @Test
    public void findAllQuestionTagsByTagId() {
        Long id = 1L;

        List<QuestionTags> list = List.of(
                new QuestionTags(
                        id,
                        1L
                ),
                new QuestionTags(
                        id,
                        1L
                )
        );

        when(questionTagsRepository.findAllByTagIdIs(id)).thenReturn(list);

        List<QuestionTags> fetchedList = questionTagsService.findAllQuestionTagsByTagId(id);

        assertEquals(fetchedList.size(), list.size());

        for(QuestionTags questionTags : fetchedList) {
            assertEquals(questionTags.getTagId(), id);
        }
    }

    @Test
    public void deleteAllByQuestionId() {
        Long id = 1L;

        doNothing().when(questionTagsRepository).deleteAllByQuestionId(id);

        questionTagsService.deleteAllByQuestionId(id);

        verify(questionTagsRepository, times(1)).deleteAllByQuestionId(id);
    }
}
