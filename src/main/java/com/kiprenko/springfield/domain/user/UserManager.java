package com.kiprenko.springfield.domain.user;

import java.util.List;

public interface UserManager {
    User create(User user);

    UserDto get(long id);

    List<UserDto> getList(int page);

    List<UserDto> getList(int page, int pageSize);

    void updateInfo(UserDto user);

    void updatePassword(Long id, String newPassword);

    void delete(long id);
}
