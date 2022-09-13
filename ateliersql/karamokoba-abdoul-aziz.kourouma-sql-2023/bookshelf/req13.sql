SELECT title, author
FROM books
WHERE release IN (SELECT min(release) FROM books GROUP BY author)
ORDER BY title, author;
