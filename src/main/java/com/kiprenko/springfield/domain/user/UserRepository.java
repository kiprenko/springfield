package com.kiprenko.springfield.domain.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<UserInfoProjection> findAllProjectionsBy(Pageable pageable);

    Optional<UserInfoProjection> findProjectionById(Long id);

    Optional<User> findByUsername(String username);

    Optional<UserInfoProjection> findProjectionByUsername(String username);

    boolean existsByUsername(String username);
}
