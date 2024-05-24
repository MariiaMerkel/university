package ru.msu.university.service.impl;

import org.springframework.stereotype.Service;
import ru.msu.university.model.Student;
import ru.msu.university.service.StudentService;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {

    private Map<Long, Student> students = new HashMap<>();
    private Long studentId = 0L;

    public Student add(Student student) {
        student.setId(++studentId);
        return students.put(studentId, student);
    }

    @Override
    public Student get(Long id) {
        return students.get(id);
    }

    @Override
    public Collection<Student> getByAge(int age) {
        List<Student> studentList = students.values().stream().filter(s -> s.getAge() == age).toList();
        return Collections.unmodifiableCollection(studentList);
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

    @Override
    public Collection<Student> getAll() {
        return Collections.unmodifiableCollection(students.values());
    }
}
