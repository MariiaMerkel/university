package ru.msu.university.controller;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Faculty> add(@RequestBody Faculty faculty) {
        Faculty addedFaculty = facultyService.add(faculty);
        return ResponseEntity.ok(addedFaculty);
    }

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(required = false) Long id,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) String color) {

        if (id != null) {
            return ResponseEntity.ok(facultyService.get(id));
        }
        if (name != null) {
            return ResponseEntity.ok(facultyService.getByName(name));
        }
        if (color != null) {
            return ResponseEntity.ok(facultyService.getByColor(color));
        }
        return ResponseEntity.ok(facultyService.getAll());
    }

    @GetMapping(params = "nameOrColor")
    public ResponseEntity<Collection<Faculty>> getByNameOrColor(@RequestParam(required = false) String nameOrColor) {
        Collection<Faculty> faculties = facultyService.getByNameOrColor(nameOrColor);
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Collection<Student>> getStudentsByFaculty(@PathVariable Long id) {
        Collection<Student> students = facultyService.getStudentsByFaculty(id);
        return ResponseEntity.ok(students);
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

    @GetMapping("/with-longest-names")
    public ResponseEntity<List<String>> findFacultiesWithLongestNames() {
        return ResponseEntity.ok(facultyService.getFacultiesWithLongestNames());
    }
}