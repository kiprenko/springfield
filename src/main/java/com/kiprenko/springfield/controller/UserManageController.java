package com.kiprenko.springfield.controller;

import com.kiprenko.springfield.domain.user.User;
import com.kiprenko.springfield.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
    public long createUser(@RequestBody User user) {
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

    @PutMapping(value = "/updateInfo", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public User updateUserInfo(@RequestBody User user) {
        User persistedUser = userRepository.findById(user.getId()).orElseThrow(IllegalArgumentException::new);
        Optional.ofNullable(user.getFirstName()).ifPresent(persistedUser::setFirstName);
        Optional.ofNullable(user.getLastName()).ifPresent(persistedUser::setLastName);
        Optional.ofNullable(user.getBirth()).ifPresent(persistedUser::setBirth);
        userRepository.save(persistedUser);
        return persistedUser;
    }

    @PutMapping(value = "/updatePassword", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public User updateUserPassword(@RequestBody User user) {
        User persistedUser = userRepository.findById(user.getId()).orElseThrow(IllegalArgumentException::new);
        Optional.ofNullable(user.getPassword()).ifPresent(persistedUser::setPassword);
        userRepository.save(persistedUser);
        return persistedUser;
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
