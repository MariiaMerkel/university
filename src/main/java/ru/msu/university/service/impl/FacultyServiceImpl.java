package ru.msu.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.msu.university.entities.Faculty;
import ru.msu.university.entities.Student;
import ru.msu.university.exceptions.CustomFacultyException;
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

    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty add(Faculty faculty) {
        faculty.setId(null);
        try {
            Faculty saved = facultyRepository.save(faculty);
            logger.debug("Added faculty {}", saved);
            return saved;
        } catch (DataIntegrityViolationException e) {
            throw new CustomFacultyException(faculty + " wasn't add.", e);
        }
    }

    @Override
    public Faculty get(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isPresent()) {
            logger.debug("Was find faculty {}", faculty);
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
        logger.debug("Was find by name faculties {}", faculties);
        return faculties;
    }

    @Override
    public Collection<Faculty> getByColor(String color) {
        Collection<Faculty> faculties = facultyRepository.findByColorContainsIgnoreCase(color);
        logger.debug("Was find faculty by color {}", faculties);
        if (faculties.isEmpty()) {
            logger.error("Faculty with color={} not found", color);
            throw new FacultyNotFoundException(color);
        }
        return facultyRepository.findByColorContainsIgnoreCase(color);
    }

    @Override
    public Collection<Faculty> getByNameOrColor(String nameOrColor) {
        Collection<Faculty> faculties = facultyRepository.findByNameContainsIgnoreCaseOrColorContainsIgnoreCase(nameOrColor, nameOrColor);
        logger.debug("Was find faculties by name or color {}", faculties);
        return faculties;
    }

    @Override
    public Collection<Student> getStudentsByFaculty(Long facultyId) {
        Collection<Student> students = studentRepository.findByFaculty_Id(facultyId);
        logger.debug("Was find students by faculty {}", students);
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
        logger.debug("Updated faculty {}", faculty);
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
        logger.debug("Deleted faculty {}", faculty);
        return faculty;
    }

    @Override
    public Collection<Faculty> getAll() {
        List<Faculty> all = facultyRepository.findAll();
        logger.debug("Founded all faculties {}", all);
        return all;
    }
}
