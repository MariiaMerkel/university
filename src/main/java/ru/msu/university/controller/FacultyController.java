package ru.msu.university.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.msu.university.model.Faculty;
import ru.msu.university.model.Student;
import ru.msu.university.service.FacultyService;
import ru.msu.university.service.impl.FacultyServiceImpl;

import java.util.Collection;

@RequestMapping("/faculty")
@RestController
public class FacultyController {

    private FacultyService facultyService;

    public FacultyController(FacultyServiceImpl facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> add(@RequestBody Faculty faculty) {
        Faculty addedFaculty = facultyService.add(faculty);
        return ResponseEntity.ok(addedFaculty);
    }

    @GetMapping(params = "id")
    public ResponseEntity<Faculty> get(@RequestParam Long id) {
        Faculty faculty = facultyService.get(id);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping(params = "name")
    public ResponseEntity<Collection<Faculty>> getByName(@RequestParam String name) {
        Collection<Faculty> faculties = facultyService.getByName(name);
        return ResponseEntity.ok(faculties);
    }

    @GetMapping(params = "color")
    public ResponseEntity<Collection<Faculty>> getByColor(@RequestParam String color) {
        Collection<Faculty> faculties = facultyService.getByColor(color);
        return ResponseEntity.ok(faculties);
    }

    @GetMapping(params = "nameOrColor")
    public ResponseEntity<Collection<Faculty>> getByNameOrColor(@RequestParam String nameOrColor) {
        Collection<Faculty> faculties = facultyService.getByNameOrColor(nameOrColor);
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("getStudents/{id}")
    public ResponseEntity<Collection<Student>> getStudents(@PathVariable Long id) {
        Collection<Student> students = facultyService.getStudents(id);
        return ResponseEntity.ok(students);
    }


    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAll() {
        Collection<Faculty> faculties = facultyService.getAll();
        return ResponseEntity.ok(faculties);
    }

    @PutMapping
    public ResponseEntity<Faculty> update(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.update(faculty);
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping(params = "id")
    public ResponseEntity<Faculty> delete(@RequestParam Long id) {
        Faculty faculty = facultyService.delete(id);
        return ResponseEntity.ok(faculty);
    }
}