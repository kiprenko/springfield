package com.kiprenko.springfield.domain.user;

import com.kiprenko.springfield.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    public static final String USER_NOT_FOUND_BY_ID_TEMPLATE = "User with ID = %d wasn't found";
    private final UserRepository repository;
    private final UserMapper userMapper;
    private final int defaultPageSize;
    private final int maxPageSize;

    public UserServiceImpl(UserRepository repository,
                           UserMapper userMapper,
                           @Value("${application.usersListDefaultPageSize}") int defaultPageSize,
                           @Value("${application.usersListMaxPageSize}") int maxPageSize) {
        this.repository = repository;
        this.userMapper = userMapper;
        this.defaultPageSize = defaultPageSize;
        this.maxPageSize = maxPageSize;
    }

    @Override
    public User create(UserDto userDto) {
        assertUserDto(userDto, "Can't create a user info when user is null");
        return repository.save(userMapper.convertDtoToUser(userDto));
    }

    @Override
    public UserInfoProjection get(long id) {
        assertId(id, "Can't get a user by ID less than 1. ID = %d");
        return repository.findProjectionById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_BY_ID_TEMPLATE, id)));
    }

    private void assertId(long id, String msg) {
        if (id < 1) {
            throw new IllegalArgumentException(String.format(msg, id));
        }
    }

    @Override
    public UserInfoProjection get(String username) {
        assertUsername(username);
        return repository.findProjectionByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with username = %s wasn't found", username)));
    }

    @Override
    public Optional<User> getUser(String username) {
        assertUsername(username);
        return repository.findByUsername(username);
    }

    private void assertUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Can't get a user by null or blank username. Username = " + username);
        }
    }

    @Override
    public List<UserInfoProjection> getList(int page) {
        return getList(page, defaultPageSize);
    }

    @Override
    public List<UserInfoProjection> getList(int pageNumber, int pageSize) {
        if (pageNumber < 0) {
            throw new IllegalArgumentException("Page number can't be less than zero. Page number = " + pageNumber);
        }
        if (pageSize < 0 || pageSize > maxPageSize) {
            throw new IllegalArgumentException(String.format("Page size can't be less than zero or greater than %d. Page size = %d", maxPageSize, pageSize));
        }
        return repository.findAllProjectionsBy(PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public void updateInfo(UserDto user) {
        assertUserDto(user, "Can't update a user info when user is null");
        Long id = user.getId();
        User persistedUser = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_BY_ID_TEMPLATE, id)));

        Optional.ofNullable(user.getFirstName())
                .filter(s -> !s.isBlank())
                .ifPresent(persistedUser::setFirstName);
        Optional.ofNullable(user.getLastName())
                .filter(s -> !s.isBlank())
                .ifPresent(persistedUser::setLastName);
        Optional.ofNullable(user.getBirth())
                .ifPresent(persistedUser::setBirth);
        repository.save(persistedUser);
    }

    private void assertUserDto(UserDto user, String msg) {
        if (user == null) {
            throw new IllegalArgumentException(msg);
        }
    }

    @Override
    public void updatePassword(Long id, String newPassword) {
        if (newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException(String.format("Can't update password for user with ID = %d. New password is null or blank", id));
        }
        User persistedUser = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_BY_ID_TEMPLATE, id)));
        repository.save(persistedUser);
    }

    @Override
    public void delete(long id) {
        assertId(id, "Can't delete a user by ID less than 1. ID = %d");
        if (!repository.existsById(id)) {
            throw new UserNotFoundException(String.format(USER_NOT_FOUND_BY_ID_TEMPLATE, id));
        }
        repository.deleteById(id);
    }

    @Override
    public long getCount() {
        return repository.count();
    }
}
