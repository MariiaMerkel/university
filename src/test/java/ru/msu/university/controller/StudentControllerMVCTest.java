package ru.msu.university.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.msu.university.entities.Student;
import ru.msu.university.exceptions.StudentNotFoundException;
import ru.msu.university.repositories.AvatarRepository;
import ru.msu.university.repositories.FacultyRepository;
import ru.msu.university.repositories.StudentRepository;
import ru.msu.university.service.impl.AvatarServiceImpl;
import ru.msu.university.service.impl.FacultyServiceImpl;
import ru.msu.university.service.impl.StudentServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.msu.university.service.impl.ConstantsForTests.*;

@WebMvcTest
@ActiveProfiles("home")
class StudentControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentServiceImpl studentService;

    @SpyBean
    private AvatarServiceImpl avatarService;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void addTest() throws Exception {
        OLGA.setFaculty(POLYTECHNIC);
        String jsonStudent = getJsonObjectStudent();

        when(studentRepository.save(any(Student.class))).thenReturn(OLGA);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(OLGA));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost/student")
                        .content(jsonStudent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(OLGA.getId()))
                .andExpect(jsonPath("$.name").value(OLGA.getName()))
                .andExpect(jsonPath("$.age").value(OLGA.getAge()))
                .andExpect(jsonPath("$.faculty").value(OLGA.getFaculty()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost/student?id=" + OLGA.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(OLGA.getId()))
                .andExpect(jsonPath("$.name").value(OLGA.getName()))
                .andExpect(jsonPath("$.age").value(OLGA.getAge()))
                .andExpect(jsonPath("$.faculty").value(OLGA.getFaculty()));
    }

    @Test
    void getAll() throws Exception {
        when(studentRepository.findAll()).thenReturn(STUDENTS);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(STUDENTS.get(0)))
                .andExpect(jsonPath("$[1]").value(STUDENTS.get(1)))
                .andExpect(jsonPath("$[2]").value(STUDENTS.get(2)))
                .andExpect(jsonPath("$[3]").value(STUDENTS.get(3)))
                .andExpect(jsonPath("$[4]").value(STUDENTS.get(4)));
    }

    @Test
    void getByIdTest() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(ALEX));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost/student?id=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ALEX.getId()))
                .andExpect(jsonPath("$.name").value(ALEX.getName()))
                .andExpect(jsonPath("$.age").value(ALEX.getAge()))
                .andExpect(jsonPath("$.faculty").value(ALEX.getFaculty()));
    }

    @Test
    void shouldReturnNotFoundById() throws Exception {
        Long id = 77L;
        List<Student> studentList = STUDENTS.stream().filter(s -> s.getId() == (id)).toList();

        when(studentRepository.findByNameContainsIgnoreCase(any(String.class))).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost/student?id=" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getByNameTest() throws Exception {
        String substring = "mAr";
        List<Student> studentList = STUDENTS.stream().filter(s -> s.getName().toLowerCase().contains(substring.toLowerCase())).toList();

        when(studentRepository.findByNameContainsIgnoreCase(any(String.class))).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost/student?name=" + substring)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(jsonPath("$[0]").value(MARIIA));
    }

    @Test
    void shouldReturnNotFoundByName() throws Exception {
        String substring = "rrr";
        List<Student> studentList = STUDENTS.stream().filter(s -> s.getName().toLowerCase().contains(substring.toLowerCase())).toList();

        when(studentRepository.findByNameContainsIgnoreCase(any(String.class))).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost/student?name=" + substring)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getByAgeTest() throws Exception {
        int age = 35;
        List<Student> studentList = STUDENTS.stream().filter(s -> s.getAge() == age).toList();

        when(studentRepository.findByAge(any(Integer.class))).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost/student?age=" + age)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(jsonPath("$[0]").value(MARIIA));
    }

    @Test
    void shouldReturnNotFoundByAge() throws Exception {
        int age = 51;
        List<Student> studentList = STUDENTS.stream().filter(s -> s.getAge() == age).toList();

        when(studentRepository.findByAge(any(Integer.class))).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost/student?age=" + age)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getByAgeBetweenTest() throws Exception {
        int minAge = 40;
        int maxAge = 50;
        List<Student> studentList = STUDENTS.stream().filter(s -> s.getAge() >= minAge && s.getAge() <= maxAge).toList();

        when(studentRepository.findByAgeBetween(any(Integer.class), any(Integer.class))).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost/student?minAge=" + minAge + "&maxAge=" + maxAge)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(studentList.get(0)))
                .andExpect(jsonPath("$[1]").value(studentList.get(1)));
    }

    @Test
    void shouldReturnNotFoundByAgeBetween() throws Exception {
        int minAge = 10;
        int maxAge = 15;
        List<Student> studentList = STUDENTS.stream().filter(s -> s.getAge() >= minAge && s.getAge() <= maxAge).toList();

        when(studentRepository.findByAgeBetween(any(Integer.class), any(Integer.class))).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost/student")
                        .param("minAge",String.valueOf(minAge))
                        .param("maxAge", String.valueOf(maxAge))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @MethodSource("provideParamsForShouldReturnBadRequest")
    void shouldReturnBadRequestByAgeBetween(String minAge, String maxAge, String expected) throws Exception {
        String url = "http://localhost/student?";

        if (minAge != null && maxAge != null) {
            url = url + minAge + "&" + maxAge;
        }
        if (minAge == null && maxAge != null) {
            url = url + maxAge;
        }
        if (minAge != null && maxAge == null) {
            url = url + minAge;
        }

        when(studentRepository.findByAgeBetween(any(Integer.class), any(Integer.class))).thenReturn(STUDENTS);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(url)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    public static Stream<Arguments> provideParamsForShouldReturnBadRequest() {
        return Stream.of(
                Arguments.of(null, "maxAge=30", "Один из параметров введён не корректно"),
                Arguments.of("minAge=30", null, "Один из параметров введён не корректно"),
                Arguments.of("minAge=30", "maxAge=-5", "Один из параметров введён не корректно"),
                Arguments.of("minAge=-5", "maxAge=30", "Один из параметров введён не корректно"),
                Arguments.of(null, "maxAge=-5", "Один из параметров введён не корректно"),
                Arguments.of("minAge=-5", null, "Один из параметров введён не корректно")
        );
    }

    @Test
    void updateTest() throws Exception {
        OLGA.setFaculty(POLYTECHNIC);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(OLGA));
        when(studentRepository.save(any(Student.class))).thenReturn(OLGA);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("http://localhost/student")
                        .content(getJsonObjectStudent())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(OLGA.getId()))
                .andExpect(jsonPath("$.name").value(OLGA.getName()))
                .andExpect(jsonPath("$.age").value(OLGA.getAge()))
                .andExpect(jsonPath("$.faculty").value(OLGA.getFaculty()));
    }

    @Test
    void shouldReturnNotFoundExceptionByUpdate() throws Exception {
        OLGA.setFaculty(POLYTECHNIC);

        when(studentRepository.save(any(Student.class))).thenThrow(StudentNotFoundException.class);

        String jsonStudent = getJsonObjectStudent();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost/student")
                        .content(jsonStudent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getFacultyOfStudent() throws Exception{
        OLGA.setFaculty(POLYTECHNIC);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(OLGA));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost/student/1/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(POLYTECHNIC.getId()))
                .andExpect(jsonPath("$.name").value(POLYTECHNIC.getName()))
                .andExpect(jsonPath("$.color").value(POLYTECHNIC.getColor()));
    }

    private String getJsonObjectStudent() throws JSONException {
        JSONObject jsonFaculty = new JSONObject();
        jsonFaculty.put("id", OLGA.getFaculty().getId());
        jsonFaculty.put("name", OLGA.getFaculty().getName());
        jsonFaculty.put("color", OLGA.getFaculty().getColor());
        JSONObject jsonStudent = new JSONObject();
        jsonStudent.put("id", OLGA.getId());
        jsonStudent.put("name", OLGA.getName());
        jsonStudent.put("age", OLGA.getAge());
        jsonStudent.put("faculty", jsonFaculty);

        return jsonStudent.toString();
    }


}
