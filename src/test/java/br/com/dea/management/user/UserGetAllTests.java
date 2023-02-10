package br.com.dea.management.user;

import br.com.dea.management.student.StudentGetAllTests;
import br.com.dea.management.student.domain.Student;
import br.com.dea.management.student.repository.StudentRepository;
import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
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
public class UserGetAllTests {
    private final String route = "/user/all";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void beforeEach() {
        log.info("Before each test in " + UserGetAllTests.class.getSimpleName());
    }

    @BeforeAll
    void beforeSuiteTest() {
        log.info("Before all tests in " + UserGetAllTests.class.getSimpleName());
//        this.userRepository.deleteAll();
    }

    @AfterAll
    void afterSuiteTest() {
        log.info("After all tests in " + StudentGetAllTests.class.getSimpleName());
        this.userRepository.deleteAll();
    }

    @Test
    void whenRequestingStudentList_thenReturnListOfStudentPaginatedSuccessfully() throws Exception {
        this.createFakeUsers(100);

        mockMvc.perform(get(route + "?page=0&pageSize=12"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(12)))
                .andExpect(jsonPath("$.content[0].name", is("name 0")))
                .andExpect(jsonPath("$.content[0].email", is("email 0")))
                .andExpect(jsonPath("$.content[0].linkedin", is("linkedin 0")))
                .andExpect(jsonPath("$.content[1].name", is("name 1")))
                .andExpect(jsonPath("$.content[1].email", is("email 1")))
                .andExpect(jsonPath("$.content[1].linkedin", is("linkedin 1")))
                .andExpect(jsonPath("$.content[2].name", is("name 10")))
                .andExpect(jsonPath("$.content[2].email", is("email 10")))
                .andExpect(jsonPath("$.content[2].linkedin", is("linkedin 10")))
                .andExpect(jsonPath("$.content[3].name", is("name 11")))
                .andExpect(jsonPath("$.content[3].email", is("email 11")))
                .andExpect(jsonPath("$.content[3].linkedin", is("linkedin 11")));
    }

    @Test
    void whenRequestingStudentListAndPageQueryParamIsInvalid_thenReturnBadRequestError() throws Exception {
        mockMvc.perform(get(route + "?page=xx&pageSize=4"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenRequestingStudentListAndPageQueryParamIsMissing_thenReturnBadRequestError() throws Exception {
        mockMvc.perform(get(route + "?pageSize=4"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenRequestingStudentListAndPageSizeQueryParamIsInvalid_thenReturnBadRequestError() throws Exception {
        mockMvc.perform(get(route + "?pageSize=xx&page=4"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenRequestingStudentListAndPageSizeQueryParamIsMissing_thenReturnBadRequestError() throws Exception {
        mockMvc.perform(get(route + "?page=0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    private void createFakeUsers(int amount) {
        for (int i = 0; i < amount; i++) {
            User u = User.builder()
                    .email("email " + i)
                    .name("name " + i)
                    .linkedin("linkedin " + i)
                    .password("pwd " + i)
                    .build();

            this.userRepository.save(u);
        }
    }
}
