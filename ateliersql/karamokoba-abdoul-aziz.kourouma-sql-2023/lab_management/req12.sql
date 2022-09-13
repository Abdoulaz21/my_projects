CREATE OR REPLACE FUNCTION buy_can(_assistant VARCHAR(64), _can VARCHAR(32))
RETURNS BOOLEAN AS
$$
BEGIN
    IF (SELECT credit FROM assistant WHERE login = _assistant) >= (SELECT price FROM can WHERE name = _can) THEN
        UPDATE can SET stock = stock - 1 WHERE name = _can;
        INSERT INTO transaction VALUES (DEFAULT, _assistant, _can, now());
        RETURN True;
    ELSE
        RETURN False;
    END IF;
END;
$$ LANGUAGE plpgsql;
