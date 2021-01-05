package com.kiprenko.springfield.domain.user;

import java.util.List;

public interface UserManager {
    User create(User user);

    UserDto get(long id);

    List<UserDto> getList(int page);

    User updateInfo(User user);

    User updatePassword(User user);

    void delete(long id);
}
