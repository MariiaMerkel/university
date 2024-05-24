package ru.msu.university.service.impl;

import org.springframework.stereotype.Service;
import ru.msu.university.model.Faculty;
import ru.msu.university.service.FacultyService;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final Map<Long, Faculty> faculties = new HashMap<>();
    private Long facultyId = 0L;

    @Override
    public Faculty add(Faculty faculty) {
        faculty.setId(++facultyId);
        faculties.put(facultyId, faculty);
        return faculty;
    }

    @Override
    public Faculty get(Long id) {
        return faculties.get(id);
    }

    @Override
    public Collection<Faculty> getByColor(String color) {
        return faculties.values().stream().filter(s -> s.getColor().equals(color)).toList();
    }

    @Override
    public Faculty update(Long id, Faculty faculty) {
        faculties.replace(id, faculty);
        return faculty;
    }

    @Override
    public Faculty delete(Long id) {
        Faculty faculty = faculties.get(id);
        faculties.remove(id);
        return faculty;
    }

    @Override
    public Collection<Faculty> getAll() {
        return Collections.unmodifiableCollection(faculties.values());
    }
}
