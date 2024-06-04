package ru.msu.university.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.msu.university.entities.Faculty;
import ru.msu.university.entities.Student;
import ru.msu.university.service.StudentService;
import ru.msu.university.service.impl.StudentServiceImpl;

import java.util.Collection;

@RequestMapping("/student")
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> add(@RequestBody Student student) {
        Student addedStudent = studentService.add(student);
        return ResponseEntity.ok(addedStudent);
    }

    @GetMapping(params = "id")
    public ResponseEntity<Student> get(@RequestParam Long id) {
        Student student = studentService.get(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Collection<Student>> getAll() {
        Collection<Student> students = studentService.getAll();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/getByName")
    public ResponseEntity<Collection<Student>> getByName(String name) {
        Collection<Student> students = studentService.getByName(name);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/getByAge")
    public ResponseEntity<Collection<Student>> getByAge(int age) {
        Collection<Student> students = studentService.getByAge(age);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/getByAgeBetween")
    public ResponseEntity<Collection<Student>> getByAgeBetween(int minAge, int maxAge) {
        Collection<Student> students = studentService.getByAgeBetween(minAge, maxAge);
        return ResponseEntity.ok(students);
    }


    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> getFacultyOfStudent(@PathVariable Long id) {
        Faculty faculty = studentService.get(id).getFaculty();
        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    public ResponseEntity<Student> update(@RequestBody Student student) {
        Student updatedStudent = studentService.update(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping
    public ResponseEntity<Student> delete(Long id) {
        Student student = studentService.delete(id);
        return ResponseEntity.ok(student);
    }
}
