package ru.msu.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.msu.university.entities.Student;
import ru.msu.university.exceptions.StudentNotFoundException;
import ru.msu.university.repositories.StudentRepository;
import ru.msu.university.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student add(Student student) {
        student.setId(null);
        Student saved = studentRepository.save(student);
        logger.debug("saved student {}", saved);
        return saved;
    }

    @Override
    public Student get(Long id) {

        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            logger.debug("got student {}", student.get());
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
        logger.debug("got student by name {}", student);
        return student;
    }

    @Override
    public Collection<Student> getByAge(int age) {
        Collection<Student> students = studentRepository.findByAge(age);
        if (students.isEmpty()) {
            throw new StudentNotFoundException(age);
        }
        logger.debug("got students by age {}", students);
        return students;
    }

    @Override
    public Collection<Student> getByAgeBetween(int min, int max) {
        Collection<Student> students = studentRepository.findByAgeBetween(min, max);
        if (students.isEmpty()) {
            throw new StudentNotFoundException("Студенты с указанным возрастом не найдены");
        }
        logger.debug("got students {}", students);
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
                .orElseThrow(() -> new StudentNotFoundException(student.getId()));
        logger.debug("updated student {}", updated);
    }

    @Override
    public Student delete(Long id) {
        Student deleted = studentRepository.findById(id)
                .map(s -> {
                    studentRepository.delete(s);
                    return s;
                })
                .orElseThrow(() -> new StudentNotFoundException(id));
        logger.debug("deleted student {}", deleted);

    }

    @Override
    public Collection<Student> getAll() {
        List<Student> all = studentRepository.findAll();
        logger.debug("got all students {}", all);
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
