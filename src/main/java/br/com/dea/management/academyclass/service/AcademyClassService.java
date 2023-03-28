package br.com.dea.management.academyclass.service;

import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.academyclass.dto.CreateAcademyClassRequestDto;
import br.com.dea.management.academyclass.dto.UpdateAcademyClassRequestDto;
import br.com.dea.management.academyclass.repository.AcademyClassRepository;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AcademyClassService {

    @Autowired
    private AcademyClassRepository academyClassRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<AcademyClass> findAllAcademyClassPaginated(Integer page, Integer pageSize) {
        return this.academyClassRepository.findAllPaginated(PageRequest.of(page, pageSize, Sort.by("startDate").ascending()));
    }

    public AcademyClass findAcademyClassById(Long id) {
        return this.academyClassRepository.findById(id).orElseThrow(() -> new NotFoundException(AcademyClass.class, id));
    }

    public AcademyClass createAcademyClass(CreateAcademyClassRequestDto createAcademyClassRequestDto) {
        Employee employee = this.employeeRepository.findById(createAcademyClassRequestDto.getInstructorId())
                .orElseThrow(() -> new NotFoundException(Employee.class, createAcademyClassRequestDto.getInstructorId()));

        AcademyClass academyClass = AcademyClass.builder()
                .instructor(employee)
                .startDate(createAcademyClassRequestDto.getStartDate())
                .endDate(createAcademyClassRequestDto.getEndDate())
                .classType(createAcademyClassRequestDto.getClassType())
                .build();

        return this.academyClassRepository.save(academyClass);
    }

    public AcademyClass updateAcademyClass(Long academyClassId, UpdateAcademyClassRequestDto updateAcademyClassRequestDto) {
        Employee employee = this.employeeRepository.findById(updateAcademyClassRequestDto.getInstructorId())
                .orElseThrow(() -> new NotFoundException(Employee.class, updateAcademyClassRequestDto.getInstructorId()));

        AcademyClass academyClass = this.academyClassRepository.findById(academyClassId)
                        .orElseThrow(() -> new NotFoundException(AcademyClass.class, academyClassId));

        academyClass.setInstructor(employee);
        academyClass.setStartDate(updateAcademyClassRequestDto.getStartDate());
        academyClass.setEndDate(updateAcademyClassRequestDto.getEndDate());
        academyClass.setClassType(updateAcademyClassRequestDto.getClassType());

        return this.academyClassRepository.save(academyClass);
    }

    public void deleteAcademyClass(Long academyClassId) {
        AcademyClass academyClass = this.findAcademyClassById(academyClassId);
        this.academyClassRepository.delete(academyClass);
    }
}
