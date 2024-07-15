package ru.msu.university.controller;

import org.springframework.web.bind.annotation.*;
import ru.msu.university.entities.Faculty;
import ru.msu.university.entities.Student;
import ru.msu.university.service.FacultyService;
import ru.msu.university.service.impl.FacultyServiceImpl;

import java.util.Collection;
import java.util.List;

@RequestMapping("/faculty")
@RestController
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyServiceImpl facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty add(@RequestBody Faculty faculty) {
        return facultyService.add(faculty);
    }

    @GetMapping
    public Object get(@RequestParam(required = false) Long id,
                      @RequestParam(required = false) String name,
                      @RequestParam(required = false) String color) {

        if (id != null) {
            return facultyService.get(id);
        }
        if (name != null) {
            return facultyService.getByName(name);
        }
        if (color != null) {
            return facultyService.getByColor(color);
        }
        return facultyService.getAll();
    }

    @GetMapping(params = "nameOrColor")
    public Collection<Faculty> getByNameOrColor(@RequestParam(required = false) String nameOrColor) {
        return facultyService.getByNameOrColor(nameOrColor);
    }

    @GetMapping("/{id}/students")
    public Collection<Student> getStudentsByFaculty(@PathVariable Long id) {
        return facultyService.getStudentsByFaculty(id);
    }

    @PutMapping
    public Faculty update(@RequestBody Faculty faculty) {
        return facultyService.update(faculty);
    }

    @DeleteMapping(params = "id")
    public Faculty delete(@RequestParam Long id) {
        return facultyService.delete(id);
    }

    @GetMapping("/with-longest-names")
    public List<String> findFacultiesWithLongestNames() {
        return facultyService.getFacultiesWithLongestNames();
    }
}