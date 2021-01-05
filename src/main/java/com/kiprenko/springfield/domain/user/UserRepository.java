package com.kiprenko.springfield.domain.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    List<UserDto> findBy(Pageable pageable);

    Optional<UserDto> findUserViewById(Long id);
}
