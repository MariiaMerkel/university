package ru.msu.university.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.msu.university.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByName(String name);

    Collection<Student> findByAge(int age);

}
