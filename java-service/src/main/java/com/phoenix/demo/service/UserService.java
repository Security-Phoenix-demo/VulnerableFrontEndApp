package com.phoenix.demo.service;

import com.phoenix.demo.dto.UserRequest;
import com.phoenix.demo.model.User;
import com.phoenix.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository users;
    private final PasswordEncoder encoder;

    public UserService(UserRepository users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    public List<User> list() {
        return users.findAll();
    }

    @Transactional
    public User create(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPasswordHash(encoder.encode(request.getPassword()));
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }
        return users.save(user);
    }

    public User require(String username) {
        return users.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Unknown user " + username));
    }
}
