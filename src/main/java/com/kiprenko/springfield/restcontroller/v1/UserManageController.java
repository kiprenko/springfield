package com.kiprenko.springfield.restcontroller.v1;

import com.kiprenko.springfield.domain.user.User;
import com.kiprenko.springfield.domain.user.UserDto;
import com.kiprenko.springfield.domain.user.UserService;
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
@RequestMapping("/api/v1/user")
public class UserManageController {

    private final UserService userService;

    @Autowired
    public UserManageController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public long createUser(@RequestBody User user) {
        return userService.create(user).getId();
    }

    @GetMapping(value = "/list", produces = APPLICATION_JSON_VALUE)
    public List<UserDto> getUsersList(@RequestParam Integer pageNumber, @RequestParam(required = false) Integer pageSize) {
        AssertUsersListParameters(pageNumber, pageSize);
        if (pageSize == null) {
            return userService.getList(pageNumber);
        }
        return userService.getList(pageNumber, pageSize);
    }

    private void AssertUsersListParameters(Integer pageNumber, Integer pageSize) {
        if (pageNumber == null || pageNumber < 0) {
            throw new IllegalArgumentException("Page number can't be null or less than zero. Page number = " + pageNumber);
        }
        if (pageSize != null && pageSize < 0) {
            throw new IllegalArgumentException("Page size can't be less than zero. Page size = " + pageSize);
        }
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public UserDto getUser(@RequestParam Long id) {
        return userService.get(id);
    }

    @PutMapping(value = "/updateInfo", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void updateUserInfo(@RequestBody UserDto user) {
        userService.updateInfo(user);
    }

    @PutMapping(value = "/updatePassword", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void updateUserPassword(@RequestParam Long id, @RequestBody String newPassword) {
        userService.updatePassword(id, newPassword);
    }

    @DeleteMapping(value = "/delete")
    public void deleteUser(@RequestParam Long id) {
        userService.delete(id);
    }

    @GetMapping(value = "/count", produces = APPLICATION_JSON_VALUE)
    public long getUsersCount() {
        return userService.getCount();
    }
}
