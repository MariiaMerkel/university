package ru.msu.university.service.impl;

import org.springframework.stereotype.Service;
import ru.msu.university.entities.Faculty;
import ru.msu.university.entities.Student;
import ru.msu.university.exceptions.FacultyNotFoundException;
import ru.msu.university.repositories.FacultyRepository;
import ru.msu.university.repositories.StudentRepository;
import ru.msu.university.service.FacultyService;

import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty add(Faculty faculty) {
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty get(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
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
        return faculties;
    }

    @Override
    public Collection<Faculty> getByColor(String color) {
        Collection<Faculty> faculties = facultyRepository.findByColorContainsIgnoreCase(color);
        if (faculties.isEmpty()) {
            throw new FacultyNotFoundException(color);
        }
        return facultyRepository.findByColorContainsIgnoreCase(color);
    }

    @Override
    public Collection<Faculty> getByNameOrColor(String nameOrColor) {
        return facultyRepository.findByNameContainsIgnoreCaseOrColorContainsIgnoreCase(nameOrColor, nameOrColor);
    }

    @Override
    public Collection<Student> getStudentsByFaculty(Long facultyId) {
        return studentRepository.findByFaculty_Id(facultyId);

    }

    @Override
    public Faculty update(Faculty faculty) {
        return facultyRepository.findById(faculty.getId())
                .map(f -> {
                    f.setName(faculty.getName());
                    f.setColor(faculty.getColor());
                    return facultyRepository.save(f);
                })
                .orElseThrow(() -> new FacultyNotFoundException(faculty.getId()));
    }

    @Override
    public Faculty delete(Long id) {
        return facultyRepository.findById(id)
                .map(f -> {
                    facultyRepository.delete(f);
                    return f;
                })
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }

    @Override
    public Collection<Faculty> getAll() {
        return facultyRepository.findAll();
    }
}
