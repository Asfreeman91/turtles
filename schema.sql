DROP TABLE IF EXISTS species_location;
DROP TABLE IF EXISTS location_category;
DROP TABLE IF EXISTS species;
DROP TABLE IF EXISTS location;
DROP TABLE IF EXISTS category;

CREATE TABLE category (
category_id int NOT NULL AUTO_INCREMENT,
family_name varchar(60) NOT NULL,
PRIMARY KEY (category_id)
);

CREATE TABLE location (
location_id int NOT NULL AUTO_INCREMENT,
country varchar(256) NOT NULL,
PRIMARY KEY (location_id)
);

CREATE TABLE species (
species_id int NOT NULL AUTO_INCREMENT,
name varchar(60) NOT NULL,
characteristics varchar(256) NOT NULL,
category_id int NOT NULL,
PRIMARY KEY (species_id),
FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE CASCADE
);

CREATE TABLE location_category (
location_id int NOT NULL,
category_id int NOT NULL,
FOREIGN KEY (location_id) REFERENCES location (location_id) ON DELETE CASCADE,
FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE CASCADE,
UNIQUE KEY (location_id, category_id)
);

CREATE TABLE species_location (
species_id int NOT NULL,
location_id int NOT NULL,
FOREIGN KEY (species_id) REFERENCES species (species_id) ON DELETE CASCADE,
FOREIGN KEY (location_id) REFERENCES location (location_id) ON DELETE CASCADE,
UNIQUE KEY (species_id, location_id)
);
