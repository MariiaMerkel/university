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

    @PostMapping("/add")
    public ResponseEntity<Faculty> add(@RequestBody Faculty faculty) {
        Faculty addedFaculty = facultyService.add(faculty);
        return ResponseEntity.ok(addedFaculty);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Faculty> get(@PathVariable Long id) {
        Faculty faculty = facultyService.get(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("getByColor/{color}")
    public ResponseEntity<Collection<Faculty>> getByAge(@PathVariable String color) {
        Collection<Faculty> faculties = facultyService.getByColor(color);
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Collection<Faculty>> getAll() {
        Collection<Faculty> faculties = facultyService.getAll();
        return ResponseEntity.ok(faculties);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Faculty> update(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.update(faculty.getId(), faculty);
        if (updatedFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Faculty> delete(@PathVariable Long id) {
        Faculty faculty = facultyService.delete(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Faculty> delete(@RequestBody Faculty faculty) {
        Faculty deletedFaculty = facultyService.delete(faculty);
        if (deletedFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletedFaculty);
    }
}