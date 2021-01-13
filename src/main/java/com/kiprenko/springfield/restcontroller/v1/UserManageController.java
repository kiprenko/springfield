package com.kiprenko.springfield.restcontroller.v1;

import com.kiprenko.springfield.domain.user.UserDto;
import com.kiprenko.springfield.domain.user.UserInfoProjection;
import com.kiprenko.springfield.domain.user.UserRole;
import com.kiprenko.springfield.domain.user.UserService;
import com.kiprenko.springfield.security.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

import static com.kiprenko.springfield.security.SecurityConstants.ADMIN_ROLE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/user")
public class UserManageController {

    private final UserService userService;

    @Autowired
    public UserManageController(UserService userService) {
        this.userService = userService;
    }

    @RolesAllowed(ADMIN_ROLE)
    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public long createUser(@RequestBody UserDto user) {
        return userService.create(user).getId();
    }

    @RolesAllowed(ADMIN_ROLE)
    @GetMapping(value = "/list", produces = APPLICATION_JSON_VALUE)
    public List<UserInfoProjection> getUsersList(@RequestParam Integer pageNumber,
                                                 @RequestParam(required = false) Integer pageSize) {
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
    public UserInfoProjection getUser(@RequestParam(required = false) Long id,
                                      @RequestParam(required = false) String username,
                                      Authentication authentication) {
        if (id == null && username == null) {
            throw new IllegalArgumentException("Can't get a user when both id and username null. Specify id or username parameter.");
        }
        assertAccessToGetUser(id, username, authentication);
        return id == null ? userService.get(username) : userService.get(id);
    }

    private void assertAccessToGetUser(Long id, String username, Authentication authentication) {
        AppUserDetails currentUser = (AppUserDetails) authentication.getPrincipal();
        if (currentUser.getRole() == UserRole.ADMIN) {
            return;
        }
        Long currentUserId = currentUser.getId();
        if (!currentUserId.equals(id) || !currentUser.getUsername().equals(username)) {
            throw new AccessDeniedException(String.format("User with ID = %s doesn't have permission to review other users information.", currentUserId));
        }
    }

    @PutMapping(value = "/updateInfo", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void updateUserInfo(@RequestBody UserDto user) {
        userService.updateInfo(user);
    }

    @PutMapping(value = "/updatePassword", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void updateUserPassword(@RequestParam Long id,
                                   @RequestBody String newPassword) {
        userService.updatePassword(id, newPassword);
    }

    @RolesAllowed(ADMIN_ROLE)
    @DeleteMapping(value = "/delete")
    public void deleteUser(@RequestParam Long id) {
        userService.delete(id);
    }

    @RolesAllowed(ADMIN_ROLE)
    @GetMapping(value = "/count", produces = APPLICATION_JSON_VALUE)
    public long getUsersCount() {
        return userService.getCount();
    }
}
