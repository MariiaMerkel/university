CREATE TABLE persons (
	id SERIAL PRIMARY KEY,
	"name" VARCHAR(50) NOT NULL,
	age INTEGER CHECK (age > 0),
	driver_license BOOLEAN default false
);

CREATE TABLE cars (
	id SERIAL PRIMARY KEY,
	brand VARCHAR(30) NOT NULL,
	model VARCHAR(30) NOT NULL,
	price NUMERIC(9, 2) NOT NULL
);

CREATE TABLE persons_cars (
	id_person INTEGER NOT NULL REFERENCES persons (id),
	id_car INTEGER NOT NULL REFERENCES cars (id)
);