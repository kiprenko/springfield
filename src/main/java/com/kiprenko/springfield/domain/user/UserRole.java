package com.kiprenko.springfield.domain.user;

import java.util.Set;

import static com.kiprenko.springfield.domain.user.UserPermission.READ_OWN_DATA;
import static com.kiprenko.springfield.domain.user.UserPermission.READ_USERS_DATA;
import static com.kiprenko.springfield.domain.user.UserPermission.WRITE_OWN_DATA;
import static com.kiprenko.springfield.domain.user.UserPermission.WRITE_USERS_DATA;

public enum UserRole {
    ADMIN(Set.of(READ_OWN_DATA,WRITE_OWN_DATA, READ_USERS_DATA, WRITE_USERS_DATA)),
    USER(Set.of(READ_OWN_DATA, WRITE_OWN_DATA));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }
}
