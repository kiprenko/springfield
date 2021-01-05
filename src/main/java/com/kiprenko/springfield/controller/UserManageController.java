package com.kiprenko.springfield.controller;

import com.kiprenko.springfield.domain.user.User;
import com.kiprenko.springfield.domain.user.UserDto;
import com.kiprenko.springfield.domain.user.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
public class UserManageController {

    private final UserManager userManager;

    @Autowired
    public UserManageController(UserManager userManager) {
        this.userManager = userManager;
    }

    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public long createUser(@RequestBody User user) {
        return userManager.create(user).getId();
    }

    @GetMapping(value = "/list", produces = APPLICATION_JSON_VALUE)
    public List<UserDto> getUsersList(@RequestParam Integer page) {
        if (page == null) {
            throw new IllegalArgumentException();
        }
        return userManager.getList(page);
    }

    @GetMapping(value = "/get", produces = APPLICATION_JSON_VALUE)
    public UserDto getUser(@RequestParam Long id) {
        return userManager.get(id);
    }

    @PutMapping(value = "/updateInfo", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public User updateUserInfo(@RequestBody User user) {
        return userManager.updateInfo(user);
    }

    @PutMapping(value = "/updatePassword", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public User updateUserPassword(@RequestBody User user) {
        return userManager.updatePassword(user);
    }

    @DeleteMapping(value = "/delete")
    public void deleteUser(@RequestParam Long id) {
        userManager.delete(id);
    }
}
