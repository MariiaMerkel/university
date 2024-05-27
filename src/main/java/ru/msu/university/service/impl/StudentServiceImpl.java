package ru.msu.university.service.impl;

import org.springframework.stereotype.Service;
import ru.msu.university.exceptions.StudentNotFoundException;
import ru.msu.university.model.Student;
import ru.msu.university.service.StudentService;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    private final Map<Long, Student> students = new HashMap<>();
    private Long studentId = 0L;

    public Student add(Student student) {

        student.setId(++studentId);
        students.put(studentId, student);
        return student;
    }

    @Override
    public Student get(Long id) {

        Student student = students.get(id);
        if (student == null) {
            throw new StudentNotFoundException(id);
        }
        return student;
    }

    @Override
    public Collection<Student> getByAge(int age) {

        return students.values().stream().filter(s -> s.getAge() == age).toList();
    }

    @Override
    public Student update(Long id, Student student) {

        Student updatedStudent = null;
        updatedStudent = students.get(id);
        if (updatedStudent == null) {
            throw new StudentNotFoundException(id);
        }
        student.setId(updatedStudent.getId());
        students.replace(id, student);
        return student;
    }

    @Override
    public Student delete(Long id) {

        Student student = students.get(id);
        students.remove(id);
        return student;
    }

    @Override
    public Collection<Student> getAll() {

        return Collections.unmodifiableCollection(students.values());
    }
}
