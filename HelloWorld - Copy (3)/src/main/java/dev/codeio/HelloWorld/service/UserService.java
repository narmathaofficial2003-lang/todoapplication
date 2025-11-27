package dev.codeio.HelloWorld.service;

import dev.codeio.HelloWorld.models.User;
import dev.codeio.HelloWorld.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // CREATE USER
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // GET USER BY ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}
