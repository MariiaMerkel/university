package ru.msu.university.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.msu.university.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findByName(String name);

    Collection<Faculty> findByColor(String color);
}
