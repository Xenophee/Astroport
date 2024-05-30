/* Setting up PROD DB */
CREATE DATABASE IF NOT EXISTS astroport;
USE astroport;

/* Creating the docks table */
CREATE TABLE docks
(
    DOCK_NUMBER INT PRIMARY KEY,
    AVAILABLE BOOL NOT NULL,
    TYPE VARCHAR(10) NOT NULL
);

/* Creating the tickets table */
CREATE TABLE tickets
(
    ID INT PRIMARY KEY AUTO_INCREMENT,
    DOCK_NUMBER INT NOT NULL,
    SHIP_REG_NUMBER VARCHAR(10) NOT NULL,
    PRICE DOUBLE,
    IN_TIME DATETIME NOT NULL,
    OUT_TIME DATETIME,
    FOREIGN KEY (DOCK_NUMBER) REFERENCES docks (DOCK_NUMBER)
);

/* Inserting data into the docks table */
INSERT INTO docks(DOCK_NUMBER, AVAILABLE, TYPE)
VALUES (1, TRUE, 'CORVETTE'),
       (2, TRUE, 'CORVETTE'),
       (3, TRUE, 'CORVETTE'),
       (4, TRUE, 'CORVETTE'),
       (5, TRUE, 'CORVETTE'),
       (6, TRUE, 'CORVETTE'),
       (7, TRUE, 'CORVETTE'),
       (8, TRUE, 'CORVETTE'),
       (9, TRUE, 'CORVETTE'),
       (10, TRUE, 'DESTROYER'),
       (11, TRUE, 'DESTROYER'),
       (12, TRUE, 'DESTROYER'),
       (13, TRUE, 'DESTROYER'),
       (14, TRUE, 'DESTROYER'),
       (15, TRUE, 'DESTROYER'),
       (16, TRUE, 'DESTROYER'),
       (17, TRUE, 'CRUISER'),
       (18, TRUE, 'CRUISER'),
       (19, TRUE, 'CRUISER'),
       (20, TRUE, 'CRUISER'),
       (21, TRUE, 'CRUISER'),
       (22, TRUE, 'CRUISER'),
       (23, TRUE, 'TITAN'),
       (24, TRUE, 'TITAN'),
       (25, TRUE, 'TITAN'),
       (26, TRUE, 'TITAN'),
       (27, TRUE, 'COLOSSUS'),
       (28, TRUE, 'COLOSSUS');




/* Setting up TEST DB */
CREATE DATABASE IF NOT EXISTS astroport_test;
USE astroport_test;

/* Creating the docks table */
CREATE TABLE docks
(
    DOCK_NUMBER INT PRIMARY KEY,
    AVAILABLE BOOL NOT NULL,
    TYPE VARCHAR(10) NOT NULL
);

/* Creating the tickets table */
CREATE TABLE tickets
(
    ID INT PRIMARY KEY AUTO_INCREMENT,
    DOCK_NUMBER INT NOT NULL,
    SHIP_REG_NUMBER VARCHAR(10) NOT NULL,
    PRICE DOUBLE,
    IN_TIME DATETIME NOT NULL,
    OUT_TIME DATETIME,
    FOREIGN KEY (DOCK_NUMBER) REFERENCES docks (DOCK_NUMBER)
);

/* Inserting data into the docks table */
INSERT INTO docks(DOCK_NUMBER, AVAILABLE, TYPE)
VALUES (1, TRUE, 'CORVETTE'),
       (2, TRUE, 'CORVETTE'),
       (3, TRUE, 'CORVETTE'),
       (4, TRUE, 'CORVETTE'),
       (5, TRUE, 'CORVETTE'),
       (6, TRUE, 'CORVETTE'),
       (7, TRUE, 'CORVETTE'),
       (8, TRUE, 'CORVETTE'),
       (9, TRUE, 'CORVETTE'),
       (10, TRUE, 'DESTROYER'),
       (11, TRUE, 'DESTROYER'),
       (12, TRUE, 'DESTROYER'),
       (13, TRUE, 'DESTROYER'),
       (14, TRUE, 'DESTROYER'),
       (15, TRUE, 'DESTROYER'),
       (16, TRUE, 'DESTROYER'),
       (17, TRUE, 'CRUISER'),
       (18, TRUE, 'CRUISER'),
       (19, TRUE, 'CRUISER'),
       (20, TRUE, 'CRUISER'),
       (21, TRUE, 'CRUISER'),
       (22, TRUE, 'CRUISER'),
       (23, TRUE, 'TITAN'),
       (24, TRUE, 'TITAN'),
       (25, TRUE, 'TITAN'),
       (26, TRUE, 'TITAN'),
       (27, TRUE, 'COLOSSUS'),
       (28, TRUE, 'COLOSSUS');
