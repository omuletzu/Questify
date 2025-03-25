package com.example.app;

import com.example.app.jpaRepository.QuestionRepository;
import com.example.app.models.SimpleModels.Question;
import com.example.app.services.QuestionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import javax.swing.text.html.Option;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class QuestionTests {

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepository;

    @Test
    public void getRecentNQuestions() {
        int pageNumber = 0;
        int limit = 10;
        List<Question> list = List.of(new Question(
                1L,
                1L,
                "title",
                "text",
                0,
                LocalDateTime.now()
        ));
        Pageable pageable = PageRequest.of(pageNumber, limit, Sort.by(Sort.Order.desc("timestamp")));
        Page<Question> page = new PageImpl<>(list, pageable, list.size());
        when(questionRepository.findAllByOrderByTimestampDesc(pageable)).thenReturn(page);

        List<Question> fetchList = questionRepository.findAllByOrderByTimestampDesc(pageable).getContent();

        assertEquals(fetchList, list);
    }

    @Test
    public void getQuestionById() {
        Long id = 1L;

        Question question = new Question(
                id,
                1L,
                "title",
                "text",
                0,
                LocalDateTime.now());

        when(questionRepository.findById(id)).thenReturn(Optional.of(question));

        Optional<Question> fetchQuestion = questionService.getQuestionById(id);

        assertTrue(fetchQuestion.isPresent());
        assertEquals(id, fetchQuestion.get().getId());
    }

    @Test
    public void addQuestion() {
        Long id = 1L;

        Question question = new Question(
                id,
                1L,
                "title",
                "text",
                0,
                LocalDateTime.now());

        when(questionRepository.save(question)).thenReturn(question);
        boolean savedQuestion = questionService.addQuestion(question);

        assertTrue(savedQuestion);
    }

    @Test
    public void deleteQuestionById() {
        Long id = 1L;

        doNothing().when(questionRepository).deleteById(id);

        questionService.deleteQuestionById(id);

        verify(questionRepository, times(1)).deleteById(id);
    }

    @Test
    public void getAllQuestions() {
        List<Question> list = List.of(new Question(
                1L,
                1L,
                "title",
                "text",
                0,
                LocalDateTime.now()));

        when(questionRepository.findAll()).thenReturn(list);

        List<Question> fetchedList = questionService.getAllQuestions();

        assertEquals(fetchedList.size(), list.size());
    }

    @Test
    public void filterByUserId() {
        Long id = 1L;

        List<Question> list = List.of(new Question(
                id,
                1L,
                "title",
                "text",
                0,
                LocalDateTime.now()),
                new Question(
                id,
                1L,
                "title",
                "text",
                0,
                LocalDateTime.now()));

        when(questionRepository.findAllByUserId(id)).thenReturn(list);

        List<Question> fetchedList = questionRepository.findAllByUserId(id);

        for(Question question : fetchedList) {
            assertEquals(question.getUserId(), id);
        }
    }

    @Test
    public void filterBySubtitle() {
        String subtitle = "subtitle";

        List<Question> list = List.of(new Question(
                1L,
                1L,
                subtitle + "a",
                "text",
                0,
                LocalDateTime.now()),

                new Question(
                        1L,
                        1L,
                        subtitle + "b",
                        "text",
                        0,
                        LocalDateTime.now()));

        when(questionRepository.filterBySubtitle(subtitle)).thenReturn(list);

        List<Question> fetchedList = questionService.filterBySubtitle(subtitle);

        for(Question question : fetchedList) {
            assertTrue(question.getTitle().contains(subtitle));
        }
    }
}
