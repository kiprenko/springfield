package com.kiprenko.springfield.security;

import com.kiprenko.springfield.domain.user.UserRole;
import org.springframework.security.core.userdetails.UserDetails;

public interface AppUserDetails extends UserDetails {
    Long getId();

    UserRole getRole();

    boolean isAdmin();
}
