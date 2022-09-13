SELECT books.title, authors.name
FROM authors LEFT JOIN books
ON authors.name = books.author
WHERE NOT (authors.death_date > books.release OR death_date IS NULL)
ORDER BY books.title, authors.name;
