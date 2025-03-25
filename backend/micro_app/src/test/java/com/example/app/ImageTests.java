package com.example.app;

import com.example.app.jpaRepository.ImagesRepository;
import com.example.app.models.SimpleModels.Images;
import com.example.app.services.ImagesService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class ImageTests {
    @InjectMocks
    private ImagesService imagesService;

    @Mock
    public ImagesRepository imagesRepository;

    @Test
    public void addImage() {
        Images image = new Images(
                1L,
                "data"
        );

        when(imagesRepository.save(image)).thenReturn(image);

        boolean addedImage = imagesService.addImage(image);

        assertTrue(addedImage);
    }

    @Test
    public void deleteById() {
        Long id = 1L;

        doNothing().when(imagesRepository).deleteById(id);

        imagesService.deleteById(id);

        verify(imagesRepository, times(1)).deleteById(id);
    }

    @Test
    public void findByImageId() {
        Long id = 1L;

        Images image = new Images(
                id,
                "data"
        );

        when(imagesRepository.findById(id)).thenReturn(Optional.of(image));

        Optional<Images> fetchedImage = imagesService.findByImageId(id);

        assertTrue(fetchedImage.isPresent());
        assertEquals(fetchedImage.get().getId(), id);
    }
}
