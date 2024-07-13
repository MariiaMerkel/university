package ru.msu.university.service;

import ru.msu.university.entities.Student;

import java.util.Collection;
import java.util.List;
import java.util.OptionalDouble;

public interface StudentService {
    Student add(Student student);

    Student get(Long id);

    Collection<Student> getByName(String name);

    Collection<Student> getByAge(int age);

    Collection<Student> getByAgeBetween(int min, int max);

    Student update(Student student);

    Student delete(Long id);

    Collection<Student> getAll();

    Integer getAmountStudent();

    Integer getAverageAge();

    List<Student> getLast(Integer amount);

    Object getNamesStartingA();

    Double getAverageAge2();
}
