CREATE TABLE item (
    id SERIAL NOT NULL,
    name VARCHAR(64) NOT NULL,
    rarity INT NOT NULL,
    icon_id INT NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE inventory (
    id SERIAL NOT NULL,
    item_id INT NOT NULL,
    character_id INT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (item_id) REFERENCES item(id),
    FOREIGN KEY (character_id) REFERENCES character(id)
);

CREATE TABLE loots (
    id SERIAL NOT NULL,
    item_id INT NOT NULL,
    creature_gid INT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (item_id) REFERENCES item(id),
    FOREIGN KEY (creature_gid) REFERENCES creature_template(id)
);
