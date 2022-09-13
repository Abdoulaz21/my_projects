CREATE TABLE mytab (
    id                  SERIAL      PRIMARY KEY,
    date                TIMESTAMP   NOT NULL,
    "old population"    BIGINT      NOT NULL,
    "new population"    BIGINT      NOT NULL
);

CREATE OR REPLACE VIEW view_earth_population_evolution AS
    SELECT id, to_char(date, 'DD/MM/YYYY HH24:MI:SS') AS date, "old population", "new population"
    FROM mytab;

CREATE OR REPLACE FUNCTION log_action()
    RETURNS trigger AS
$$
BEGIN
    INSERT INTO mytab
    VALUES (DEFAULT, now()::timestamp, OLD.population, NEW.population);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER store_earth_population_updates
AFTER UPDATE on planet
FOR EACH ROW
WHEN(OLD.population IS DISTINCT FROM NEW.population AND OLD.name = 'Earth')
EXECUTE PROCEDURE log_action();
