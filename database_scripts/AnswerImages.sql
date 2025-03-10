CREATE TABLE AnswerImages(
    answerId INT NOT NULL,
    imageId INT NOT NULL,
    PRIMARY KEY (answerId, imageId),
    FOREIGN KEY (answerId) REFERENCES Answer(id) ON DELETE CASCADE,
    FOREIGN KEY (imageId) REFERENCES Images(id) ON DELETE CASCADE
)