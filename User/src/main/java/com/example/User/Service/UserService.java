package com.example.User.Service;

import com.example.User.DTO.UserDTO;
import com.example.User.Request.UserRequest;
import com.example.User.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public interface UserService {
//    UserEntity createUser(UserRequest request);

    List<UserDTO> listUser();

    UserDTO getById(UUID id);

    UserEntity updateUser(UUID id, UserRequest request);

    void deleteUser(UUID id);

    Page<UserEntity> pagination(Integer pageNo);
    UserEntity createUser(String username, String password, String firstname, String lastname, LocalDate dob, MultipartFile imageFile) throws IOException;
    UserEntity GetMyInfo();
}
