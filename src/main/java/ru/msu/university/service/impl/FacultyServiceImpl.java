package ru.msu.university.service.impl;

import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.msu.university.entities.Faculty;
import ru.msu.university.entities.Student;
import ru.msu.university.exceptions.FacultyNotFoundException;
import ru.msu.university.repositories.FacultyRepository;
import ru.msu.university.repositories.StudentRepository;
import ru.msu.university.service.FacultyService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty add(Faculty faculty) {
        faculty.setId(null);
        try {
            Faculty saved = facultyRepository.save(faculty);
            logger.info("Added faculty {}", saved);
            return saved;
        } catch (Exception e) {
            logger.error("Faculty {} wasn't add", faculty);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Faculty get(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isPresent()) {
            logger.info("Was find faculty {}", faculty);
            return faculty.get();
        }
        logger.error("Faculty with id={} not found", id);
        throw new FacultyNotFoundException(id);
    }

    @Override
    public Collection<Faculty> getByName(String name) {
        Collection<Faculty> faculties = facultyRepository.findByNameContainsIgnoreCase(name);
        if (faculties.isEmpty()) {
            logger.error("Faculties with name={} not found", name);
            throw new FacultyNotFoundException(name);
        }
        logger.info("Was find by name faculties {}", faculties);
        return faculties;
    }

    @Override
    public Collection<Faculty> getByColor(String color) {
        Collection<Faculty> faculties = facultyRepository.findByColorContainsIgnoreCase(color);
        logger.info("Was find faculty by color {}", faculties);
        if (faculties.isEmpty()) {
            logger.error("Faculty with color={} not found", color);
            throw new FacultyNotFoundException(color);
        }
        return facultyRepository.findByColorContainsIgnoreCase(color);
    }

    @Override
    public Collection<Faculty> getByNameOrColor(String nameOrColor) {
        Collection<Faculty> faculties = facultyRepository.findByNameContainsIgnoreCaseOrColorContainsIgnoreCase(nameOrColor, nameOrColor);
        logger.info("Was find faculties by name or color {}", faculties);
        return faculties;
    }

    @Override
    public Collection<Student> getStudentsByFaculty(Long facultyId) {
        Collection<Student> students = studentRepository.findByFaculty_Id(facultyId);
        logger.info("Was find students by faculty {}", students);
        return students;

    }

    @Override
    public Faculty update(Faculty faculty) {
        Faculty founded = facultyRepository.findById(faculty.getId())
                .map(f -> {
                    f.setName(faculty.getName());
                    f.setColor(faculty.getColor());
                    return facultyRepository.save(f);
                })
                .orElseThrow(() -> {
                    logger.error("Faculty {} wasn't update", faculty);
                    return new FacultyNotFoundException(faculty.getId());
                });
        logger.info("Updated faculty {}", faculty);
        return founded;
    }

    @Override
    public Faculty delete(Long id) {
       Faculty faculty = facultyRepository.findById(id)
                .map(f -> {
                    facultyRepository.delete(f);
                    return f;
                })
                .orElseThrow(() -> {
                    logger.error("Faculty with id={} wasn't delete", id);
                    return new FacultyNotFoundException(id);
                });
        logger.info("Deleted faculty {}", faculty);
        return faculty;
    }

    @Override
    public Collection<Faculty> getAll() {
        List<Faculty> all = facultyRepository.findAll();
        logger.info("Founded all faculties {}", all);
        return all;
    }
}
