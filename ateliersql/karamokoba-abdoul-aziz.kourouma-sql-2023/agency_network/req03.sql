SELECT name, address, rooms
FROM hotel
GROUP BY name, address, rooms
ORDER BY name, rooms DESC;
