SELECT name, death_date - birth_date AS days
FROM authors
WHERE birth_date <= '2000-01-01' AND death_date IS NOT NULL
UNION
SELECT name, '2000-01-01' - birth_date
FROM authors
WHERE birth_date <= '2000-01-01' AND death_date IS NULL
ORDER BY name;
