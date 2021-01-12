package com.kiprenko.springfield.security;

import com.kiprenko.springfield.domain.user.User;
import com.kiprenko.springfield.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserDetails(userService.get(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with %s wasn't found", username))));
    }

    private UserDetailsImpl getUserDetails(User user) {
        return UserDetailsImpl.builder()
                .username(user.getUsername())
                .password(user.getEncryptedPassword())
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isEnabled(true)
                .isCredentialsNonExpired(true)
                .grantedAuthorities(getUserAuthorities(user))
                .build();
    }

    private Set<SimpleGrantedAuthority> getUserAuthorities(User user) {
        return user.getRole()
                .getPermissions()
                .stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
