UPDATE character
SET level = 15
WHERE name = 'Tilon';

UPDATE character
SET level = level + 1
WHERE name = 'Kuro';

UPDATE character
SET max_health =
    CASE
        WHEN blessed = 1 THEN (level + 1) * 10
        WHEN blessed = 0 THEN level * 10
    END;
