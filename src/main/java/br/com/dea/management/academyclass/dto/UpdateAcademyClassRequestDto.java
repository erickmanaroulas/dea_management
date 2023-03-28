package br.com.dea.management.academyclass.dto;

import br.com.dea.management.academyclass.ClassType;
import br.com.dea.management.employee.EmployeeType;
import br.com.dea.management.employee.dto.EmployeeDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateAcademyClassRequestDto {

    @NotNull(message = "Start Date could not be null")
    private LocalDate startDate;
    private LocalDate endDate;

    @NotNull(message = "InstructorId could not be null")
    private Long instructorId;

    @NotNull(message = "ClassType could not be null")
    private ClassType classType;
}