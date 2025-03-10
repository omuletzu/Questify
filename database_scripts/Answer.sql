CREATE TABLE Answer(
    id SERIAL PRIMARY KEY ,
    userId INT NOT NULL DEFAULT 0,
    questionId INT NOT NULL DEFAULT 0,
    text VARCHAR(1000) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    upVotes INT DEFAULT 0,
    downVotes INT DEFAULT 0,
    FOREIGN KEY (userId) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (questionId) REFERENCES Question(id) ON DELETE CASCADE
);