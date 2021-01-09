package com.kiprenko.springfield.domain.user;

import com.kiprenko.springfield.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final int defaultPageSize;

    public UserServiceImpl(UserRepository repository, @Value("${usersListDefaultPageSize}") int defaultPageSize) {
        this.repository = repository;
        this.defaultPageSize = defaultPageSize;
    }

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    public UserDto get(long id) {
        return repository.findProjectionById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<UserDto> getList(int page) {
        return getList(page, defaultPageSize);
    }

    @Override
    public List<UserDto> getList(int page, int pageSize) {
        return repository.findAllProjectionsBy(PageRequest.of(page, pageSize));
    }

    @Override
    public void updateInfo(UserDto userInfoUpdate) {
        User persistedUser = repository.findById(userInfoUpdate.getId()).orElseThrow(UserNotFoundException::new);
        Optional.ofNullable(userInfoUpdate.getFirstName())
                .filter(s -> !s.isBlank())
                .ifPresent(persistedUser::setFirstName);
        Optional.ofNullable(userInfoUpdate.getLastName())
                .filter(s -> !s.isBlank())
                .ifPresent(persistedUser::setLastName);
        Optional.ofNullable(userInfoUpdate.getBirth())
                .ifPresent(persistedUser::setBirth);
        repository.save(persistedUser);
    }

    @Override
    public void updatePassword(Long id, String newPassword) {
        if (newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException(String.format("Can't update password for user with ID = %d. New password is null or blank", id));
        }
        User persistedUser = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with ID = %d wasn't found", id)));
        repository.save(persistedUser);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public long getCount() {
        return repository.count();
    }
}
