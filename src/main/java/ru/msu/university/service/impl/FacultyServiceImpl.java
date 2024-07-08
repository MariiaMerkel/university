package ru.msu.university.service.impl;

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
        Faculty saved = facultyRepository.save(faculty);
        logger.debug("added faculty {}", saved);
        return saved;
    }

    @Override
    public Faculty get(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        logger.debug("got faculty {}", faculty);
        if (faculty.isPresent()) {
            return faculty.get();
        }
        throw new FacultyNotFoundException(id);
    }

    @Override
    public Collection<Faculty> getByName(String name) {
        Collection<Faculty> faculties = facultyRepository.findByNameContainsIgnoreCase(name);
        if (faculties.isEmpty()) {
            throw new FacultyNotFoundException(name);
        }
        logger.debug("got by name facultyies {}", faculties);
        return faculties;
    }

    @Override
    public Collection<Faculty> getByColor(String color) {
        Collection<Faculty> faculties = facultyRepository.findByColorContainsIgnoreCase(color);
        logger.debug("got faculty by color {}", faculties);
        if (faculties.isEmpty()) {
            throw new FacultyNotFoundException(color);
        }
        return facultyRepository.findByColorContainsIgnoreCase(color);
    }

    @Override
    public Collection<Faculty> getByNameOrColor(String nameOrColor) {
        Collection<Faculty> faculties = facultyRepository.findByNameContainsIgnoreCaseOrColorContainsIgnoreCase(nameOrColor, nameOrColor);
        logger.debug("got faculties by name or color {}", faculties);
        return faculties;
    }

    @Override
    public Collection<Student> getStudentsByFaculty(Long facultyId) {
        Collection<Student> students = studentRepository.findByFaculty_Id(facultyId);
        logger.debug("got students by faculty {}", students);
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
                .orElseThrow(() -> new FacultyNotFoundException(faculty.getId()));
        logger.debug("updated faculty {}", faculty);
        return founded;
    }

    @Override
    public Faculty delete(Long id) {
       Faculty faculty = facultyRepository.findById(id)
                .map(f -> {
                    facultyRepository.delete(f);
                    return f;
                })
                .orElseThrow(() -> new FacultyNotFoundException(id));
        logger.debug("deleted faculty {}", faculty);
        return faculty;
    }

    @Override
    public Collection<Faculty> getAll() {
        List<Faculty> all = facultyRepository.findAll();
        logger.debug("deleted faculty {}", all);
        return all;
    }
}
