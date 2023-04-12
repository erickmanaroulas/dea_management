package br.com.dea.management.project.update;

import br.com.dea.management.academyclass.AcademyClassTestUtils;
import br.com.dea.management.academyclass.ClassType;
import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.academyclass.repository.AcademyClassRepository;
import br.com.dea.management.employee.EmployeeTestUtils;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.project.ProjectTestUtils;
import br.com.dea.management.project.domain.Project;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProjectUpdateSuccessCaseTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectTestUtils projectTestUtils;


    @Autowired
    private EmployeeTestUtils employeeTestUtils;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Test
    void whenRequestingProjectUpdateWithAValidPayload_thenUpdateAProjectSuccessfully() throws Exception {
        this.projectRepository.deleteAll();
        this.employeeRepository.deleteAll();

        this.employeeTestUtils.createFakeEmployees(2);

        Long productOwnerId = this.employeeRepository.findAll().get(0).getId();
        Long scrumMasterId = this.employeeRepository.findAll().get(1).getId();

        LocalDate startDate = LocalDate.of(2023, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(2024, Month.DECEMBER, 20);

        this.projectTestUtils.createFakeProjects(1);
        Long projectId = this.projectRepository.findAll().get(0).getId();

        String payload = "{" +
                "\"name\": \"New Project\"," +
                "\"startDate\": \"" + startDate + "\"," +
                "\"endDate\": \"" + endDate + "\"," +
                "\"client\": \"Claudio\"," +
                "\"productOwner\": \"" + productOwnerId + "\"," +
                "\"scrumMaster\": \"" + scrumMasterId + "\"," +
                "\"externalProductManager\": \"Cid\"" +
                "}";

        mockMvc.perform(put("/project/" + projectId)
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isOk());

        Project project = this.projectRepository.findAll().get(0);

        assertThat(project.getName()).isEqualTo("New Project");
        assertThat(project.getStartDate()).isEqualTo(startDate);
        assertThat(project.getEndDate()).isEqualTo(endDate);
        assertThat(project.getClient()).isEqualTo("Claudio");
        assertThat(project.getProductOwner().getId()).isEqualTo(productOwnerId);
        assertThat(project.getScrumMaster().getId()).isEqualTo(scrumMasterId);
        assertThat(project.getExternalProductManager()).isEqualTo("Cid");
    }
}