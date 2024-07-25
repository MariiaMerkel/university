package ru.msu.university.service;

import java.util.Collection;
import java.util.List;
import ru.msu.university.entities.Faculty;
import ru.msu.university.entities.Student;

public interface FacultyService {

    Faculty add(Faculty faculty);

    Faculty get(Long id);

    Collection<Faculty> getByName(String name);

    Collection<Faculty> getByColor(String color);

    Faculty update(Faculty faculty);

    Faculty delete(Long id);

    Collection<Faculty> getAll();

    Collection<Student> getStudentsByFaculty(Long id);

    Collection<Faculty> getByNameOrColor(String nameOrColor);

    List<String> getFacultiesWithLongestNames();
}
