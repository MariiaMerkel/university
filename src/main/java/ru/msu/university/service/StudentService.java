package ru.msu.university.service;

import ru.msu.university.model.Student;

import java.util.Collection;

public interface StudentService {
    Student add(Student student);

    Student get(Long id);

    Collection<Student> getByName(String name);

    Collection<Student> getByAge(int age);

    Collection<Student> getByAgeBetween(int min, int max);

    Student update(Student student);

    Student delete(Long id);

    Collection<Student> getAll();
}
