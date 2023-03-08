package br.com.dea.management.user;

import br.com.dea.management.student.get.StudentGetAllTests;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class UserGetByIdTests {
    private final String route = "/user/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        log.info("Before each test in " + UserGetByIdTests.class.getSimpleName());
    }

    @BeforeAll
    void beforeSuiteTest() {
        log.info("Before all tests in " + UserGetByIdTests.class.getSimpleName());
        this.userRepository.deleteAll();
    }

    @AfterAll
    void afterSuiteTest() {
        log.info("After all tests in " + StudentGetAllTests.class.getSimpleName());
        this.userRepository.deleteAll();
    }

    @Test
    void whenRequestingAnExistentUserById_thenReturnTheUserSuccessfully() throws Exception {
        this.userRepository.deleteAll();
        this.createFakeUsers(10);

        User user = this.userRepository.findAll().get(0);

        mockMvc.perform(get(route + user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.linkedin", is(user.getLinkedin())));
    }

    @Test
    void whenRequestingByIdAndIdIsNotANumber_thenReturnTheBadRequestError() throws Exception {

        mockMvc.perform(get(route + "xx"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenRequestingAnNonExistentStudentById_thenReturnTheNotFoundError() throws Exception {

        mockMvc.perform(get(route + "5000"))
                .andExpect(status().isNotFound())
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
