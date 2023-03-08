package br.com.dea.management.student.get;

import br.com.dea.management.student.StudentTestUtils;
import br.com.dea.management.student.domain.Student;
import br.com.dea.management.student.repository.StudentRepository;
import br.com.dea.management.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class StudentGetAllTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentTestUtils studentTestUtils;

    @BeforeEach
    void beforeEach() {
        log.info("Before each test in " + StudentGetAllTests.class.getSimpleName());
    }

    @BeforeAll
    void beforeSuiteTest() {
        log.info("Before all tests in " + StudentGetAllTests.class.getSimpleName());
    }

    @Test
    void whenRequestingStudentList_thenReturnListOfStudentPaginatedSuccessfully() throws Exception {
        this.studentRepository.deleteAll();
        this.studentTestUtils.createFakeStudents(100);

        mockMvc.perform(get("/student?page=0&pageSize=4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(4)))
                .andExpect(jsonPath("$.content[0].name", is("name 0")))
                .andExpect(jsonPath("$.content[0].email", is("email 0")))
                .andExpect(jsonPath("$.content[0].linkedin", is("linkedin 0")))
                .andExpect(jsonPath("$.content[0].university", is("university 0")))
                .andExpect(jsonPath("$.content[0].graduation", is("graduation 0")))
                .andExpect(jsonPath("$.content[1].name", is("name 1")))
                .andExpect(jsonPath("$.content[1].email", is("email 1")))
                .andExpect(jsonPath("$.content[1].linkedin", is("linkedin 1")))
                .andExpect(jsonPath("$.content[1].university", is("university 1")))
                .andExpect(jsonPath("$.content[1].graduation", is("graduation 1")))
                .andExpect(jsonPath("$.content[2].name", is("name 10")))
                .andExpect(jsonPath("$.content[2].email", is("email 10")))
                .andExpect(jsonPath("$.content[2].linkedin", is("linkedin 10")))
                .andExpect(jsonPath("$.content[2].university", is("university 10")))
                .andExpect(jsonPath("$.content[2].graduation", is("graduation 10")))
                .andExpect(jsonPath("$.content[3].name", is("name 11")))
                .andExpect(jsonPath("$.content[3].email", is("email 11")))
                .andExpect(jsonPath("$.content[3].linkedin", is("linkedin 11")))
                .andExpect(jsonPath("$.content[3].university", is("university 11")))
                .andExpect(jsonPath("$.content[3].graduation", is("graduation 11")));

    }

    @Test
    void whenRequestingStudentListAndPageQueryParamIsInvalid_thenReturnBadRequestError() throws Exception {
        mockMvc.perform(get("/student?page=xx&pageSize=4"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenRequestingStudentListAndPageQueryParamIsMissing_thenReturnBadRequestError() throws Exception {
        mockMvc.perform(get("/student?pageSize=4"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenRequestingStudentListAndPageSizeQueryParamIsInvalid_thenReturnBadRequestError() throws Exception {
        mockMvc.perform(get("/student?pageSize=xx&page=4"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenRequestingStudentListAndPageSizeQueryParamIsMissing_thenReturnBadRequestError() throws Exception {
        mockMvc.perform(get("/student?page=0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

}