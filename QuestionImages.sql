CREATE TABLE QuestionImages(
    question_id INT NOT NULL,
    image_id INT NOT NULL,
    PRIMARY KEY (question_id, image_id),
    FOREIGN KEY (question_id) REFERENCES Question(id) ON DELETE CASCADE,
    FOREIGN KEY (image_id) REFERENCES Images(id) ON DELETE CASCADE
)