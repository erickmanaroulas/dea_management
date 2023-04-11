package br.com.dea.management.project.dto;

import br.com.dea.management.academyclass.ClassType;
import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.academyclass.dto.AcademyClassDto;
import br.com.dea.management.employee.dto.EmployeeDto;
import br.com.dea.management.project.domain.Project;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectDto {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String client;
    private EmployeeDto productOwner;
    private EmployeeDto scrumMaster;
    private String externalProductManager;

    public static List<ProjectDto> fromProject(List<Project> projects) {
        return projects.stream().map(ProjectDto::fromProject).collect(Collectors.toList());
    }

    public static ProjectDto fromProject(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setStartDate(project.getStartDate());
        projectDto.setEndDate(project.getEndDate());
        projectDto.setClient(project.getClient());
        projectDto.setProductOwner(EmployeeDto.fromEmployee(project.getProductOwner()));
        projectDto.setScrumMaster(EmployeeDto.fromEmployee(project.getScrumMaster()));
        projectDto.setExternalProductManager(project.getExternalProductManager());

        return projectDto;
    }
}
