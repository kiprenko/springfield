package com.kiprenko.springfield.domain.user;

import com.kiprenko.springfield.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserManagerImpl implements UserManager {

    private final UserRepository repository;

    @Autowired
    public UserManagerImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    public User get(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<User> getList(int page) {
        return (List<User>) repository.findAll();
    }

    @Override
    public User updateInfo(User user) {
        User persistedUser = repository.findById(user.getId()).orElseThrow(UserNotFoundException::new);
        Optional.ofNullable(user.getFirstName()).ifPresent(persistedUser::setFirstName);
        Optional.ofNullable(user.getLastName()).ifPresent(persistedUser::setLastName);
        Optional.ofNullable(user.getBirth()).ifPresent(persistedUser::setBirth);
        return repository.save(persistedUser);
    }

    @Override
    public User updatePassword(User user) {
        User persistedUser = repository.findById(user.getId()).orElseThrow(UserNotFoundException::new);
        Optional.ofNullable(user.getPassword()).ifPresent(persistedUser::setPassword);
        return repository.save(persistedUser);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }
}
