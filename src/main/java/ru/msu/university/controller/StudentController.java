package ru.msu.university.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.msu.university.entities.Faculty;
import ru.msu.university.entities.Student;
import ru.msu.university.service.StudentService;
import ru.msu.university.service.impl.StudentServiceImpl;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(required = false) Long id,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) Integer age,
                                      @RequestParam(required = false) Integer minAge,
                                      @RequestParam(required = false) Integer maxAge) {

        if (id != null) {
            return ResponseEntity.ok(studentService.get(id));
        }
        if (name != null) {
            return ResponseEntity.ok(studentService.getByName(name));
        }
        if (age != null) {
            return ResponseEntity.ok(studentService.getByAge(age));
        }
        if (minAge != null && minAge > 0 && maxAge != null && maxAge > 0) {
            return ResponseEntity.ok(studentService.getByAgeBetween(minAge, maxAge));
        } else if ((minAge != null ^ maxAge != null) || (minAge != null && minAge < 0) || (maxAge != null && maxAge < 0)) {
            return ResponseEntity.badRequest().body("Один из параметров введён не корректно");
        }
        return ResponseEntity.ok(studentService.getAll());
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

    @GetMapping("/amount")
    public ResponseEntity<Integer> getAmountStudent() {
        Integer amount = studentService.getAmountStudent();
        return ResponseEntity.ok(amount);
    }

    @GetMapping("/average-age")
    public ResponseEntity<Integer> getAvgAge() {
        Integer avg = studentService.getAverageAge();
        return ResponseEntity.ok(avg);
    }

    @GetMapping("/last")
    public ResponseEntity<List<Student>> getLastFive(@RequestParam(defaultValue = "5") Integer amount) {
        List<Student> students = studentService.getLast(amount);
        return ResponseEntity.ok(students);
    }
}
