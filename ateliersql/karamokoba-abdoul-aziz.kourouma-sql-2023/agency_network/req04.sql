SELECT COUNT(DISTINCT city) AS count, country
FROM destination
GROUP BY country
ORDER BY COUNT(DISTINCT city) DESC, country;
