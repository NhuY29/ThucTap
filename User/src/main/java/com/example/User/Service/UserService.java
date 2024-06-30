package com.example.User.Service;

import com.example.User.DTO.UserDTO;
import com.example.User.Request.UserRequest;
import com.example.User.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {
    UserEntity createUser(UserRequest request);

    List<UserDTO> listUser();

    UserDTO getById(UUID id);

    UserEntity updateUser(UUID id, UserRequest request);

    void deleteUser(UUID id);

    Page<UserEntity> pagination(Integer pageNo);


}
