package ru.msu.university.service;

import ru.msu.university.model.Faculty;
import ru.msu.university.model.Student;

import java.util.Collection;

public interface FacultyService {
    Faculty add(Faculty faculty);

    Faculty get(Long id);

    Collection<Faculty> getByColor(String color);

    Faculty update(Long id, Faculty faculty);

    Faculty delete(Long id);

    Faculty delete(Faculty faculty);

    Collection<Faculty> getAll();
}
