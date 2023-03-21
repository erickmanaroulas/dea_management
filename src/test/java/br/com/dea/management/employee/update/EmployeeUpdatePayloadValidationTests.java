package br.com.dea.management.employee.update;

import br.com.dea.management.employee.EmployeeTestUtils;
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

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmployeeUpdatePayloadValidationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeTestUtils employeeTestUtils;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    void whenPayloadHasRequiredFieldsAreMissing_thenReturn400AndTheErrors() throws Exception {
        this.employeeRepository.deleteAll();
        this.positionRepository.deleteAll();

        employeeTestUtils.createFakeEmployees(1);

        Employee employee = employeeRepository.findAll().get(0);

        String payload = "{}";
        mockMvc.perform(put("/employee/" + employee.getId())
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(5)))
                .andExpect(jsonPath("$.details[*].field", hasItem("name")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Name could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("email")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Email could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("password")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Password could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("position")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Position could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("employeeType")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Employee Type could not be null")));
    }

    @Test
    void whenPayloadHasInvalidPositionId_thenReturn404AndTheErrors() throws Exception {
        this.employeeRepository.deleteAll();
        this.positionRepository.deleteAll();

        employeeTestUtils.createFakeEmployees(1);

        Employee employee = employeeRepository.findAll().get(0);
        Long positionId = employee.getPosition().getId() + 1;

        String payload = "{" +
                "\"name\": \"name\"," +
                "\"email\": \"email@email.com\"," +
                "\"linkedin\": \"linkedin\"," +
                "\"password\": \"password\"," +
                "\"employeeType\": \"RESIDENT\"," +
                "\"position\": \"" + positionId.toString() + "\"" +
                "}";
        mockMvc.perform(put("/employee/" + employee.getId())
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenPayloadHasInvalidEmployeeId_thenReturn404AndTheErrors() throws Exception {
        this.employeeRepository.deleteAll();
        this.positionRepository.deleteAll();

        employeeTestUtils.createFakeEmployees(1);

        Employee employee =  employeeRepository.findAll().get(0);
        Long employeeId = employee.getId() + 1;
        Long positionId = employee.getPosition().getId();

        String payload = "{" +
                "\"name\": \"name\"," +
                "\"email\": \"email@email.com\"," +
                "\"linkedin\": \"linkedin\"," +
                "\"password\": \"password\"," +
                "\"employeeType\": \"RESIDENT\"," +
                "\"position\": \"" + positionId.toString() + "\"" +
                "}";
        mockMvc.perform(put("/employee/" + employeeId)
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

}