CREATE TABLE Question(
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    text VARCHAR(255) NOT NULL,
    status INT NOT NULL DEFAULT 0,
    timestamp TIMESTAMP NOT NULL
)