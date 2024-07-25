package ru.msu.university.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.msu.university.ConstantsForTests.BIOLOGY;
import static ru.msu.university.ConstantsForTests.CHEMICAL;
import static ru.msu.university.ConstantsForTests.ECONOMICS;
import static ru.msu.university.ConstantsForTests.MARIIA;
import static ru.msu.university.ConstantsForTests.MATHEMATICS;
import static ru.msu.university.ConstantsForTests.PHILOLOGY;
import static ru.msu.university.ConstantsForTests.TATYANA;

import java.util.Collection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.msu.university.entities.Faculty;
import ru.msu.university.entities.Student;
import ru.msu.university.service.FacultyService;
import ru.msu.university.service.StudentService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private StudentService studentService;

    private String url;

    @BeforeEach
    void setUp() {
        url = "http://localhost:" + port + "/faculty";
    }

    @AfterEach
    void tearDown() {
        Collection<Student> students = studentService.getAll();
        students.forEach(student -> studentService.delete(student.getId()));
        Collection<Faculty> faculties = facultyService.getAll();
        faculties.forEach(faculty -> {
            facultyService.delete(faculty.getId());
        });

    }

    @Test
    void contextLoads() {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void addTest() {
        Faculty actual = testRestTemplate.postForObject(url, BIOLOGY, Faculty.class);
        BIOLOGY.setId(actual.getId());

        assertThat(actual).isEqualTo(BIOLOGY);
    }

    @Test
    void getAllTest() {
        Faculty added = testRestTemplate.postForObject(url, PHILOLOGY, Faculty.class);
        String actual = testRestTemplate.getForObject(url, String.class);

        assertThat(actual).contains(
                "[",
                "]",
                String.format("{\"id\":%d,\"name\":\"%s\",\"color\":\"%s\"}", added.getId(), added.getName(), added.getColor())
        );
    }

    @Test
    void getByIdTest() {
        Faculty added = testRestTemplate.postForObject(url, MATHEMATICS, Faculty.class);
        String actual = testRestTemplate.getForObject(url + "?id=" + added.getId(), String.class);

        assertThat(actual).isEqualTo(String.format("{\"id\":%d,\"name\":\"%s\",\"color\":\"%s\"}", added.getId(), added.getName(), added.getColor()));
    }

    @Test
    void getByNameTest() {
        Faculty added = testRestTemplate.postForObject(url, ECONOMICS, Faculty.class);
        String actual = testRestTemplate.getForObject(url + "?name=" + added.getName(), String.class);

        assertThat(actual).contains(
                "[",
                "]",
                String.format("{\"id\":%d,\"name\":\"%s\",\"color\":\"%s\"}", added.getId(), added.getName(), added.getColor()));
    }

    @Test
    void getByColorTest() {
        Faculty added = testRestTemplate.postForObject(url, ECONOMICS, Faculty.class);
        String actual = testRestTemplate.getForObject(url + "?color=" + added.getColor(), String.class);

        assertThat(actual).contains(
                "[",
                "]",
                String.format("{\"id\":%d,\"name\":\"%s\",\"color\":\"%s\"}", added.getId(), added.getName(), added.getColor()));
    }

    @Test
    void getByNameOrColorTest() {
        Faculty added = testRestTemplate.postForObject(url, ECONOMICS, Faculty.class);
        String subColor = added.getColor().substring(0, 2);
        String actual = testRestTemplate.getForObject(url + "?nameOrColor=" + subColor, String.class);
        String subName = added.getName().substring(0, 2);
        String actual2 = testRestTemplate.getForObject(url + "?nameOrColor=" + subName, String.class);

        assertThat(actual).contains(
                "[",
                "]",
                String.format("{\"id\":%d,\"name\":\"%s\",\"color\":\"%s\"}", added.getId(), added.getName(), added.getColor()));

        assertThat(actual2).contains(
                "[",
                "]",
                String.format("{\"id\":%d,\"name\":\"%s\",\"color\":\"%s\"}", added.getId(), added.getName(), added.getColor()));
    }

    @Test
    void getStudentByFacultyTest() {
        Faculty addedFaculty = testRestTemplate.postForObject(url, PHILOLOGY, Faculty.class);
        MARIIA.setFaculty(addedFaculty);
        Student addedStudent = testRestTemplate.postForObject("http://localhost:" + port + "/student", MARIIA, Student.class);

        String actual = testRestTemplate.getForObject(url + "/" + addedStudent.getFaculty().getId() + "/students", String.class);

        assertThat(actual).contains(
                "[",
                "]",
                String.format("{\"id\":%d,\"name\":\"%s\",\"age\":%d,\"faculty\":{\"id\":%d,\"name\":", addedStudent.getId(), addedStudent.getName(), addedStudent.getAge(), addedStudent.getFaculty().getId()));
    }

    @Test
    void shouldReturnStudentNotFoundException() {
        Faculty updated = new Faculty(1L, "ADS", "ads");
        ResponseEntity<String> response = testRestTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(updated),
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertEquals(response.getBody(), "Факультет с id=1 не найден");
    }

    @Test
    void updateTest() {
        ResponseEntity<Faculty> response = testRestTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(CHEMICAL),
                Faculty.class
        );
        BIOLOGY.setId(response.getBody().getId());

        ResponseEntity<Faculty> actual = testRestTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(BIOLOGY),
                Faculty.class
        );

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isEqualTo(BIOLOGY);
    }

    @Test
    void deleteTest() {
        ResponseEntity<Faculty> response = testRestTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(TATYANA),
                Faculty.class
        );
        Long id = response.getBody().getId();

        testRestTemplate.delete(url + "?id=" + id);
        String actual = testRestTemplate.getForObject(url + "?id=" + id, String.class);

        assertThat(actual).contains("Факультет с id=" + id + " не найден");
    }
}
