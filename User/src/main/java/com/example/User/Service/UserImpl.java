package com.example.User.Service;

import com.example.User.DTO.UserDTO;
import com.example.User.Reponsitory.UserRepositories;
import com.example.User.Request.UserRequest;
import com.example.User.Entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserImpl implements UserService {

    @Autowired
    UserRepositories userRepositories;

    @Override
    public UserEntity createUser(UserRequest request) {
        UserEntity user = new UserEntity();
        if (userRepositories.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        user.setUsername(request.getUsername());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setDob(request.getDob());
        return userRepositories.save(user);
    }

    @Override
    public List<UserDTO> listUser() {
        return userRepositories.findByDeletedFalse().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    private UserDTO convertToDTO(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setDob(user.getDob());
        return userDTO;
    }

    @Override
    public UserDTO getById(UUID userId) {
        if (!userRepositories.existsByUserIdAndDeletedFalse(userId)) {
            throw new RuntimeException("User not found");
        }
        UserEntity user = userRepositories.findById(userId).orElse(null);
        return convertToDTO(user);
    }

    @Override
    public UserEntity updateUser(UUID id, UserRequest request) {
        UserEntity user = userRepositories.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isDeleted()) {
            throw new RuntimeException("User is deleted and cannot be updated");
        }
        user.setUsername(request.getUsername());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setDob(request.getDob());

        return userRepositories.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        UserEntity user = userRepositories.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (userRepositories.existsOrdersByUserId(id)) {
            throw new RuntimeException("Cannot delete user with orders");
        }
        user.setDeleted(true);
        userRepositories.save(user);
    }

    public Page<UserEntity> pagination(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 2);
        return userRepositories.findByDeletedFalse(pageable);
    }

}
