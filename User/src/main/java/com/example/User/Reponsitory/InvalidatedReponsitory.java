package com.example.User.Reponsitory;

import com.example.User.Entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedReponsitory extends JpaRepository<InvalidatedToken, String> {
}
