package com.kiprenko.springfield.domain.user;

import com.kiprenko.springfield.exception.UserNotFoundException;
import com.kiprenko.springfield.exception.UsernameAlreadyExists;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(UserDto user) throws UsernameAlreadyExists;

    UserInfoProjection get(long id) throws UserNotFoundException;

    UserInfoProjection get(String username) throws UserNotFoundException;

    Optional<User> getUser(String username);

    List<UserInfoProjection> getList(int page);

    List<UserInfoProjection> getList(int page, int pageSize);

    void updateInfo(UserDto user) throws UserNotFoundException;

    void updatePassword(Long id, String newPassword) throws UserNotFoundException;

    void delete(long id) throws UserNotFoundException;

    long getCount();
}
