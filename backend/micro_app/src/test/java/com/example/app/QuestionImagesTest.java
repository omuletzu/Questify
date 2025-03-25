package com.example.app;

import com.example.app.jpaRepository.QuestionImagesRepository;
import com.example.app.models.BetweenModels.QuestionImages;
import com.example.app.models.CompositePK.QuestionImagesPK;
import com.example.app.services.QuestionImagesService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class QuestionImagesTest {
    @InjectMocks
    private QuestionImagesService questionImagesService;

    @Mock
    private QuestionImagesRepository questionImagesRepository;

    @Test
    public void addQuestionImage() {
        QuestionImages questionImages = new QuestionImages(
                1L,
                1L
        );

        when(questionImagesRepository.save(questionImages)).thenReturn(questionImages);

        boolean addedQuestionImage = questionImagesService.addQuestionImage(questionImages);

        assertTrue(addedQuestionImage);
    }

    @Test
    public void findAllQuestionImagesByQuestionId() {
        Long id = 1L;

        List<QuestionImages> list = List.of(
                new QuestionImages(
                        id,
                        1L
                ),
                new QuestionImages(
                        id,
                        2L
                )
        );

        when(questionImagesRepository.findAllByQuestionId(id)).thenReturn(list);

        List<QuestionImages> fetchedList = questionImagesService.findAllQuestionImagesByQuestionId(id);

        assertEquals(fetchedList.size(), list.size());

        for(QuestionImages questionImages : fetchedList) {
            assertEquals(questionImages.getQuestionId(), id);
        }
    }

    @Test
    public void deleteAllQuestionImagesByQuestionId() {
        Long id = 1L;

        doNothing().when(questionImagesRepository).deleteAllByQuestionId(id);

        questionImagesService.deleteAllQuestionImagesByQuestionId(id);

        verify(questionImagesRepository, times(1)).deleteAllByQuestionId(id);
    }

    @Test
    public void deleteByQuestionImageId() {
        Long questionId = 1L;
        Long imageId = 1L;

        QuestionImagesPK key = new QuestionImagesPK(questionId, imageId);

        doNothing().when(questionImagesRepository).deleteById(key);

        questionImagesService.deleteByQuestionImageId(key);

        verify(questionImagesRepository, times(1)).deleteById(key);
    }
}
