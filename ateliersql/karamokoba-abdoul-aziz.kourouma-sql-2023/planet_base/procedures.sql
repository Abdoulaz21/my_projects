/* space travel */
CREATE FUNCTION space_travel(origin INT, destination INT, quantity BIGINT)
RETURNS boolean AS
$$
BEGIN
    IF (SELECT id_system FROM planet WHERE id = origin) = (SELECT id_system FROM planet WHERE id = destination) AND quantity > 0 THEN
        UPDATE planet
        SET population = population - quantity
        WHERE id = origin;
        UPDATE planet
        SET population = population + quantity
        WHERE id = destination;
        RETURN True;
    ELSE
        RETURN False;
    END IF;
EXCEPTION WHEN OTHERS THEN RETURN False;
END;
$$ LANGUAGE plpgsql;

/* list_satellite_inf_750 */

CREATE FUNCTION list_satellite_inf_750(syst INT)
RETURNS TABLE(satellite VARCHAR(32), planet VARCHAR(32), radius INT) AS
$$
BEGIN
    RETURN QUERY
    SELECT satellite.name AS satellite, planet.name AS planet, satellite.radius AS radius
    FROM satellite LEFT JOIN planet
    ON satellite.id_planet = planet.id
    WHERE satellite.radius <= 750 AND planet.id_system = syst
    ORDER BY planet.name, satellite.radius DESC, satellite.name;
END;
$$ LANGUAGE plpgsql;
