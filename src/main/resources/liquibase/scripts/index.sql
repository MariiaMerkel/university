-- liquibase formatted sql

-- changeset mariiam:1
CREATE TABLE faculties (
	id bigserial PRIMARY KEY,
	"name" VARCHAR(255) NOT NULL,
	color VARCHAR(255) NOT NULL
);

CREATE TABLE students (
	id bigserial PRIMARY KEY,
	"name" VARCHAR(255) NOT NULL,
	age int4 NOT NULL,
	faculty_id int8 REFERENCES faculties (id) ON DELETE CASCADE
);

CREATE TABLE avatars (
	id bigserial PRIMARY KEY,
	file_path varchar(255),
	file_size int8 NOT NULL,
	media_type varchar(255),
	preview oid,
	data oid NOT NULL,
	student_id int8 REFERENCES students (id) ON DELETE CASCADE
);