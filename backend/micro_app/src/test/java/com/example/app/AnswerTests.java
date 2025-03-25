package com.example.app;

import com.example.app.jpaRepository.AnswerRepository;
import com.example.app.models.SimpleModels.Answer;
import com.example.app.services.AnswerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class AnswerTests {
    @InjectMocks
    private AnswerService answerService;

    @Mock
    private AnswerRepository answerRepository;

    @Test
    public void getAnswersByQuestionId() {
        Long id = 1L;

        List<Answer> list = List.of(new Answer(
                1L,
                1L,
                id,
                "text1",
                LocalDateTime.now()), new Answer(
                2L,
                1L,
                id,
                "text2",
                LocalDateTime.now()
        ));

        when(answerRepository.findAnswerByQuestionId(id)).thenReturn(list);

        List<Answer> fetchedList = answerService.getAnswersByQuestionId(id);

        for(Answer answer : fetchedList) {
            assertEquals(answer.getQuestionId(), id);
        }
    }

    @Test
    public void addAnswerForQuestionId() {
        Answer answer = new Answer(
                1L,
                1L,
                1L,
                "text1",
                LocalDateTime.now());

        when(answerRepository.save(answer)).thenReturn(answer);

        boolean saveAnswer = answerService.addAnswerForQuestionId(answer);

        assertTrue(saveAnswer);
    }

    @Test
    public void getAnswerById() {
        Long id = 1L;

        Answer answer = new Answer(
                1L,
                1L,
                1L,
                "text1",
                LocalDateTime.now());

        when(answerRepository.findById(id)).thenReturn(Optional.of(answer));

        Optional<Answer> fetchedAnswer = answerService.getAnswerById(id);

        assertTrue(fetchedAnswer.isPresent());
        assertEquals(id, fetchedAnswer.get().getId());
    }

    @Test
    public void deleteAnswerById() {
        Long id = 1L;

        doNothing().when(answerRepository).deleteById(id);

        answerService.deleteAnswerById(id);

        verify(answerRepository, times(1)).deleteById(id);
    }

    @Test
    public void getAnswerByUserId() {
        Long id = 1L;

        List<Answer> list = List.of(new Answer(
                1L,
                id,
                1L,
                "text1",
                LocalDateTime.now()), new Answer(
                2L,
                id,
                1L,
                "text2",
                LocalDateTime.now()));

        when(answerRepository.findAllByUserId(id)).thenReturn(list);

        List<Answer> fetchedList = answerService.getAnswerByUserId(id);

        assertEquals(list.size(), fetchedList.size());

        for(Answer answer : fetchedList) {
            assertEquals(answer.getUserId(), id);
        }
    }
}
