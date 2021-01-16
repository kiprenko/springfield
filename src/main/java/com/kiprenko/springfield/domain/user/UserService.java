package com.kiprenko.springfield.domain.user;

import com.kiprenko.springfield.exception.UsernameAlreadyExists;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(UserDto user) throws UsernameAlreadyExists;

    UserInfoProjection get(long id);

    UserInfoProjection get(String username);

    Optional<User> getUser(String username);

    List<UserInfoProjection> getList(int page);

    List<UserInfoProjection> getList(int page, int pageSize);

    void updateInfo(UserDto user);

    void updatePassword(Long id, String newPassword);

    void delete(long id);

    long getCount();
}
