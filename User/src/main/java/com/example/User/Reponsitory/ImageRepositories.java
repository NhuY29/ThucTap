package com.example.User.Reponsitory;

import com.example.User.Entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageRepositories extends JpaRepository<ImageEntity, UUID> {

}
