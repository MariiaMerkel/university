package ru.msu.university.service;

import ru.msu.university.model.Faculty;
import ru.msu.university.model.Student;

import java.util.Collection;

public interface FacultyService {
    Faculty add(Faculty faculty);

    Faculty get(Long id);

    Collection<Faculty> getByName(String name);

    Collection<Faculty> getByColor(String color);

    Faculty update(Faculty faculty);

    Faculty delete(Long id);

    Collection<Faculty> getAll();

    Collection<Student> getStudents(Long id);

    Collection<Faculty> getByNameOrColor(String nameOrColor);
}
