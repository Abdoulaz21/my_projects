SELECT customer.surname AS surname, customer.name AS name, concat(destination.country, ', ', destination.city) AS destination, hotel.name AS hotel
FROM (customer LEFT JOIN destination
ON customer.top_destination = destination.acronym) LEFT JOIN hotel
ON destination.hotel_id = hotel.id
ORDER BY customer.surname, customer.name, destination.country, destination.city, hotel.name;
