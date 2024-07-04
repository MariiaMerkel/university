-- liquibase formatted sql

-- changeset mariiam:1
CREATE INDEX student_name_index ON students (name);

-- changeset mariiam:2
CREATE INDEX faculty_name_color_index ON faculties ("name", color);