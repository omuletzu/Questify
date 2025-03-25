CREATE TABLE QuestionVotes(
    question_id INT NOT NULL,
    user_id INT NOT NULL,
    up_down BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (question_id, user_id),
    FOREIGN KEY (question_id) REFERENCES Question(id) ON DELETE CASCADE
)