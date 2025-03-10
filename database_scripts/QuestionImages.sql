CREATE TABLE QuestionImages(
    questionId INT NOT NULL,
    imageId INT NOT NULL,
    PRIMARY KEY (questionId, imageId),
    FOREIGN KEY (questionId) REFERENCES Question(id) ON DELETE CASCADE,
    FOREIGN KEY (imageId) REFERENCES Images(id) ON DELETE CASCADE
)