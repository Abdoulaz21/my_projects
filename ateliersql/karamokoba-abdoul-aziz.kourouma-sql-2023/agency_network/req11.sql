SELECT id,
    CASE    WHEN start_date > now() THEN 'Booked'
            WHEN end_date < now() THEN 'Done'
            WHEN end_date > now() AND start_date < now() THEN 'Ongoing'
    END AS "trip status"
FROM booking
ORDER BY "trip status", id;
