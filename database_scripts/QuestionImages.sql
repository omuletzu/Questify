CREATE TABLE QuestionImages(
    questionId INT NOT NULL,
    imageId INT NOT NULL,
    FOREIGN KEY (questionId) REFERENCES Question(id) ON DELETE CASCADE,
    FOREIGN KEY (imageId) REFERENCES Images(id) ON DELETE CASCADE
)