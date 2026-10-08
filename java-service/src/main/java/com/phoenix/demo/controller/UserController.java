package com.phoenix.demo.controller;

import com.phoenix.demo.dto.UserRequest;
import com.phoenix.demo.model.User;
import com.phoenix.demo.service.TokenService;
import com.phoenix.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService users;
    private final TokenService tokens;

    public UserController(UserService users, TokenService tokens) {
        this.users = users;
        this.tokens = tokens;
    }

    @GetMapping
    public List<User> list() {
        return users.list();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody UserRequest request) {
        return users.create(request);
    }

    @PostMapping("/{username}/token")
    public Map<String, String> token(@PathVariable String username) {
        User user = users.require(username);
        return Map.of("token", tokens.issue(user.getUsername(), user.getRole()));
    }
}
