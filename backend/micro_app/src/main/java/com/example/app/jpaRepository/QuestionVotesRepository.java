    package com.example.app.jpaRepository;

    import com.example.app.models.BetweenModels.QuestionVotes;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;

    import java.util.Optional;

    public interface QuestionVotesRepository extends JpaRepository<QuestionVotes, QuestionVotes> {
        public Optional<QuestionVotes> findByQuestionIdAndUserId(Long questionId, Long userId);
        public long countByUpDownTrueAndUserId(Long userId);
        public long countByUpDownFalseAndUserId(Long userId);

        public long countByUpDownTrueAndQuestionId(Long questionId);

        public long countByUpDownFalseAndQuestionId(Long questionId);

        @Query("UPDATE QuestionVotes q SET q.upDown = CASE WHEN q.upDown = true THEN false ELSE true END WHERE q.questionId = :id")
        public void flipById(@Param("id") Long id);
    }
