package ru.msu.university.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.msu.university.exceptions.StudentNotFoundException;
import ru.msu.university.model.Student;
import ru.msu.university.service.StudentService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static ru.msu.university.service.impl.ConstantsForTests.*;

class StudentServiceImplTest {

    private final StudentService studentService;

    public StudentServiceImplTest(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @BeforeEach
    void setUp() {

        studentService.add(ALEX);
        studentService.add(SERGEY);
    }

    @Test
    void addTest() {

        Student actual = studentService.add(MARIIA);
        Student actual2 = studentService.add(TATYANA);

        assertEquals(MARIIA_EXPECTED, actual);
        assertEquals(TATYANA_EXPECTED, actual2);
    }

    @Test
    void getTest() {

        Student actual = studentService.get(2L);

        assertEquals(SERGEY_EXPECTED, actual);
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
        Student student = SERGEY_EXPECTED;
        expected.add(student);

        Collection<Student> expected2 = new ArrayList<>();

        Collection<Student> actual = studentService.getByAge(30);
        Collection<Student> actual2 = studentService.getByAge(10);

        assertEquals(expected, actual);
        assertEquals(expected2, actual2);
    }

    @Test
    void updateTest() {

        Student actual = studentService.update(IVAN);

        assertEquals(IVAN_EXPECTED, actual);
    }

    @Test
    void shouldReturnExceptionForUpdating() {

        assertThrows(StudentNotFoundException.class, () -> studentService.update(IVAN));
        StudentNotFoundException thrown = Assertions.assertThrows(StudentNotFoundException.class, () -> {
            studentService.get(3L);
        }, "Студент с id=3 не найден");
    }

    @Test
    void deleteTest() {

        Student actual = studentService.delete(2L);

        assertEquals(SERGEY_EXPECTED, actual);
    }

    @Test
    void shouldReturnExceptionForDeleting() {

        assertThrows(StudentNotFoundException.class, () -> studentService.update(IVAN));
        StudentNotFoundException thrown = Assertions.assertThrows(StudentNotFoundException.class, () -> {
            studentService.get(3L);
        }, "Студент с id=3 не найден");
    }

    @Test
    void getAll() {

        Map<Long, Student> studentMap = new HashMap<>();
        studentMap.put(ALEX_EXPECTED.getId(), ALEX_EXPECTED);
        studentMap.put(SERGEY_EXPECTED.getId(), SERGEY_EXPECTED);
        Collection<Student> expected = Collections.unmodifiableCollection(studentMap.values());

        Collection<Student> actual = studentService.getAll();

        assertArrayEquals(expected.toArray(), actual.toArray());
    }
}