package br.com.dea.management.academyclass.controller;

import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.academyclass.dto.AcademyClassDto;
import br.com.dea.management.academyclass.dto.CreateAcademyClassRequestDto;
import br.com.dea.management.academyclass.dto.UpdateAcademyClassRequestDto;
import br.com.dea.management.academyclass.service.AcademyClassService;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.dto.CreateEmployeeRequestDto;
import br.com.dea.management.employee.dto.UpdateEmployeeRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Tag(name = "AcademyClass", description = "The AcademyClass API")
@RequestMapping("academy-class")
public class AcademyClassController {

    @Autowired
    AcademyClassService academyClassService;

    @Operation(summary = "Load the list of academyClass paginated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Page or Page Size params not valid"),
            @ApiResponse(responseCode = "500", description = "Error fetching academyClass list"),
    })
    @GetMapping({"/", ""})
    public Page<AcademyClassDto> getAcademyClass(@RequestParam() Integer page,
                                                 @RequestParam() Integer pageSize) {

        log.info(String.format("Fetching academyClass : page : %s : pageSize", page, pageSize));

        Page<AcademyClass> academyClassPaged = this.academyClassService.findAllAcademyClassPaginated(page, pageSize);
        Page<AcademyClassDto> academyClasses = academyClassPaged.map(AcademyClassDto::fromAcademyClass);

        log.info(String.format("AcademyClass loaded successfully : AcademyClass : %s : pageSize", academyClasses.getContent().size()));

        return academyClasses;

    }

    @Operation(summary = "Load the academyClass by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "AcademyClass Id invalid"),
            @ApiResponse(responseCode = "404", description = "AcademyClass Not found"),
            @ApiResponse(responseCode = "500", description = "Error fetching academyClass list"),
    })
    @GetMapping("/{id}")
    public AcademyClassDto getAcademyClass(@PathVariable Long id) {

        log.info(String.format("Fetching academyClass by id : Id : %s", id));

        AcademyClassDto academyClass = AcademyClassDto.fromAcademyClass(this.academyClassService.findAcademyClassById(id));

        log.info(String.format("AcademyClass loaded successfully : AcademyClass : %s", academyClass));

        return academyClass;

    }

    @Operation(summary = "Create a new academyClass")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Payload not valid"),
            @ApiResponse(responseCode = "500", description = "Error creating academyClass"),
    })
    @PostMapping({"/", ""})
    public void createAcademyClass(@Valid @RequestBody CreateAcademyClassRequestDto createAcademyClassRequestDto) {
        log.info(String.format("Creating academyClass : Payload : %s", createAcademyClassRequestDto));

        AcademyClass academyClass = academyClassService.createAcademyClass(createAcademyClassRequestDto);

        log.info(String.format("AcademyClass created successfully : id : %s", academyClass.getId()));
    }

    @Operation(summary = "Update an academyClass")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Payload not valid"),
            @ApiResponse(responseCode = "404", description = "AcademyClass not found"),
            @ApiResponse(responseCode = "500", description = "Error updating academyClass"),
    })
    @PutMapping("/{academyClassId}")
    public void updateAcademyClass(@PathVariable Long academyClassId, @Valid @RequestBody UpdateAcademyClassRequestDto updateAcademyClassRequestDto) {
        log.info(String.format("Updating academyClass : Payload : %s", updateAcademyClassRequestDto));

        AcademyClass academyClass = academyClassService.updateAcademyClass(academyClassId, updateAcademyClassRequestDto);

        log.info(String.format("AcademyClass updated successfully : id : %s", academyClass.getId()));
    }


    @Operation(summary = "Delete an academyClass")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Payload not valid"),
            @ApiResponse(responseCode = "404", description = "AcademyClass not found"),
            @ApiResponse(responseCode = "500", description = "Error deleting academyClass"),
    })
    @DeleteMapping("/{academyClassId}")
    public void deleteAcademyClass(@PathVariable Long academyClassId) {
        log.info(String.format("Removing academyClass : Id : %s", academyClassId));

        academyClassService.deleteAcademyClass(academyClassId);

        log.info(String.format("AcademyClass removed successfully : id : %s", academyClassId));
    }
}
