    package com.example.questify.models.JpaRepository;

    import com.example.questify.models.BetweenModels.QuestionVotes;
    import com.example.questify.models.CompositePK.QuestionVotesPK;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;
    import java.util.Optional;

    public interface QuestionVotesRepository extends JpaRepository<QuestionVotes, QuestionVotesPK> {
        public Optional<QuestionVotes> findByQuestionIdAndUserId(Long questionId, Long userId);
        public long countByUpOrDownTrueAndUserId(Long userId);
        public long countByUpOrDownFalseAndUserId(Long userId);

        public long countByUpOrDownTrueAndQuestionId(Long questionId);

        public long countByUpOrDownFalseAndQuestionId(Long questionId);
    }
