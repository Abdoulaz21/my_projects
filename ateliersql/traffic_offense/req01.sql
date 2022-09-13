SELECT car.owner_name AS owner, SUM(ticket_type.points) AS points_deducted
FROM (ticket LEFT JOIN car
ON ticket.car_id = car.id) LEFT JOIN ticket_type
ON ticket.type_id = ticket_type.id
GROUP BY car.owner_name
HAVING SUM(ticket_type.points) > 100
ORDER BY SUM(ticket_type.points), car.owner_name;
