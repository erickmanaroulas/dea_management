package br.com.dea.management.employee.update;

import br.com.dea.management.employee.EmployeeTestUtils;
import br.com.dea.management.employee.EmployeeType;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.position.domain.Position;
import br.com.dea.management.position.repository.PositionRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmployeeUpdateSuccessCaseTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private EmployeeTestUtils employeeTestUtils;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    void whenRequestingEmployeeUpdateWithAValidPayload_thenUpdateAnEmployeeSuccessfully() throws Exception {
        this.employeeRepository.deleteAll();
        this.positionRepository.deleteAll();

        employeeTestUtils.createFakeEmployees(1);

        Employee employee = employeeRepository.findAll().get(0);
        Position positionTest = positionRepository.findAll().get(0);

        Position positionSut = Position.builder()
                .description("resident developer")
                .seniority("resident")
                .build();

        this.positionRepository.save(positionTest);

        String payload = "{" +
                "\"name\": \"update\"," +
                "\"email\": \"email@email.com\"," +
                "\"linkedin\": \"linkedin\"," +
                "\"password\": \"password\"," +
                "\"employeeType\": \"RESIDENT\"," +
                "\"position\": \"" + employee.getPosition().toString() + "\"" +
                "}";

        mockMvc.perform(put("/employee/" + employee.getId())
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isOk());

        Employee sut = this.employeeRepository.findAll().get(0);

        assertThat(sut.getUser().getName()).isEqualTo("name");
        assertThat(sut.getUser().getEmail()).isEqualTo("email@email.com");
        assertThat(sut.getUser().getLinkedin()).isEqualTo("linkedin");
        assertThat(sut.getUser().getPassword()).isEqualTo("password");
        assertThat(sut.getEmployeeType()).isEqualTo(EmployeeType.RESIDENT);

        assertThat(positionSut.getId()).isEqualTo(sut.getPosition());
    }
}