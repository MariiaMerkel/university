package ru.msu.university.service.impl;

import org.springframework.stereotype.Service;
import ru.msu.university.model.Student;
import ru.msu.university.service.StudentService;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    private Map<Long, Student> students = new HashMap<>();
    private Long studentId = 1L;

    public Student add(Student student) {
        return students.put(studentId++, student);
    }

    @Override
    public Student get(Long id) {
        return students.get(id);
    }

    @Override
    public Student update(Long id, Student student) {
        return students.replace(id, student);
    }

    @Override
    public Student delete(Long id) {
        return students.remove(id);
    }

    @Override
    public Student delete(Student student) {
        return students.remove(student);
    }
}
