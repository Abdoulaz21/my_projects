/* view_nearest_planet_to_sun */
CREATE OR REPLACE VIEW view_nearest_planet_to_sun AS
    SELECT name AS planet
    FROM planet
    WHERE id_system = (SELECT id FROM planetary_system WHERE star ILIKE 'sun')
    ORDER BY period
    LIMIT 3;

/* view_nb_satellite_per_planet */
CREATE OR REPLACE VIEW view_nb_satellite_per_planet AS
    SELECT planet.name AS planet, COUNT(satellite.id) AS "number of satellites"
    FROM planet LEFT JOIN satellite ON id_planet = planet.id AND satellite.radius > 500
    GROUP BY planet
    ORDER BY "number of satellites", planet;

/* view_average_period */
CREATE OR REPLACE VIEW view_average_period AS
    SELECT planetary_system.name AS system,
        round(AVG(CASE WHEN planet.period IS NULL THEN 0 ELSE planet.period END), 2) AS average_period
    FROM planet right join planetary_system ON id_system = planetary_system.id
    GROUP BY system
    ORDER BY average_period, system;
    
/* view_biggest_entities */
CREATE OR REPLACE VIEW view_biggest_entities AS
    SELECT 'planet' AS type, planetary_system.name AS system, planet.name AS name, planet.radius AS radius
    FROM planet LEFT JOIN planetary_system
    ON planetary_system.id = planet.id_system
    UNION
    SELECT 'satellite', planetary_system.name, satellite.name, satellite.radius
    FROM (satellite LEFT JOIN planet
    ON satellite.id_planet = planet.id) LEFT JOIN planetary_system
    ON planetary_system.id = planet.id_system
    ORDER BY radius DESC, name
    LIMIT 10;
