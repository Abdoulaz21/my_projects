INSERT INTO can
VALUES ('Dr Pepper', 100, 330, 0.70);

INSERT INTO transaction
VALUES (DEFAULT, 'claire.billy', 'Dr Pepper', '2019-02-22 17:43:00');

UPDATE can
SET stock = 99
WHERE name = 'Dr Pepper';
