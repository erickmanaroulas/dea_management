package br.com.dea.management.student.controller;

import br.com.dea.management.student.domain.Student;
import br.com.dea.management.student.dto.StudentDto;
import br.com.dea.management.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Student> getStudentsAllRaw() {
        return this.studentService.findAllStudents();
    }

    @RequestMapping(value = "/without-pagination", method = RequestMethod.GET)
    public List<StudentDto> getStudentsWithOutPagination() {
        List<Student> students = this.studentService.findAllStudents();
        return StudentDto.fromStudents(students);
    }

    @GetMapping("/students")
    public Page<StudentDto> getStudents(@RequestParam Integer page,
                                        @RequestParam Integer pageSize) {

        Page<Student> studentsPaged = this.studentService.findAllStudentsPaginated(page, pageSize);
        Page<StudentDto> students = studentsPaged.map(student -> StudentDto.fromStudent(student));
        return students;

    }

    @GetMapping("/student")
    public StudentDto getStudent(@RequestParam Integer id) {
        return StudentDto.fromStudent(this.studentService.findStudentById(id.longValue()));
    }
}
