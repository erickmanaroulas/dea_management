package br.com.dea.management.employee.create;

import br.com.dea.management.employee.EmployeeType;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.position.domain.Position;
import br.com.dea.management.position.repository.PositionRepository;
import br.com.dea.management.student.domain.Student;
import br.com.dea.management.student.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmployeeCreationSuccessCaseTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    void whenRequestingEmployeeCreationWithAValidPayload_thenCreateAnEmployeeSuccessfully() throws Exception {
        this.employeeRepository.deleteAll();
        this.positionRepository.deleteAll();

        Position positionTest = Position.builder()
                .description("resident developer")
                .seniority("resident")
                .build();

        this.positionRepository.save(positionTest);

        String positionId = this.positionRepository.findAll().get(0).getId().toString();

        String payload = "{" +
                "\"name\": \"name\"," +
                "\"email\": \"email@email.com\"," +
                "\"linkedin\": \"linkedin\"," +
                "\"password\": \"password\"," +
                "\"employeeType\": \"RESIDENT\"," +
                "\"position\": \"" + positionId + "\"" +
                "}";
        mockMvc.perform(post("/employee")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isOk());

        Employee employee = this.employeeRepository.findAll().get(0);

        assertThat(employee.getUser().getName()).isEqualTo("name");
        assertThat(employee.getUser().getEmail()).isEqualTo("email@email.com");
        assertThat(employee.getUser().getLinkedin()).isEqualTo("linkedin");
        assertThat(employee.getUser().getPassword()).isEqualTo("password");
        assertThat(employee.getEmployeeType()).isEqualTo(EmployeeType.RESIDENT);

        Position positionSut = employee.getPosition();

        assertThat(positionSut.getId()).isEqualTo(positionTest.getId());
        assertThat(positionSut.getDescription()).isEqualTo(positionTest.getDescription());
        assertThat(positionSut.getSeniority()).isEqualTo(positionTest.getSeniority());
    }
}