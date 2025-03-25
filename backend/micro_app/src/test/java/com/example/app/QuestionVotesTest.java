package com.example.app;

import com.example.app.jpaRepository.QuestionVotesRepository;
import com.example.app.models.BetweenModels.QuestionVotes;
import com.example.app.services.QuestionVotesService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class QuestionVotesTest {
    @InjectMocks
    private QuestionVotesService questionVotesService;

    @Mock
    private QuestionVotesRepository questionVotesRepository;

    @Test
    public void findQuestionVote() {
        Long userId = 1L;
        Long questionId = 1L;

        QuestionVotes questionVotes = new QuestionVotes(
                userId,
                questionId,
                true
        );

        when(questionVotesRepository.findByQuestionIdAndUserId(questionId, userId)).thenReturn(Optional.of(questionVotes));

        Optional<QuestionVotes> fetchedQuestionVote = questionVotesService.findQuestionVote(questionId, userId);

        assertTrue(fetchedQuestionVote.isPresent());
    }

    @Test
    public void addQuestionVote() {
        QuestionVotes questionVote = new QuestionVotes(
                1L,
                1L,
                true
        );

        when(questionVotesRepository.save(questionVote)).thenReturn(questionVote);

        boolean addedQuestionVote = questionVotesService.addQuestionVote(questionVote);

        assertTrue(addedQuestionVote);
    }

    @Test
    public void deleteQuestionVote() {
        Long questionId = 1L;
        Long userId = 1L;

        QuestionVotes questionVotes = new QuestionVotes(
                userId, questionId, true
        );

        when(questionVotesRepository.findByQuestionIdAndUserId(questionId, userId)).thenReturn(Optional.of(questionVotes));
        Optional<QuestionVotes> fetchedQuestionVotes = questionVotesService.findQuestionVote(questionId, userId);

        assertTrue(fetchedQuestionVotes.isPresent());

        doNothing().when(questionVotesRepository).delete(fetchedQuestionVotes.get());

        questionVotesService.deleteQuestionVote(fetchedQuestionVotes.get());

        verify(questionVotesRepository, times(1)).delete(fetchedQuestionVotes.get());
    }

    @Test
    public void countByUpDownTrueByUserId() {
        Long id = 1L;

        Long count = 10L;
        when(questionVotesRepository.countByUpDownTrueAndUserId(id)).thenReturn(count);

        Long fetchedCount = questionVotesService.countByUpDownTrueByUserId(id);

        assertEquals(count, fetchedCount);
    }

    @Test
    public void countByUpDownFalseByUserId() {
        Long id = 1L;

        Long count = 10L;
        when(questionVotesRepository.countByUpDownFalseAndUserId(id)).thenReturn(count);

        Long fetchedCount = questionVotesService.countByUpDownFalseByUserId(id);

        assertEquals(count, fetchedCount);
    }

    @Test
    public void countByUpDownTrueByQuestionId() {
        Long id = 1L;

        Long count = 10L;
        when(questionVotesRepository.countByUpDownTrueAndQuestionId(id)).thenReturn(count);

        Long fetchedCount = questionVotesService.countByUpDownTrueByQuestionId(id);

        assertEquals(count, fetchedCount);
    }

    @Test
    public void countByUpDownFalseByQuestionId() {
        Long id = 1L;

        Long count = 10L;
        when(questionVotesRepository.countByUpDownFalseAndQuestionId(id)).thenReturn(count);

        Long fetchedCount = questionVotesService.countByUpDownFalseByQuestionId(id);

        assertEquals(count, fetchedCount);
    }
}
