package ru.msu.university.service;

import ru.msu.university.model.Student;

public interface StudentService {
    Student add(Student student);

    Student get(Long id);

    Student update(Long id, Student student);

    Student delete(Long id);

    Student delete(Student student);
}
