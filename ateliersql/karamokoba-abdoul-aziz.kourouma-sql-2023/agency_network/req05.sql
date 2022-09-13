SELECT REGEXP_REPLACE(country, '[^A-Za-z]', '', 'g') AS country, REGEXP_REPLACE(city, '[^A-Za-z]', '', 'g') AS city
FROM destination
ORDER BY country, city;
