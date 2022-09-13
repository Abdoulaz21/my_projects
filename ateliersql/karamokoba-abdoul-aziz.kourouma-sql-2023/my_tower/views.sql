/* view_empty_apartment */
CREATE OR REPLACE VIEW view_empty_apartment AS
    SELECT id_floor AS floor, id_apartment AS number
    FROM floor_apartment
    WHERE id_apartment NOT IN (SELECT DISTINCT id_apartment FROM apartment_inhabitant)
    ORDER BY id_floor, id_apartment;

/* view_inhabitant_per_floor */
CREATE OR REPLACE VIEW view_inhabitant_per_floor AS
    SELECT floor.id AS floor, COUNT(apartment_inhabitant.id_inhabitant) AS inhabitants
    FROM (floor LEFT JOIN floor_apartment
    ON floor.id = floor_apartment.id_floor) LEFT JOIN apartment_inhabitant
    ON floor_apartment.id_apartment = apartment_inhabitant.id_apartment
    GROUP BY floor.id
    ORDER BY floor.id DESC;

/* view_senior_floor */
CREATE OR REPLACE VIEW view_senior_floor AS
    SELECT floor.id AS floor, CEIL(AVG(inhabitant.age)) AS "average age"
    FROM ((floor LEFT JOIN floor_apartment
    ON floor.id = floor_apartment.id_floor) LEFT JOIN apartment_inhabitant
    ON floor_apartment.id_apartment = apartment_inhabitant.id_apartment) LEFT JOIN inhabitant
    ON inhabitant.id = apartment_inhabitant.id_inhabitant
    GROUP BY floor.id
    HAVING AVG(inhabitant.age) > 50
    ORDER BY floor.id;

/* view_average_rent_per_floor */
CREATE OR REPLACE VIEW view_average_rent_per_floor AS
    SELECT fa.id_floor AS floor, SUM("adult inhabitants") AS "adult inhabitants", ROUND(SUM(rent) / SUM("adult inhabitants"), 2) AS "average rent"
    FROM floor_apartment fa INNER JOIN
    (SELECT part1.apart, "adult inhabitants", rent
    FROM
        (SELECT id_apartment AS apart,
        CAST(
            SUM(
                CASE WHEN age >= 18 THEN 1
                ELSE 0
                END
            ) AS DECIMAL
        ) AS "adult inhabitants"
        FROM apartment_inhabitant INNER JOIN inhabitant i
        ON id_inhabitant = i.id
        GROUP BY apart
        ORDER BY apart) AS part1
    INNER JOIN
        (SELECT id AS apart, rent
        FROM apartment
        GROUP BY apart
        ORDER BY apart) AS part2
    ON part1.apart = part2.apart
    GROUP BY part1.apart, "adult inhabitants", rent
    ORDER BY part1.apart) AS B
    ON fa.id_apartment = apart
    GROUP BY fa.id_floor
    ORDER BY fa.id_floor;

/* view_occupancy_rate */
CREATE OR REPLACE VIEW view_occupancy_rate AS
    SELECT DISTINCT floor_apartment.id_floor AS floor, apartment.id AS "apartment number", COUNT(apartment_inhabitant.id_inhabitant) AS occupants, apartment.nb_room * 2 AS capacity,
        CASE WHEN COUNT(apartment_inhabitant.id_inhabitant) = apartment.nb_room THEN 'MEDIUM'
            WHEN COUNT(apartment_inhabitant.id_inhabitant) = 1 THEN 'MINIMUM'
            WHEN COUNT(apartment_inhabitant.id_inhabitant) = apartment.nb_room * 2 THEN 'FULL'
            WHEN COUNT(apartment_inhabitant.id_inhabitant) <= apartment.nb_room AND COUNT(apartment_inhabitant.id_inhabitant) >= 1 THEN 'LOW'
            ELSE 'HEAVY'
        END AS occupancy
    FROM (apartment LEFT JOIN floor_apartment
    ON apartment.id = floor_apartment.id_apartment) LEFT JOIN apartment_inhabitant
    ON floor_apartment.id_apartment = apartment_inhabitant.id_apartment
    GROUP BY floor_apartment.id_floor, apartment.id
    HAVING COUNT(apartment_inhabitant.id_inhabitant) > 0
    ORDER BY floor_apartment.id_floor, apartment.id;
