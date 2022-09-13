SELECT transaction.assistant, SUM(can.price) AS price
FROM transaction LEFT JOIN can
ON transaction.can = can.name
GROUP BY transaction.assistant
ORDER BY SUM(can.price) DESC
LIMIT 3;
