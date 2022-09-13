/* add_floor */
CREATE OR REPLACE FUNCTION add_floor(capacity INT)
RETURNS BOOLEAN AS
$$
BEGIN
    IF capacity >= 1 THEN
        IF (SELECT COUNT(id) FROM floor) = 0 THEN
            INSERT INTO floor VALUES (0, capacity);
        ELSE
            INSERT INTO floor VALUES ((SELECT max(id) + 1 from floor), capacity);
        END IF;
        RETURN True;
    ELSE
        RETURN False;
    END IF;
END;
$$ LANGUAGE plpgsql;

/* renovate floor */
CREATE OR REPLACE FUNCTION renovate_floor(floor INT, new_capacity INT)
RETURNS BOOLEAN AS
$$
BEGIN
    IF (SELECT COUNT(*) FROM floor_apartment WHERE id_floor = $1) > 0 THEN
        RETURN FALSE;
    END IF;
    IF $1 IN (SELECT id FROM floor WHERE id NOT IN (SELECT id_floor FROM floor_apartment)) THEN
        UPDATE floor SET room_capacity = new_capacity WHERE id = $1;
        RETURN True;
    ELSE
        RETURN False;
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        RETURN FALSE;
END;
$$ LANGUAGE plpgsql;

/* add_apartment */
CREATE OR REPLACE FUNCTION add_apartment(number INT, rooms INT, rent INT, floor INT)
RETURNS BOOLEAN AS
$$
DECLARE
    max_nb INT;
    init_nb_room INT;
BEGIN
    SELECT SUM(nb_room) INTO init_nb_room FROM apartment JOIN floor_apartment ON id_apartment = apartment.id WHERE id_floor = $4;
    IF init_nb_room IS NULL THEN init_nb_room = 0; END IF;
    SELECT room_capacity INTO max_nb FROM floor WHERE id = $4;
    IF max_nb < init_nb_room + rooms THEN RETURN FALSE; END IF;
    INSERT INTO apartment VALUES
    (number, rooms, rent);
    INSERT INTO floor_apartment
    VALUES (DEFAULT, $4, number);
    RETURN TRUE;
EXCEPTION
    WHEN OTHERS THEN
        RETURN FALSE;
END;
$$ LANGUAGE plpgsql;

/* add_inhabitant */
CREATE OR REPLACE FUNCTION add_inhabitant(id INT, firstname VARCHAR(64), lastname VARCHAR(64), age INT)
RETURNS BOOLEAN AS
$$
BEGIN
    INSERT INTO inhabitant VALUES ($1, $2, $3, $4);
    RETURN True;
EXCEPTION WHEN OTHERS THEN
    RETURN False;
END;
$$ LANGUAGE plpgsql;

/* add_inhabitant_to_apartment */
CREATE OR REPLACE FUNCTION add_inhabitant_to_apartment(id_inhabitant INT, id_apartment INT)
RETURNS BOOLEAN AS
$$
DECLARE
    nbr_inhabitant INT;
    max_capacity INT;
    age_inhabitant INT;
BEGIN
    SELECT nb_room * 2 INTO max_capacity FROM apartment a WHERE a.id = $2;
    SELECT COUNT(*) INTO nbr_inhabitant FROM apartment_inhabitant ai WHERE ai.id_apartment = $2;
    SELECT age INTO age_inhabitant FROM inhabitant i WHERE i.id = $1;
    IF max_capacity = nbr_inhabitant OR (nbr_inhabitant = 0 AND age_inhabitant < 18) THEN
        RETURN FALSE;
    END IF;
    INSERT INTO apartment_inhabitant
    VALUES (DEFAULT, id_inhabitant, id_apartment);
    RETURN TRUE;
EXCEPTION WHEN OTHERS THEN
    RETURN FALSE;
END;
$$ LANGUAGE plpgsql;
