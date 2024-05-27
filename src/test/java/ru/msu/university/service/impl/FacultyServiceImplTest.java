package ru.msu.university.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.msu.university.exceptions.FacultyNotFoundException;
import ru.msu.university.exceptions.StudentNotFoundException;
import ru.msu.university.model.Faculty;
import ru.msu.university.model.Student;
import ru.msu.university.service.FacultyService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static ru.msu.university.service.impl.ConstantsForTests.*;

class FacultyServiceImplTest {

    private final FacultyService facultyService = new FacultyServiceImpl();

    @BeforeEach
    void setUp() {
        facultyService.add(BIOLOGY);
        facultyService.add(MATHEMATICS);
    }
    @Test
    void addTest() {

        Faculty actual = facultyService.add(ECONOMICS);
        Faculty actual2 = facultyService.add(CHEMICAL);

        assertEquals(ECONOMICS_EXPECTED, actual);
        assertEquals(CHEMICAL_EXPECTED, actual2);
    }

    @Test
    void getTest() {

        Faculty actual = facultyService.get(2L);

        assertEquals(MATHEMATICS_EXPECTED, actual);
    }

    @Test
    void shouldReturnExceptionForGetting() {

        assertThrows(FacultyNotFoundException.class, () -> facultyService.get(3L));
        FacultyNotFoundException thrown = Assertions.assertThrows(FacultyNotFoundException.class, () -> {
            facultyService.get(3L);
        }, "Факультет с id=3 не найден");
    }

    @Test
    void getByColor() {

        Collection<Faculty> expected = new ArrayList<>();
        Faculty faculty = MATHEMATICS_EXPECTED;
        expected.add(faculty);

        Collection<Student> expected2 = new ArrayList<>();

        Collection<Faculty> actual = facultyService.getByColor("Grey");
        Collection<Faculty> actual2 = facultyService.getByColor("Black");

        assertEquals(expected, actual);
        assertEquals(expected2, actual2);
    }

    @Test
    void updateTest() {

        Faculty actual = facultyService.update(1L, PHILOLOGY);

        assertEquals(PHILOLOGY_EXPECTED, actual);
    }

    @Test
    void shouldReturnExceptionForUpdating() {

        assertThrows(FacultyNotFoundException.class, () -> facultyService.update(3L, CHEMICAL));
        FacultyNotFoundException thrown = Assertions.assertThrows(FacultyNotFoundException.class, () -> {
            facultyService.get(3L);
        }, "Факультет с id=3 не найден");
    }

    @Test
    void deleteTest() {

        Faculty actual = facultyService.delete(2L);
        assertEquals(MATHEMATICS_EXPECTED, actual);
    }

    @Test
    void shouldReturnExceptionForDeleting() {

        assertThrows(FacultyNotFoundException.class, () -> facultyService.update(3L, CHEMICAL));
        FacultyNotFoundException thrown = Assertions.assertThrows(FacultyNotFoundException.class, () -> {
            facultyService.get(3L);
        }, "Факультет с id=3 не найден");
    }

    @Test
    void getAll() {

        Map<Long, Faculty> facultyMap = new HashMap<>();
        facultyMap.put(ALEX_EXPECTED.getId(), BIOLOGY_EXPECTED);
        facultyMap.put(SERGEY_EXPECTED.getId(), MATHEMATICS_EXPECTED);
        Collection<Faculty> expected = Collections.unmodifiableCollection(facultyMap.values());

        Collection<Faculty> actual = facultyService.getAll();

        assertArrayEquals(expected.toArray(), actual.toArray());
    }
}