package com.kiprenko.springfield.domain.user;

public interface UserManager {
    long create(User user);

    long get(long id);

    long getList(int page);

    long update(User user);

    void delete(long id);
}
