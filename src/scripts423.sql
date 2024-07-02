SELECT s."name", s.age, f."name" FROM students s
FULL JOIN faculties f
ON s.faculty_id = f.id;

SELECT s.* FROM students s
JOIN avatars a
ON s.id = a.student_id ;