-- TripMate database schema
-- Compatible with the current TiDB Cloud schema supplied for this project.
-- This script creates table structures only; it does not insert real user data.
-- Run against a NEW/EMPTY database. Do not run against the live database without a backup.

CREATE DATABASE IF NOT EXISTS tripmate
  CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
USE tripmate;

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE IF NOT EXISTS destinations (
    id BIGINT NOT NULL AUTO_INCREMENT,
    average_budget DOUBLE NOT NULL,
    country VARCHAR(100) NOT NULL,
    description TEXT DEFAULT NULL,
    image_url TEXT DEFAULT NULL,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE IF NOT EXISTS places (
    id BIGINT NOT NULL AUTO_INCREMENT,
    category VARCHAR(50) NOT NULL,
    description TEXT DEFAULT NULL,
    estimated_cost DOUBLE NOT NULL,
    image_url TEXT DEFAULT NULL,
    location VARCHAR(150) NOT NULL,
    name VARCHAR(150) NOT NULL,
    destination_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    KEY FKsjlvqcdj2bs6y5cwjqsqc4vbp (destination_id),
    CONSTRAINT FKsjlvqcdj2bs6y5cwjqsqc4vbp
        FOREIGN KEY (destination_id) REFERENCES destinations (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE IF NOT EXISTS trips (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id INT DEFAULT NULL,
    destination VARCHAR(150) DEFAULT NULL,
    start_date DATE DEFAULT NULL,
    end_date DATE DEFAULT NULL,
    budget DOUBLE NOT NULL,
    created_at DATETIME(6) NOT NULL,
    estimated_cost DOUBLE NOT NULL,
    interests TEXT DEFAULT NULL,
    number_of_days INT NOT NULL,
    number_of_travelers INT NOT NULL,
    status VARCHAR(30) NOT NULL,
    travel_date DATE NOT NULL,
    destination_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    KEY fk_1 (user_id),
    KEY FKqyeck80yftx02q35eo8s1ebv2 (destination_id),
    CONSTRAINT fk_1 FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT FKqyeck80yftx02q35eo8s1ebv2
        FOREIGN KEY (destination_id) REFERENCES destinations (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE IF NOT EXISTS trip_places (
    id BIGINT NOT NULL AUTO_INCREMENT,
    notes TEXT DEFAULT NULL,
    visit_day INT NOT NULL,
    place_id BIGINT NOT NULL,
    trip_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    KEY FKgn03ug5cxgm3n5v41fs3ohlgg (place_id),
    KEY FKq5s9ydlwtwbfo0so818xdcge8 (trip_id),
    CONSTRAINT FKgn03ug5cxgm3n5v41fs3ohlgg
        FOREIGN KEY (place_id) REFERENCES places (id),
    CONSTRAINT FKq5s9ydlwtwbfo0so818xdcge8
        FOREIGN KEY (trip_id) REFERENCES trips (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- Optional verification:
-- SHOW TABLES;
-- SHOW CREATE TABLE users;
-- SHOW CREATE TABLE destinations;
-- SHOW CREATE TABLE places;
-- SHOW CREATE TABLE trips;
-- SHOW CREATE TABLE trip_places;
