package ru.msu.university.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.msu.university.model.Faculty;
import ru.msu.university.model.Student;
import ru.msu.university.service.StudentService;
import ru.msu.university.service.impl.StudentServiceImpl;

import java.util.Collection;

@RequestMapping("/student")
@RestController
public class StudentController {

    private StudentService studentService;

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
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAll() {
        Collection<Student> students = studentService.getAll();
        return ResponseEntity.ok(students);
    }

    @GetMapping(params = "name")
    public ResponseEntity<Collection<Student>> getByName(@RequestParam String name) {
        Collection<Student> students = studentService.getByName(name);
        return ResponseEntity.ok(students);
    }

    @GetMapping(params = "age")
    public ResponseEntity<Collection<Student>> getByAge(@RequestParam int age) {
        Collection<Student> students = studentService.getByAge(age);
        return ResponseEntity.ok(students);
    }

    @GetMapping(params = {"minAge", "maxAge"})
    public ResponseEntity<Collection<Student>> getByAgeBetween(@RequestParam int minAge, @RequestParam int maxAge) {
        Collection<Student> students = studentService.getByAgeBetween(minAge, maxAge);
        return ResponseEntity.ok(students);
    }


    @GetMapping("getFaculty/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Student student = studentService.get(id);
        Faculty faculty = student.getFaculty();
        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    public ResponseEntity<Student> update(@RequestBody Student student) {
        Student updatedStudent = studentService.update(student);
        if (updatedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping(params = "id")
    public ResponseEntity<Student> delete(@RequestParam Long id) {
        Student student = studentService.delete(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
}
