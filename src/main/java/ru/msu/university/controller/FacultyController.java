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

    @GetMapping("{id}")
    public ResponseEntity<Faculty> get(@PathVariable Long id) {
        Faculty faculty = facultyService.get(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("getByName/{name}")
    public ResponseEntity<Collection<Faculty>> getByName(@PathVariable String name) {
        Collection<Faculty> faculties = facultyService.getByName(name);
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("getByColor/{color}")
    public ResponseEntity<Collection<Faculty>> getByAge(@PathVariable String color) {
        Collection<Faculty> faculties = facultyService.getByColor(color);
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
        if (updatedFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> delete(@PathVariable Long id) {
        Faculty faculty = facultyService.delete(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }
}