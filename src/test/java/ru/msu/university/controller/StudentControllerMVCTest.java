package ru.msu.university.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.msu.university.entities.Faculty;
import ru.msu.university.entities.Student;
import ru.msu.university.repositories.AvatarRepository;
import ru.msu.university.repositories.FacultyRepository;
import ru.msu.university.repositories.StudentRepository;
import ru.msu.university.service.impl.AvatarServiceImpl;
import ru.msu.university.service.impl.FacultyServiceImpl;
import ru.msu.university.service.impl.StudentServiceImpl;

import java.util.Optional;

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

        Long id = 1l;
        String name = "Olga";
        int age = 18;
        Faculty faculty = new Faculty(1L, "Technic", "Blue");
        Student student = new Student(id, name, age);
        student.setFaculty(faculty);

        JSONObject jsonFaculty = new JSONObject();
        jsonFaculty.put("id", 1L);
        jsonFaculty.put("name", "Economics");
        jsonFaculty.put("color", "Red");
        JSONObject jsonStudent = new JSONObject();
        jsonStudent.put("id", id);
        jsonStudent.put("name", name);
        jsonStudent.put("age", age);
        jsonStudent.put("faculty", jsonFaculty);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost/student")
                        .content(jsonStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.faculty").value(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost/student?id=" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.faculty").value(faculty));
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

































}
