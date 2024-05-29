package ru.msu.university.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.msu.university.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
