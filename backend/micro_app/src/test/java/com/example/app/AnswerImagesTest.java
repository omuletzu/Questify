package com.example.app;

import com.example.app.jpaRepository.AnswerImagesRespository;
import com.example.app.models.BetweenModels.AnswerImages;
import com.example.app.models.CompositePK.AnswerImagePK;
import com.example.app.services.AnswerImagesService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AnswerImagesTest {
    @InjectMocks
    private AnswerImagesService answerImagesService;

    @Mock
    private AnswerImagesRespository answerImagesRespository;

    @Test
    public void addAnswerImages() {
        AnswerImages answerImages = new AnswerImages(
                1L,
                1L
        );

        when(answerImagesRespository.save(answerImages)).thenReturn(answerImages);

        boolean addedAnswerImages = answerImagesService.addAnswerImages(answerImages);

        assertTrue(addedAnswerImages);
    }

    @Test
    public void getAllImagesId() {
        Long id = 1L;

        List<AnswerImages> list = List.of(
                new AnswerImages(
                        id,
                        2L
                ),
                new AnswerImages(
                        id,
                        2L
                )
        );

        when(answerImagesRespository.findByAnswerId(id)).thenReturn(list);

        List<AnswerImages> fetchedList = answerImagesService.getAllImagesId(id);

        assertEquals(list.size(), fetchedList.size());

        for(AnswerImages answerImages : fetchedList) {
            assertEquals(answerImages.getAnswerId(), id);
        }
    }

    @Test
    public void deleteAllById() {
        Long id = 1L;

        doNothing().when(answerImagesRespository).deleteAnswerImagesByAnswerId(id);

        answerImagesService.deleteAllById(id);

        verify(answerImagesRespository, times(1)).deleteAnswerImagesByAnswerId(id);
    }
}
