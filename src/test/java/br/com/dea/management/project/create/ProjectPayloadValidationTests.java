package br.com.dea.management.project.create;

import br.com.dea.management.employee.EmployeeTestUtils;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.project.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProjectPayloadValidationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeTestUtils employeeTestUtils;

    @Autowired
    private ProjectRepository projectRepository;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);


    @Test
    void whenPayloadHasRequiredFieldsAreMissing_thenReturn400AndTheErrors() throws Exception {
        String payload = "{}";
        mockMvc.perform(post("/project")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(7)))
                .andExpect(jsonPath("$.details[*].field", hasItem("name")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Name could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("startDate")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Start Date could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("endDate")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("End Date could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("client")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Client could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("productOwner")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Product Owner could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("scrumMaster")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Scrum Master could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("externalProductManager")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("External Product Manager could not be null")));
    }

    @Test
    void whenPayloadHasInvalidProductOwner_thenReturn404AndTheErrors() throws Exception {
        this.employeeRepository.deleteAll();
        this.employeeTestUtils.createFakeEmployees(2);

        long productOwnerId = this.employeeRepository.findAll().get(0).getId() + 3;
        long scrumMasterId = this.employeeRepository.findAll().get(1).getId();

        LocalDate startDate = LocalDate.of(2023, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(2024, Month.DECEMBER, 20);

        String payload = "{" +
                "\"name\": \"New Project\"," +
                "\"startDate\": \"" + startDate + "\"," +
                "\"endDate\": \"" + endDate + "\"," +
                "\"client\": \"Claudio\"," +
                "\"productOwner\": \"" + productOwnerId + "\"," +
                "\"scrumMaster\": \"" + scrumMasterId + "\"," +
                "\"externalProductManager\": \"Cid\"" +
                "}";

        mockMvc.perform(post("/project")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenPayloadHasInvalidScrumMaster_thenReturn404AndTheErrors() throws Exception {
        this.employeeRepository.deleteAll();
        this.employeeTestUtils.createFakeEmployees(2);

        long productOwnerId = this.employeeRepository.findAll().get(0).getId();
        long scrumMasterId = this.employeeRepository.findAll().get(1).getId() + 3;

        LocalDate startDate = LocalDate.of(2023, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(2024, Month.DECEMBER, 20);

        String payload = "{" +
                "\"name\": \"New Project\"," +
                "\"startDate\": \"" + startDate + "\"," +
                "\"endDate\": \"" + endDate + "\"," +
                "\"client\": \"Claudio\"," +
                "\"productOwner\": \"" + productOwnerId + "\"," +
                "\"scrumMaster\": \"" + scrumMasterId + "\"," +
                "\"externalProductManager\": \"Cid\"" +
                "}";

        mockMvc.perform(post("/project")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenPayloadHasInvalidProductOwnerAndScrumMaster_thenReturn404AndTheErrors() throws Exception {
        this.employeeRepository.deleteAll();
        this.employeeTestUtils.createFakeEmployees(2);

        long productOwnerId = this.employeeRepository.findAll().get(0).getId() + 3;
        long scrumMasterId = this.employeeRepository.findAll().get(1).getId() + 3;

        LocalDate startDate = LocalDate.of(2023, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(2024, Month.DECEMBER, 20);

        String payload = "{" +
                "\"name\": \"New Project\"," +
                "\"startDate\": \"" + startDate + "\"," +
                "\"endDate\": \"" + endDate + "\"," +
                "\"client\": \"Claudio\"," +
                "\"productOwner\": \"" + productOwnerId + "\"," +
                "\"scrumMaster\": \"" + scrumMasterId + "\"," +
                "\"externalProductManager\": \"Cid\"" +
                "}";

        mockMvc.perform(post("/project")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }
}