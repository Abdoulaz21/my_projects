SELECT extract(month from issue_date) AS month, extract(year from issue_date) AS year, COUNT(id) AS number_of_tickets
FROM ticket
GROUP BY extract(month from issue_date), extract(year from issue_date)
ORDER BY COUNT(id) DESC, extract(year from issue_date), extract(month from issue_date);
