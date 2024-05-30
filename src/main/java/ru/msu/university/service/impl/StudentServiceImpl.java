package ru.msu.university.service.impl;

import org.springframework.stereotype.Service;
import ru.msu.university.exceptions.StudentNotFoundException;
import ru.msu.university.model.Student;
import ru.msu.university.repositories.StudentRepository;
import ru.msu.university.service.StudentService;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student add(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student get(Long id) {

        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return student.get();
        }
        throw new StudentNotFoundException(id);
    }

    @Override
    public Collection<Student> getByName(String name) {
        Collection<Student> student = studentRepository.findByName(name);
        if (student.isEmpty()) {
            throw new StudentNotFoundException(name);
        } else {
            return student;
        }
    }

    @Override
    public Collection<Student> getByAge(int age) {
        Collection<Student> students = studentRepository.findByAge(age);
        if (students.isEmpty()) {
            throw new StudentNotFoundException(age);
        } else {
            return students;
        }
    }

    @Override
    public Student update(Student student) {
        Optional<Student> studentOptional = studentRepository.findById(student.getId());
        if (studentOptional.isPresent()) {
            return studentRepository.save(student);
        }
        throw new StudentNotFoundException(student.getId());
    }

    @Override
    public Student delete(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            studentRepository.deleteById(id);
            return student.get();
        }
        throw new StudentNotFoundException(id);
    }

    @Override
    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }
}
