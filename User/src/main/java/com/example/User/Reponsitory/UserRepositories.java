package com.example.User.Reponsitory;

import com.example.User.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepositories extends JpaRepository<UserEntity, UUID> {

    boolean existsOrdersByUserId(UUID userId);

    boolean existsByUsername(String username);

    List<UserEntity> findByDeletedFalse();

    boolean existsByUserIdAndDeletedFalse(UUID userId);

    Page<UserEntity> findByDeletedFalse(Pageable pageable);

}
