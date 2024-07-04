ALTER TABLE students
ADD CONSTRAINT age_constraint CHECK (age > 16),
ADD CONSTRAINT name_unique UNIQUE (name),
ALTER COLUMN name SET NOT NULL,
ALTER COLUMN age SET DEFAULT 20;

ALTER TABLE faculties
ADD CONSTRAINT color_name_unique UNIQUE (color, name);
