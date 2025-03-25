package com.example.app;

import com.example.app.jpaRepository.AnswerVoteRepository;
import com.example.app.models.BetweenModels.AnswerVotes;
import com.example.app.models.CompositePK.AnswerVotesPK;
import com.example.app.services.AnswerVotesService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AnswerVotesTest {
    @InjectMocks
    private AnswerVotesService answerVotesService;

    @Mock
    private AnswerVoteRepository answerVoteRepository;

    @Test
    public void findAnswerVote() {
        Long answerId = 1L;
        Long userId = 1L;

        AnswerVotes answerVotes = new AnswerVotes(
                userId, answerId, true
        );

        when(answerVoteRepository.findByAnswerIdAndUserId(answerId, userId)).thenReturn(Optional.of(answerVotes));

        Optional<AnswerVotes> fetchedAnswerVotes = answerVotesService.findAnswerVote(answerId, userId);

        assertTrue(fetchedAnswerVotes.isPresent());
    }

    @Test
    public void addAnswerVote() {
        Long answerId = 1L;
        Long userId = 1L;

        AnswerVotes answerVotes = new AnswerVotes(
                userId, answerId, true
        );

        when(answerVoteRepository.save(answerVotes)).thenReturn(answerVotes);

        boolean addedAnswerVotes = answerVotesService.addAnswerVote(answerVotes);

        assertTrue(addedAnswerVotes);
    }

    @Test
    public void deleteAnswerVote() {
        Long answerId = 1L;
        Long userId = 1L;

        AnswerVotes answerVotes = new AnswerVotes(
                userId, answerId, true
        );

        when(answerVoteRepository.findByAnswerIdAndUserId(answerId, userId)).thenReturn(Optional.of(answerVotes));
        Optional<AnswerVotes> fetchedAnswerVotes = answerVotesService.findAnswerVote(answerId, userId);

        assertTrue(fetchedAnswerVotes.isPresent());

        doNothing().when(answerVoteRepository).delete(fetchedAnswerVotes.get());

        answerVotesService.deleteAnswerVote(fetchedAnswerVotes.get());

        verify(answerVoteRepository, times(1)).delete(fetchedAnswerVotes.get());
    }

    @Test
    public void countByUpDownTrueByUserId() {
        Long id = 1L;

        Long count = 10L;
        when(answerVoteRepository.countByUpDownTrueAndUserId(id)).thenReturn(count);

        Long fetchedCount = answerVotesService.countByUpDownTrueByUserId(id);

        assertEquals(count, fetchedCount);
    }

    @Test
    public void countByUpDownFalseByUserId() {
        Long id = 1L;

        Long count = 10L;
        when(answerVoteRepository.countByUpDownFalseAndUserId(id)).thenReturn(count);

        Long fetchedCount = answerVotesService.countByUpDownFalseByUserId(id);

        assertEquals(count, fetchedCount);
    }

    @Test
    public void countByUpDownTrueByAnswerId() {
        Long id = 1L;

        Long count = 10L;
        when(answerVoteRepository.countByUpDownTrueAndAnswerId(id)).thenReturn(count);

        Long fetchedCount = answerVotesService.countByUpDownTrueByAnswerId(id);

        assertEquals(count, fetchedCount);
    }

    @Test
    public void countByUpDownFalseByAnswerId() {
        Long id = 1L;

        Long count = 10L;
        when(answerVoteRepository.countByUpDownFalseAndAnswerId(id)).thenReturn(count);

        Long fetchedCount = answerVotesService.countByUpDownFalseByAnswerId(id);

        assertEquals(count, fetchedCount);
    }
}
