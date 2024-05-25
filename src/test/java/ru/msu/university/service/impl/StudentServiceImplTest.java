package ru.msu.university.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.msu.university.exceptions.StudentNotFoundException;
import ru.msu.university.model.Student;
import ru.msu.university.service.StudentService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {

    private final StudentService studentService = new StudentServiceImpl();

    @BeforeEach
    void setUp() {
        studentService.add(new Student("Alex", 40));
        studentService.add(new Student("Sergey", 30));
    }

    @Test
    void addTest() {
        Student expected = new Student("Mariia", 35);
        expected.setId(3L);
        Student expected2 = new Student("Tatyana", 50);
        expected2.setId(4L);

        Student actual = studentService.add(new Student("Mariia", 35));
        Student actual2 = studentService.add(new Student("Tatyana", 50));

        assertEquals(expected, actual);
        assertEquals(expected2, actual2);
    }

    @Test
    void getTest() {
        Student expected = new Student("Sergey", 30);
        expected.setId(2L);

        Student actual = studentService.get(2L);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnExceptionForGetting() {
        assertThrows(StudentNotFoundException.class, () -> studentService.get(3L));
        StudentNotFoundException thrown = Assertions.assertThrows(StudentNotFoundException.class, () -> {
            studentService.get(3L);
        }, "Студент с id=3 не найден");
    }

    @Test
    void getByAgeTest() {
        Collection<Student> expected = new ArrayList<>();
        Student student = new Student("Sergey", 30);
        student.setId(2L);
        expected.add(student);

        Collection<Student> expected2 = new ArrayList<>();

        Collection<Student> actual = studentService.getByAge(30);
        Collection<Student> actual2 = studentService.getByAge(10);

        assertEquals(expected, actual);
        assertEquals(expected2, actual2);
    }

    @Test
    void updateTest() {
        Student expected = new Student("Ivan", 60);
        expected.setId(1L);

        Student actual = studentService.update(1L, new Student("Ivan", 60));

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnExceptionForUpdating() {
        assertThrows(StudentNotFoundException.class, () -> studentService.update(3L, new Student("Ivan", 60)));
        StudentNotFoundException thrown = Assertions.assertThrows(StudentNotFoundException.class, () -> {
            studentService.get(3L);
        }, "Студент с id=3 не найден");
    }

    @Test
    void deleteTest() {
        Student expected = new Student("Sergey", 30);
        expected.setId(2L);

        Student actual = studentService.delete(2L);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnExceptionForDeleting() {
        assertThrows(StudentNotFoundException.class, () -> studentService.update(3L, new Student("Ivan", 60)));
        StudentNotFoundException thrown = Assertions.assertThrows(StudentNotFoundException.class, () -> {
            studentService.get(3L);
        }, "Студент с id=3 не найден");
    }

    @Test
    void getAll() {
        Map<Long, Student> studentMap = new HashMap<>();

        Student student = new Student("Alex", 40);
        student.setId(1L);
        studentMap.put(student.getId(), student);

        Student student2 = new Student("Sergey", 30);
        student2.setId(2L);
        studentMap.put(student2.getId(), student2);

        Collection<Student> expected = Collections.unmodifiableCollection(studentMap.values());

        Collection<Student> actual = studentService.getAll();

        assertArrayEquals(expected.toArray(), actual.toArray());

    }
}