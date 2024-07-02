package ru.msu.university.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.msu.university.entities.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByNameContainsIgnoreCase(String name);

    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);

    Collection<Student> findByFaculty_Id(Long id);

    @Query(value = "SELECT COUNT(*) FROM students", nativeQuery = true)
    Integer getAmountOfStudent();

    @Query(value = "SELECT AVG(age) FROM students", nativeQuery = true)
    Integer getAverageAge();

    @Query(value = "SELECT * FROM students ORDER BY id DESC LIMIT :amount", nativeQuery = true)
    List<Student> getLast(Integer amount);
}
