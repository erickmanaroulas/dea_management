package br.com.dea.management.project.dto;

import br.com.dea.management.employee.dto.EmployeeDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateProjectRequestDto {
    @NotNull(message = "Name could not be null")
    private String name;

    @NotNull(message = "Start Date could not be null")
    private LocalDate startDate;

    @NotNull(message = "End Date could not be null")
    private LocalDate endDate;

    @NotNull(message = "Client could not be null")
    private String client;

    @NotNull(message = "Product Owner could not be null")
    private Long productOwner;

    @NotNull(message = "Scrum Master could not be null")
    private Long scrumMaster;

    @NotNull(message = "External Product Manager could not be null")
    private String externalProductManager;
}
