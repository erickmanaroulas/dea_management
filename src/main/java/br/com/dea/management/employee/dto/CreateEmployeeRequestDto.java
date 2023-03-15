package br.com.dea.management.employee.dto;

import br.com.dea.management.employee.EmployeeType;
import br.com.dea.management.position.domain.Position;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateEmployeeRequestDto {

    @NotNull(message = "Name could not be null")
    private String name;

    @NotNull(message = "Email could not be null")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password could not be null")
    private String password;

    private String linkedin;

    @NotNull(message = "Employee Type could not be null")
    private EmployeeType employeeType;

    @NotNull(message = "Position could not be null")
    private Long position;
}