package br.com.dea.management.user.controller;

import br.com.dea.management.student.domain.Student;
import br.com.dea.management.student.dto.StudentDto;
import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.dto.UserDto;
import br.com.dea.management.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value= "/all", method = RequestMethod.GET)
    public Page<UserDto> getUsers(@RequestParam Integer page,
                                  @RequestParam Integer pageSize) {

        Page<User> usersPaged = this.userService.findAllUsersPaginated(page, pageSize);
        Page<UserDto> users = usersPaged.map(user -> UserDto.fromUser(user));
        return users;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserDto getUserById(@PathVariable("id") Integer id) {
        return UserDto.fromUser(this.userService.findUserById(id));
    }
}
