SELECT authors.name,
    CASE WHEN COUNT(books.title) = 1 THEN concat(COUNT(books.title), ' book')
        WHEN COUNT(books.title) > 1 THEN concat(COUNT(books.title), ' books')
    END AS stocks
FROM authors LEFT JOIN books
ON authors.name = books.author
GROUP BY authors.name
ORDER BY authors.name;
