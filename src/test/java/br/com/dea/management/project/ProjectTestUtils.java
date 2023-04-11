package br.com.dea.management.project;

import br.com.dea.management.employee.EmployeeTestUtils;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.project.domain.Project;
import br.com.dea.management.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ProjectTestUtils {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeTestUtils employeeTestUtils;

    public void createFakeProjects(int amount) {

        this.employeeTestUtils.createFakeEmployees(2);

        Employee productOwner = this.employeeRepository.findAll().get(0);
        Employee scrumMaster = this.employeeRepository.findAll().get(1);

        for (int i = 0; i < amount; i++) {
            Project project = Project.builder()
                    .name("project " + i)
                    .startDate(LocalDate.now().minusMonths(i))
                    .endDate(LocalDate.now().plusMonths(i))
                    .client("client " + i)
                    .productOwner(productOwner)
                    .scrumMaster(scrumMaster)
                    .externalProductManager("external product manager " + i)
                    .build();

            this.projectRepository.save(project);
        }
    }

}
