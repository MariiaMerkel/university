package ru.msu.university.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.msu.university.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
//    @Query("SELECT s FROM student s WHERE s.age = :age")
//    Collection<Student> findByAge(@Param("age") int age);

}
