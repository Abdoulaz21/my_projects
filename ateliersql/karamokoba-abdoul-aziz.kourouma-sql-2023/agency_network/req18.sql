SELECT employee.surname AS surname, employee.name AS name, employee.email AS email
FROM employee LEFT JOIN agency
ON employee.agency_code = agency.code
WHERE agency.ratings > 6.0
ORDER BY employee.surname, employee.name;
