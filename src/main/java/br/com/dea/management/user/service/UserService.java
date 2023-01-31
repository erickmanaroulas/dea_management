package br.com.dea.management.user.service;

import br.com.dea.management.exceptions.NotFoundException;
import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    public User findUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(User.class, email));
    }

    public User findUserByLinkedin(String linkedin) {
        return this.userRepository.findByLinkedin(linkedin).orElseThrow(() -> new NotFoundException(User.class, linkedin));
    }

    public User findById(long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class, id));
    }
}
