SELECT title, author
FROM books
WHERE genre = 'Poem' AND author IN (SELECT name FROM authors WHERE country = 'France')
ORDER BY title, author;
