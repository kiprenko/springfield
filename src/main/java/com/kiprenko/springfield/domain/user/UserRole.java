package com.kiprenko.springfield.domain.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.kiprenko.springfield.domain.user.UserPermission.READ_OWN_DATA;
import static com.kiprenko.springfield.domain.user.UserPermission.READ_USERS_DATA;
import static com.kiprenko.springfield.domain.user.UserPermission.WRITE_OWN_DATA;
import static com.kiprenko.springfield.domain.user.UserPermission.WRITE_USERS_DATA;

public enum UserRole {
    ADMIN(Set.of(READ_OWN_DATA, WRITE_OWN_DATA, READ_USERS_DATA, WRITE_USERS_DATA)),
    USER(Set.of(READ_OWN_DATA, WRITE_OWN_DATA));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(UserPermission::getPermission)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
