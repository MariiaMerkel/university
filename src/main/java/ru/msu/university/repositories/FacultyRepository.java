package ru.msu.university.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.msu.university.entities.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findByNameContainsIgnoreCase(String name);

    Collection<Faculty> findByColorContainsIgnoreCase(String color);

    Collection<Faculty> findByNameContainsIgnoreCaseOrColorContainsIgnoreCase(String name, String color);
}
