package ru.msu.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.msu.university.entities.Student;
import ru.msu.university.exceptions.CustomStudentException;
import ru.msu.university.exceptions.StudentNotFoundException;
import ru.msu.university.repositories.StudentRepository;
import ru.msu.university.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student add(Student student) {
        student.setId(null);
        try {
            Student saved = studentRepository.save(student);
            logger.debug("Saved student {}", saved);
            return saved;
        } catch (DataIntegrityViolationException e) {
            throw new CustomStudentException(student + " wasn't add.", e);
        }
    }

    @Override
    public Student get(Long id) {

        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            logger.debug("Student with id={} was find", id);
            return student.get();
        }
        logger.error("Student with id={} not found", id);
        throw new StudentNotFoundException(id);
    }

    @Override
    public Collection<Student> getByName(String name) {
        Collection<Student> student = studentRepository.findByNameContainsIgnoreCase(name);
        if (student.isEmpty()) {
            logger.error("Student wasn't find by name \"{}\"", name);
            throw new StudentNotFoundException(name);
        }
        logger.debug("Student found by name \"{}\"", name);
        return student;
    }

    @Override
    public Collection<Student> getByAge(int age) {
        Collection<Student> students = studentRepository.findByAge(age);
        if (students.isEmpty()) {
            logger.error("Student wasn't find by age={}", age);
            throw new StudentNotFoundException(age);
        }
        logger.debug("Students was find by age {}", students);
        return students;
    }

    @Override
    public Collection<Student> getByAgeBetween(int min, int max) {
        Collection<Student> students = studentRepository.findByAgeBetween(min, max);
        if (students.isEmpty()) {
            logger.error("Studenta wasn't find by age between {} and {}", min, max);
            throw new StudentNotFoundException("Студенты с указанным возрастом не найдены");
        }
        logger.debug("Students found by age between {} and {}", min, max);
        return students;
    }

    @Override
    public Student update(Student student) {
        Student updated = studentRepository.findById(student.getId())
                .map(s -> {
                    s.setName(student.getName());
                    s.setAge(student.getAge());
                    s.setFaculty(student.getFaculty());
                    return studentRepository.save(s);
                })
                .orElseThrow(() -> {
                    logger.error("Student {} wasn't update", student);
                    return new StudentNotFoundException(student.getId());
                });
        logger.debug("Updated student {}", updated);
        return updated;
    }

    @Override
    public Student delete(Long id) {
        Student deleted = studentRepository.findById(id)
                .map(s -> {
                    studentRepository.delete(s);
                    return s;
                })
                .orElseThrow(() -> {
                    logger.error("Student with id={} wasn't delete", id);
                    return new StudentNotFoundException(id);
                });
        logger.debug("deleted student {}", deleted);
        return deleted;
    }

    @Override
    public Collection<Student> getAll() {
        List<Student> all = studentRepository.findAll();
        logger.debug("All students was find");
        return all;
    }

    @Override
    public Integer getAmountStudent() {
        Integer amount = studentRepository.getAmountOfStudent();
        logger.debug("got amount of students {}", amount);
        return amount;
    }

    @Override
    public Integer getAverageAge() {
        Integer averageAge = studentRepository.getAverageAge();
        logger.debug("got average age of students {}", averageAge);
        return averageAge;
    }

    @Override
    public List<Student> getLast(Integer amount) {
        List<Student> last = studentRepository.getLast(amount);
        logger.debug("got last students {}", last);
        return last;
    }
}
