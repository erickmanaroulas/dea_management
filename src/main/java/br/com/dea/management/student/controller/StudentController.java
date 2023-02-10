package br.com.dea.management.student.controller;

import br.com.dea.management.student.domain.Student;
import br.com.dea.management.student.dto.StudentDto;
import br.com.dea.management.student.service.StudentService;
import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

    @Autowired
    StudentService studentService;

    @Operation(summary = "Load all students without pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Error fetching student list"),
    })
    @Deprecated
    @RequestMapping(value = "/all-without-pagination", method = RequestMethod.GET)
    public List<StudentDto> getStudentsWithOutPagination() {
        List<Student> students = this.studentService.findAllStudents();
        return StudentDto.fromStudents(students);
    }

    @Operation(summary = "Load the list of students paginated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Page or Page Size params not valid"),
            @ApiResponse(responseCode = "500", description = "Error fetching student list"),
    })
    @GetMapping("/all")
    public Page<StudentDto> getStudents(@RequestParam Integer page,
                                        @RequestParam Integer pageSize) {
        log.info(String.format("Fetching students : page : %s : pageSize", page, pageSize));

        Page<Student> studentsPaged = this.studentService.findAllStudentsPaginated(page, pageSize);
        Page<StudentDto> students = studentsPaged.map(student -> StudentDto.fromStudent(student));

        log.info(String.format("Students loaded successfully : Students : %s : pageSize", students.getContent()));

        return students;

    }

    @Operation(summary = "Load the student by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Student Id invalid"),
            @ApiResponse(responseCode = "404", description = "Student Not found"),
            @ApiResponse(responseCode = "500", description = "Error fetching student list"),
    })
    @GetMapping("/{id}")
    public StudentDto getStudent(@PathVariable("id") Integer id) {
        log.info(String.format("Fetching student by id : Id : %s", id));

        StudentDto student = StudentDto.fromStudent(this.studentService.findStudentById(id.longValue()));

        log.info(String.format("Student loaded successfully : Student : %s", student));

        return student;
    }
}
