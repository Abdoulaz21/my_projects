CREATE TABLE faction (
    id SERIAL NOT NULL,
    color INT NOT NULL,
    name VARCHAR(64) NOT NULL,

    PRIMARY KEY (id)
);

INSERT INTO faction
VALUES (DEFAULT, 65280, 'Enlightened');

ALTER TABLE creature
ADD faction_id INT REFERENCES faction(id);
