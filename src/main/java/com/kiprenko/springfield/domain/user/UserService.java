package com.kiprenko.springfield.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user);

    UserDto get(long id);

    Optional<User> get(String username);

    List<UserDto> getList(int page);

    List<UserDto> getList(int page, int pageSize);

    void updateInfo(UserDto user);

    void updatePassword(Long id, String newPassword);

    void delete(long id);

    long getCount();
}
