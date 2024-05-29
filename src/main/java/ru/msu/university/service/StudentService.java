package ru.msu.university.service;

import ru.msu.university.model.Student;

import java.util.Collection;

public interface StudentService {
    Student add(Student student);

    Student get(Long id);

    Collection<Student> getByAge(int age);

    Student update(Student student);

    Student delete(Long id);

    Collection<Student> getAll();
}
