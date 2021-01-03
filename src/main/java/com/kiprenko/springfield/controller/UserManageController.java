package com.kiprenko.springfield.controller;

import com.kiprenko.springfield.domain.user.User;
import com.kiprenko.springfield.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
public class UserManageController {

    private final UserRepository userRepository;

    @Autowired
    public UserManageController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public long createUser(User user) {
        userRepository.save(user);
        return user.getId();
    }

    @GetMapping(value = "/list/{page}", produces = APPLICATION_JSON_VALUE)
    public List<User> getUsersList(@PathVariable Integer page) {
        return (List<User>) userRepository.findAll();
    }

    @GetMapping(value = "/get/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
