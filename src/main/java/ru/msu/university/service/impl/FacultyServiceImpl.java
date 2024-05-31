package ru.msu.university.service.impl;

import org.springframework.stereotype.Service;
import ru.msu.university.exceptions.FacultyNotFoundException;
import ru.msu.university.model.Faculty;
import ru.msu.university.repositories.FacultyRepository;
import ru.msu.university.service.FacultyService;

import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {

    private FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty add(Faculty faculty) {
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
    public Collection<Faculty> getByColor(String color) {
        Collection<Faculty> faculties = facultyRepository.findByColorContainsIgnoreCase(color);
        if (faculties.isEmpty()) {
            throw new FacultyNotFoundException(color);
        }
        return facultyRepository.findByColorContainsIgnoreCase(color);
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
    public Faculty update(Faculty faculty) {
        Optional<Faculty> facultyOptional = facultyRepository.findById(faculty.getId());
        if (facultyOptional.isPresent()) {
            facultyRepository.save(faculty);
            return faculty;
        }
        throw new FacultyNotFoundException(faculty.getId());
    }

    @Override
    public Faculty delete(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isPresent()) {
            facultyRepository.deleteById(id);
            return faculty.get();
        }
        throw new FacultyNotFoundException(id);
    }

    @Override
    public Collection<Faculty> getAll() {
        return facultyRepository.findAll();
    }
}
