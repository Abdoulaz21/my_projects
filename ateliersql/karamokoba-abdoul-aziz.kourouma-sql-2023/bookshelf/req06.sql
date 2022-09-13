SELECT title, author
FROM books
WHERE genre ILIKE '%fantasy%'
ORDER BY title, author;
