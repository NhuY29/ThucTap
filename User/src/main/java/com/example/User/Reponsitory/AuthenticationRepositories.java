package com.example.User.Reponsitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.User.Entity.UserEntity;

@Repository
public interface AuthenticationRepositories extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
}
