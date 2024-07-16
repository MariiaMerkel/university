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
    public Student add(@RequestBody Student student) {
        return studentService.add(student);
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
    public Faculty getFacultyOfStudent(@PathVariable Long id) {
        return studentService.get(id).getFaculty();
    }

    @PutMapping
    public Student update(@RequestBody Student student) {
        return studentService.update(student);
    }

    @DeleteMapping
    public Student delete(Long id) {
        return studentService.delete(id);
    }

    @GetMapping("/amount")
    public Integer getAmountStudent() {
        return studentService.getAmountStudent();
    }

    @GetMapping("/average-age")
    public Integer getAvgAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("/last")
    public List<Student> getLastFive(@RequestParam(defaultValue = "5") Integer amount) {
        return studentService.getLast(amount);
    }

    @GetMapping("/names-starting-with/{letter}")
    public ResponseEntity<?> getNamesStarting(@PathVariable String letter) {
        if (letter.length() > 1) {
            return ResponseEntity.badRequest().body("Invalid parameter: 'letter' must be a single character.");
        }
        return ResponseEntity.ok(studentService.getNamesStarting(letter));
    }

    @GetMapping("/average-age-second")
    public Double getAvgAgeSecond() {
        return studentService.getAverageAgeSecond();
    }

    @GetMapping("/students/print-parallel")
    public void printNamesParallel() {
        studentService.printAllNames();
    }
}
