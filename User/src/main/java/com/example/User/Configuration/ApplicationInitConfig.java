package com.example.User.Configuration;

import com.example.User.Entity.UserEntity;
import com.example.User.Reponsitory.UserRepositories;
import com.example.User.enums.Role;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.UUID;

@Configuration
public class ApplicationInitConfig {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    ApplicationRunner applicationRunner(UserRepositories userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                UserEntity user = UserEntity.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(Role.ADMIN.name())
                        .imageId(UUID.fromString("69cd4018-4b6b-47c3-92dc-bd22e2a9ea54"))
                        .firstname("Ý")
                        .lastname("Nguyễn")
                        .dob(LocalDate.parse("2003-06-29"))
                        .build();
                userRepository.save(user);
            }
        };
    }
}
