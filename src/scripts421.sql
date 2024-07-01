ALTER TABLE students
ADD CONSTRAINT age_constraint CHECK (age > 16);