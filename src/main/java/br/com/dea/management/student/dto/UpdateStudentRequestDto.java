package br.com.dea.management.student.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateStudentRequestDto {

    @NotNull(message = "Name could not be null")
    private String name;

    @NotNull(message = "Email could not be null")
    private String email;

    @NotNull(message = "Password could not be null")
    private String password;

    private String linkedin;
    private String university;
    private String graduation;
    private LocalDate finishDate;
    private LocalDate enrollmentDate;
}