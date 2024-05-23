package ru.msu.university.service.impl;

import org.springframework.stereotype.Service;
import ru.msu.university.model.Faculty;
import ru.msu.university.service.FacultyService;

import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyServiceImpl implements FacultyService {

    private Map<Long, Faculty> faculties = new HashMap<>();
    private Long facultyId = 1L;

    @Override
    public Faculty add(Faculty faculty) {
        return faculties.put(facultyId++, faculty);
    }

    @Override
    public Faculty get(Long id) {
        return faculties.get(id);
    }

    @Override
    public Faculty update(Long id, Faculty faculty) {
        return faculties.replace(id, faculty);
    }

    @Override
    public Faculty delete(Long id) {
        return faculties.remove(id);
    }

    @Override
    public Faculty delete(Faculty faculty) {
        return faculties.remove(faculty);
    }
}
