package br.com.dea.management.project.service;

import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.academyclass.dto.CreateAcademyClassRequestDto;
import br.com.dea.management.academyclass.dto.UpdateAcademyClassRequestDto;
import br.com.dea.management.academyclass.repository.AcademyClassRepository;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.exceptions.NotFoundException;
import br.com.dea.management.project.domain.Project;
import br.com.dea.management.project.dto.CreateProjectRequestDto;
import br.com.dea.management.project.dto.ProjectDto;
import br.com.dea.management.project.dto.UpdateProjectRequestDto;
import br.com.dea.management.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<Project> findAllProjectsPaginated(Integer page, Integer pageSize) {
        return this.projectRepository.findAllPaginated(PageRequest.of(page, pageSize, Sort.by("startDate").ascending()));
    }

    public Project findProjectById(Long id) {
        return this.projectRepository.findById(id).orElseThrow(() -> new NotFoundException(Project.class, id));
    }

    public Project createProject(CreateProjectRequestDto createProjectRequestDto) {
        Employee productOwner = this.employeeRepository.findById(createProjectRequestDto.getProductOwner())
                .orElseThrow(() -> new NotFoundException(Employee.class, createProjectRequestDto.getProductOwner()));

        Employee scrumMaster = this.employeeRepository.findById(createProjectRequestDto.getScrumMaster())
                .orElseThrow(() -> new NotFoundException(Employee.class, createProjectRequestDto.getScrumMaster()));

        Project project = Project.builder()
                .name(createProjectRequestDto.getName())
                .startDate(createProjectRequestDto.getStartDate())
                .endDate(createProjectRequestDto.getEndDate())
                .productOwner(productOwner)
                .scrumMaster(scrumMaster)
                .externalProductManager(createProjectRequestDto.getExternalProductManager())
                .build();

        return this.projectRepository.save(project);
    }

    public Project updateProject(Long projectId, UpdateProjectRequestDto updateProjectRequestDto) {
        Employee productOwner = this.employeeRepository.findById(updateProjectRequestDto.getProductOwner())
                .orElseThrow(() -> new NotFoundException(Employee.class, updateProjectRequestDto.getProductOwner()));

        Employee scrumMaster = this.employeeRepository.findById(updateProjectRequestDto.getScrumMaster())
                .orElseThrow(() -> new NotFoundException(Employee.class, updateProjectRequestDto.getScrumMaster()));

        Project project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(Project.class, projectId));

        project.setName(updateProjectRequestDto.getName());
        project.setStartDate(updateProjectRequestDto.getStartDate());
        project.setEndDate(updateProjectRequestDto.getEndDate());
        project.setProductOwner(productOwner);
        project.setScrumMaster(scrumMaster);
        project.setExternalProductManager(updateProjectRequestDto.getExternalProductManager());

        return this.projectRepository.save(project);
    }

    public void deleteProject(Long projectId) {
        Project project = this.findProjectById(projectId);
        this.projectRepository.delete(project);
    }
}