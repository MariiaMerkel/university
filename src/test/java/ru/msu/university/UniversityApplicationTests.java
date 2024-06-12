package ru.msu.university;

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
import ru.msu.university.controller.StudentController;
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

@WebMvcTest
@ActiveProfiles("home")
class UniversityApplicationTests {

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
    void saveStudentTest() throws Exception {

        long id = 1;
        String name = "Olga";
        int age = 18;

        String facultyName = "Technic";
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName(facultyName);
        faculty.setColor("Blue");

        JSONObject jsonStudent = new JSONObject();
        jsonStudent.put("id", id);
        jsonStudent.put("name", name);
        jsonStudent.put("age", age);
        jsonStudent.put("faculty", faculty);

        Student student = new Student(id, name, age);
        student.setFaculty(faculty);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost/students")
//                        .content(jsonStudent.toString())
                        .content("{\"id\":1,\"name\":\"Olga\",\"age\":18,\"faculty\":{\"id\"=1, \"name\"=\"Technic\", \"color\"=\"Blue\"}}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost/student?id=" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.faculty.id").value(faculty.getId()));
    }

}
