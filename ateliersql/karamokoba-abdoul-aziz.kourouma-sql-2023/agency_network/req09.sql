SELECT DISTINCT customer_surname
FROM booking
WHERE start_date < '2019-03-11' AND end_date > '2019-03-11'
ORDER BY customer_surname;
