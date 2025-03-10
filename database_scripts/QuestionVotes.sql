CREATE TABLE QuestionVotes(
    questionId INT NOT NULL,
    userId INT NOT NULL,
    upOrDown BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (questionId, userId),
    FOREIGN KEY (questionId) REFERENCES Question(id) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES Users(id) ON DELETE CASCADE
)