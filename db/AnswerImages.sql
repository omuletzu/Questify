CREATE TABLE AnswerImages(
    answer_id INT NOT NULL,
    image_id INT NOT NULL,
    PRIMARY KEY (answer_id, image_id),
    FOREIGN KEY (answer_id) REFERENCES Answer(id) ON DELETE CASCADE,
    FOREIGN KEY (image_id) REFERENCES Images(id) ON DELETE CASCADE
)