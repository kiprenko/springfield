package com.kiprenko.springfield.configuration;

import com.kiprenko.springfield.domain.user.User;
import com.kiprenko.springfield.domain.user.UserRepository;
import com.kiprenko.springfield.domain.user.UserRole;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
@Log4j2
public class DatabaseConfiguration {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> loadUsers(userRepository);
    }

    private void loadUsers(UserRepository userRepository) {
        LOGGER.info("Preloading " + userRepository.save(User.builder()
                                                            .username("admin")
                                                            .password("A12345")
                                                            .firstName("Tom")
                                                            .lastName("Collins")
                                                            .birth(LocalDate.now().minusYears(32))
                                                            .role(UserRole.ADMIN)
                                                            .build()));
        LOGGER.info("Preloading " + userRepository.save(User.builder()
                                                            .username("timofey")
                                                            .password("A12345")
                                                            .firstName("Timofey")
                                                            .lastName("Handsome")
                                                            .birth(LocalDate.now().minusYears(25))
                                                            .role(UserRole.USER)
                                                            .build()));
    }
}
