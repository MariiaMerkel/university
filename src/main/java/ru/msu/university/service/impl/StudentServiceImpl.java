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
        student.setId(null);
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
        Collection<Student> student = studentRepository.findByNameContainsIgnoreCase(name);
        if (student.isEmpty()) {
            throw new StudentNotFoundException(name);
        }
        return student;
    }

    @Override
    public Collection<Student> getByAge(int age) {
        Collection<Student> students = studentRepository.findByAge(age);
        if (students.isEmpty()) {
            throw new StudentNotFoundException(age);
        }
        return students;
    }

    @Override
    public Collection<Student> getByAgeBetween(int min, int max) {
        Collection<Student> students = studentRepository.findByAgeBetween(min, max);
        if (students.isEmpty()) {
            throw new StudentNotFoundException("Студенты с указанным возрастом не найдены");
        }
        return students;
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
