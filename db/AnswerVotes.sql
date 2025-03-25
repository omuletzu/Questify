CREATE TABLE AnswerVotes(
    answer_id INT NOT NULL,
    user_id INT NOT NULL,
    up_down BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (answer_id, user_id),
    FOREIGN KEY (answer_id) REFERENCES Answer(id) ON DELETE CASCADE
)