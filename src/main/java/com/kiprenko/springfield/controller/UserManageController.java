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
    public List<UserDto> getUsersList(@RequestParam Integer pageNumber, @RequestParam(required = false) Integer pageSize) {
        AssertUsersListParameters(pageNumber, pageSize);
        if (pageSize == null) {
            return userManager.getList(pageNumber);
        }
        return userManager.getList(pageNumber, pageSize);
    }

    private void AssertUsersListParameters(Integer pageNumber, Integer pageSize) {
        if (pageNumber == null || pageNumber < 0) {
            throw new IllegalArgumentException("Page number can't be null or less than zero. Page number = " + pageNumber);
        }
        if (pageSize != null && pageSize < 0) {
            throw new IllegalArgumentException("Page size can't be less than zero. Page size = " + pageSize);
        }
    }

    @GetMapping(value = "/get", produces = APPLICATION_JSON_VALUE)
    public UserDto getUser(@RequestParam Long id) {
        return userManager.get(id);
    }

    @PutMapping(value = "/updateInfo", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void updateUserInfo(@RequestBody UserDto user) {
        userManager.updateInfo(user);
    }

    @PutMapping(value = "/updatePassword", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void updateUserPassword(@RequestParam Long id, @RequestBody String newPassword) {
        userManager.updatePassword(id, newPassword);
    }

    @DeleteMapping(value = "/delete")
    public void deleteUser(@RequestParam Long id) {
        userManager.delete(id);
    }

    @GetMapping(value = "/count", produces = APPLICATION_JSON_VALUE)
    public long getUsersCount() {
        return userManager.getCount();
    }
}
