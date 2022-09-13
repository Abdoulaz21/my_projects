DROP TABLE IF EXISTS shop_can CASCADE;
DROP TABLE IF EXISTS student_can_shop CASCADE;
DROP TABLE IF EXISTS shop CASCADE;
DROP TABLE IF EXISTS can CASCADE;
DROP TABLE IF EXISTS student CASCADE;
DROP TABLE IF EXISTS address CASCADE;

CREATE TABLE can (
    id          SERIAL,
    name        VARCHAR(64)  NOT NULL,
    capacity_cl INT          NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE address (
    id          SERIAL,
    number      INTEGER       NOT NULL,
    street      VARCHAR(64)   NOT NULL,
    city        VARCHAR(64)   NOT NULL,
    zip_code    VARCHAR(5)    NOT NULL,
    country     VARCHAR(64)   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE student (
    login       VARCHAR(64),
    firstname   VARCHAR(64),
    lastname    VARCHAR(64),
    assistant   BOOLEAN      NOT NULL,
    address_id  INTEGER,
    PRIMARY KEY (login),
    FOREIGN KEY (address_id) REFERENCES address(id)
);

CREATE TABLE shop (
    id          SERIAL,
    name        VARCHAR(64) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE shop_can (
    shop_id         SERIAL,
    can_id          SERIAL,
    price           REAL    NOT NULL,
    PRIMARY KEY (shop_id, can_id),
    FOREIGN KEY (shop_id) REFERENCES shop(id),
    FOREIGN KEY (can_id) REFERENCES can(id)
);


CREATE TABLE student_can_shop (
    id              SERIAL,
    login           VARCHAR(64),
    can_id          SERIAL,
    shop_id         SERIAL,
    purchase_time   TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (login) REFERENCES student(login),
    FOREIGN KEY (can_id) REFERENCES can(id),
    FOREIGN KEY (shop_id) REFERENCES shop(id)
);


------------------------------------------------------------------------------
------------------------------- INSERTIONS -----------------------------------
------------------------------------------------------------------------------

-- Cans
INSERT INTO can VALUES
    (DEFAULT, 'Coke', 25),          -- 1
    (DEFAULT, 'Oasis', 25),         -- 2
    (DEFAULT, 'Diet Coke', 25),     -- 3
    (DEFAULT, 'Orangina', 25),      -- 4
    (DEFAULT, 'Sprite', 25),        -- 5
    (DEFAULT, 'Pepsi', 25),         -- 6
    (DEFAULT, 'Ice Tea', 25),       -- 7
    (DEFAULT, 'Water', 50),         -- 8
    (DEFAULT, 'Fanta', 25),         -- 9
    (DEFAULT, 'Orange Juice', 33);  -- 10


-- Addresses
INSERT INTO address VALUES
    (DEFAULT, 12, 'rue de la Paix', 'Paris', '75002', 'FRANCE'),        -- 1
    (DEFAULT, 1, 'rue de la Paix', 'Paris', '75002', 'FRANCE'),         -- 2
    (DEFAULT, 115, 'Bld de Belleville', 'paris', '75011', 'France'),    -- 3
    (DEFAULT, 103, 'Bld de Belleville', 'paris', '75011', 'France'),    -- 4
    (DEFAULT, 3, 'Bld de la Villette', 'Paris', '75011', 'France'),     -- 5
    (DEFAULT, 4, 'Bld de la Villette', 'Paris', '75011', 'France'),     -- 6
    (DEFAULT, 5, 'Avenue de Neuilly', 'Paris', '75017', 'france'),      -- 7
    (DEFAULT, 18, 'Place Pigalle', 'PARIS', '75009', 'france'),         -- 8
    (DEFAULT, 1, 'Alexanderstrasse', 'Berlin', '10178', 'Deutschland'), -- 9
    (DEFAULT, 3, 'Eagle Road', 'Greenhithe', 'DA99Q', 'United Kingdom'),--10
    (DEFAULT, 15, 'Place de la Bourse', 'PARIS', '75002', 'france'),    --11
    (DEFAULT, 5, 'Avenue Matignon', 'PARIS', '75008', 'France'),        --12
    (DEFAULT, 48, 'Avenue Foch', 'Paris', '75116', 'France'),           --13
    (DEFAULT, 50, 'Boulevard Malesherbes', 'Paris', '75008', 'France'), --14
    (DEFAULT, 3, 'Boulevard Malesherbes', 'Paris', '75008', 'France'),  --15
    (DEFAULT, 14, 'Rue Voltaire', 'Kremlin-Bicêtre', '94270', 'France');--16

-- Students
INSERT INTO student VALUES
    ('cyrill_a', 'Aimé', 'Cyrille', FALSE, 1),
    ('alphon_a', 'André', 'Alphonsine', FALSE, 2),
    ('muriel_l', 'Léonie', 'Muriel', FALSE, 3),
    ('leo_m', 'Marjolaine', 'Léo', FALSE, 4),
    ('batrie_p', 'Paul', 'Batrien', FALSE, 5),
    ('arnaud_p', 'Philomène', 'Arnaud', FALSE, 6),
    ('cosee_t', 'Tiphaine', 'Cosée', FALSE, 7),
    ('paulin_a', 'Arnaud', 'Pauline', FALSE, 8),
    ('joel_c', 'Corentin', 'Joel', FALSE, 9),
    ('theoph_a', 'Alexandre', 'Théophile', FALSE, 10),
    ('prospe_j', 'Joseph', 'Prosper', FALSE, 11),
    ('emeric_a', 'Aurélie', 'Emericé', FALSE, 12),
    ('roch_c', 'Cédric', 'Roch', FALSE, 13),
    ('toros_n', 'Noé', 'Toros', FALSE, 14),
    ('bedros_g', 'Guy', 'Bedros', FALSE, 5),
    ('firmin_v', 'Valentin', 'Firmin', FALSE, 6),
    ('rouben_u', 'Ulysse', 'Rouben', FALSE, 7),
    ('nazare_p', 'PAUL', 'Nazare', FALSE, 8),
    ('gevorg_e', 'Eugène', 'Gevorg', FALSE, 9),
    ('marcel_i', 'Isidore', 'Marcellin', FALSE, 10),
    ('roch_h', 'Hadrien', 'Roch', FALSE, 11),
    ('guyree_b', 'Brice', 'Guyrée', FALSE, 12),
    ('daniel_n', 'Noémi', 'Danièle', FALSE, 13),
    ('aghavn_a', 'Anne', 'Aghavni', FALSE, 14),
    ('domini_s', 'Solène', 'Dominique', FALSE, 5),
    ('geinau_h', 'Hélène', 'Geinaux', FALSE, 4),
    ('paul.khuat-duy', 'Paul', 'Khuat-Duy', TRUE, 16),
    ('theophane.vie', 'Théophane', 'Vié', TRUE, 16),
    ('claire.billy', 'Claire', 'Billy', TRUE, 16),
    ('el_hal_a', 'Anas', 'El Halouani', TRUE, 16),
    ('login_x', NULL, NULL, FALSE, NULL);

-- Shops
INSERT INTO shop VALUES
    (DEFAULT, 'Lidl'),      -- 1
    (DEFAULT, 'Crocus'),    -- 2
    (DEFAULT, 'Okabe');     -- 3

INSERT INTO shop_can VALUES
    (1, 1, 1.10),
    (1, 2, 1.10),
    (1, 4, 1.10),
    (1, 5, 1.10),
    (1, 7, 1.10),
    (1, 8, 1.10),

    (2, 1, 1.20),
    (2, 2, 1.20),
    (2, 3, 1.20),
    (2, 4, 1.20),
    (2, 5, 1.20),
    (2, 6, 1.20),
    (2, 7, 1.20),
    (2, 8, 1.20),
    (2, 9, 1.20),
    (2, 10, 1.20),

    (3, 1, 1.00),
    (3, 2, 1.00),
    (3, 3, 1.00),
    (3, 4, 1.00),
    (3, 5, 1.00),
    (3, 6, 1.00),
    (3, 7, 1.00),
    (3, 8, 1.00),
    (3, 9, 1.00),
    (3, 10, 1.00);

INSERT INTO student_can_shop VALUES
    (DEFAULT, 'theoph_a', 2, 1, TIMESTAMP '2016-11-15 10:04:00'),
    (DEFAULT, 'theoph_a', 2, 1, TIMESTAMP '2016-10-16 11:18:00'),
    (DEFAULT, 'theoph_a', 2, 1, TIMESTAMP '2017-10-16 20:07:00'),
    (DEFAULT, 'theoph_a', 2, 1, TIMESTAMP '2017-10-17 11:30:00'),
    (DEFAULT, 'theoph_a', 2, 1, TIMESTAMP '2017-10-20 14:00:00'),
    (DEFAULT, 'theoph_a', 2, 1, TIMESTAMP '2017-10-20 15:03:00'),
    (DEFAULT, 'theoph_a', 1, 1, TIMESTAMP '2017-10-21 23:02:00'),
    (DEFAULT, 'theoph_a', 1, 1, TIMESTAMP '2018-01-22 11:46:00'),
    (DEFAULT, 'theoph_a', 1, 1, TIMESTAMP '2018-01-26 13:53:00'),
    (DEFAULT, 'theoph_a', 7, 1, TIMESTAMP '2017-01-28 12:04:00'),
    (DEFAULT, 'theoph_a', 7, 2, TIMESTAMP '2017-01-29 16:15:00'),
    (DEFAULT, 'theoph_a', 7, 3, TIMESTAMP '2017-02-01 17:30:00'),
    (DEFAULT, 'theoph_a', 8, 1, TIMESTAMP '2018-02-02 17:40:00'),
    (DEFAULT, 'theoph_a', 8, 1, TIMESTAMP '2018-02-03 18:32:00'),

    (DEFAULT, 'cyrill_a', 7, 1, TIMESTAMP '2018-01-28 17:03:00'),
    (DEFAULT, 'cyrill_a', 7, 1, TIMESTAMP '2017-12-01 17:30:00'),
    (DEFAULT, 'cyrill_a', 7, 1, TIMESTAMP '2017-12-05 18:15:00'),
    (DEFAULT, 'cyrill_a', 7, 1, TIMESTAMP '2018-02-08 12:45:00'),

    (DEFAULT, 'firmin_v', 9, 1, TIMESTAMP '2018-01-10 17:03:00'),
    (DEFAULT, 'firmin_v', 9, 1, TIMESTAMP '2018-01-11 18:14:00'),
    (DEFAULT, 'firmin_v', 9, 1, TIMESTAMP '2017-11-12 19:39:00'),
    (DEFAULT, 'firmin_v', 9, 1, TIMESTAMP '2017-11-15 10:20:00'),
    (DEFAULT, 'firmin_v', 9, 1, TIMESTAMP '2017-11-23 11:19:00'),
    (DEFAULT, 'firmin_v', 9, 1, TIMESTAMP '2017-11-25 12:29:00'),
    (DEFAULT, 'firmin_v', 7, 1, TIMESTAMP '2017-02-01 17:30:00'),
    (DEFAULT, 'firmin_v', 7, 1, TIMESTAMP '2018-02-08 23:46:00'),

    (DEFAULT, 'alphon_a', 1, 1, TIMESTAMP '2017-10-28 09:20:00'),
    (DEFAULT, 'alphon_a', 1, 1, TIMESTAMP '2017-10-29 14:56:00'),
    (DEFAULT, 'alphon_a', 1, 3, TIMESTAMP '2018-01-30 21:48:00'),

    (DEFAULT, 'daniel_n', 7, 1, TIMESTAMP '2018-02-02 16:46:00'),
    (DEFAULT, 'daniel_n', 7, 1, TIMESTAMP '2018-02-01 13:24:00'),

    (DEFAULT, 'arnaud_p', 10, 2, TIMESTAMP '2018-01-21 16:23:00'),
    (DEFAULT, 'arnaud_p', 10, 2, TIMESTAMP '2018-01-23 17:42:00'),
    (DEFAULT, 'arnaud_p', 10, 2, TIMESTAMP '2018-02-02 00:37:00'),
    (DEFAULT, 'arnaud_p', 10, 2, TIMESTAMP '2018-02-03 19:11:00'),
    (DEFAULT, 'arnaud_p', 10, 2, TIMESTAMP '2018-02-05 04:13:00'),
    (DEFAULT, 'arnaud_p', 10, 2, TIMESTAMP '2018-02-07 17:09:00'),
    (DEFAULT, 'arnaud_p', 10, 2, TIMESTAMP '2018-02-08 19:24:00'),

    (DEFAULT, 'nazare_p', 8, 1, TIMESTAMP '2017-01-25 12:13:00'),
    (DEFAULT, 'nazare_p', 8, 1, TIMESTAMP '2018-02-13 12:24:00'),
    (DEFAULT, 'nazare_p', 3, 1, TIMESTAMP '2018-03-01 12:24:00');
