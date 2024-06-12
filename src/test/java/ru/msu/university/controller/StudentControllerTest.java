package ru.msu.university.controller;

import org.assertj.core.api.Assertions;
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
import org.springframework.test.context.ActiveProfiles;
import ru.msu.university.entities.Student;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("home")
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String url;

    @BeforeEach
    void setUp() {
        url = "http://localhost:" + port + "/student";
    }

    @Test
    void contextLoads() {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void mainTest() {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port, String.class))
                .contains("ello")
                .isEqualTo("Hello");
    }

    @Test
    void addTest() throws Exception {

        Student expected = new Student("Pasha", 23);
        Student actual = testRestTemplate.postForObject(url, expected, Student.class);
        expected.setId(actual.getId());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getAllTest() throws Exception {

        Student student = new Student("Glasha", 100);
        Student addedStudent = testRestTemplate.postForObject(url, student, Student.class);
        student.setId(addedStudent.getId());
        String actual = testRestTemplate.getForObject(url, String.class);
        assertThat(actual).contains(
                "[",
                "]",
                "{\"id\":" + addedStudent.getId() + ",\"name\":\"Glasha\",\"age\":100,\"faculty\":null}"
        );
    }

    @Test
    void getFacultyOfStudentTest() {
        Student student = new Student("Natasha", 45);
        Student addedStudent = testRestTemplate.getForObject(url + "/2", student, Student.class);
        student.setId(addedStudent.getId());
        String actual = testRestTemplate.getForObject(url, String.class);

    }

    @Test
    void updateTest() {

        final Student updated = new Student(99L, "Rowan Atkinson", 53);

        final ResponseEntity<Student> response = testRestTemplate.exchange(
                String.format(url),
                HttpMethod.PUT,
                new HttpEntity<>(updated),
                Student.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

//        final Student updated2 = new Student(2L, "Mariia", 35);
//
//        final ResponseEntity<Student> response2 = testRestTemplate.exchange(
//                String.format("http://localhost:" + port + "/student"),
//                HttpMethod.PUT,
//                new HttpEntity<>(updated2),
//                Student.class
//        );
//
//        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);


    }

    @Test
    void deleteTest() {
    }
}