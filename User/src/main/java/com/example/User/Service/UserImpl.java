package com.example.User.Service;

import com.example.User.DTO.UserDTO;
import com.example.User.Entity.ImageEntity;
import com.example.User.Reponsitory.ImageRepositories;
import com.example.User.Reponsitory.UserRepositories;
import com.example.User.Request.UserRequest;
import com.example.User.Entity.UserEntity;
import com.example.User.enums.Role;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserImpl implements UserService {

    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private ImageRepositories imageRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserEntity createUser(String username, String password, String firstname, String lastname, LocalDate dob, MultipartFile imageFile) throws IOException {
        ImageEntity image = saveImage(imageFile);
        UserEntity user = new UserEntity();
        user.setUsername(username);
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setDob(dob);
        user.setImageId(image.getId());
        user.setRoles(Role.USER.name());
        return userRepositories.save(user);
    }


    private ImageEntity saveImage(MultipartFile imageFile) throws IOException {
        String directory = "D:\\ThucTapIT5\\MyFile";
        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        Path filePath = Path.of(directory, fileName);

        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        ImageEntity image = new ImageEntity();
        image.setUrl(filePath.toString());
        
        return imageRepository.save(image);
    }
    public UserEntity GetMyInfo(){
        var contex = SecurityContextHolder.getContext();
        String name = contex.getAuthentication().getName();
        UserEntity user = userRepositories.findByUsername(name).orElseThrow(() -> new RuntimeException("User Not Found"));
        return user;
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
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
        userDTO.setImageId(user.getImageId());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }
    @PostAuthorize("returnObject.username== authentication.name")
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
//        if (userRepositories.existsOrdersByUserId(id)) {
//            throw new RuntimeException("Cannot delete user with orders");
//        }
        user.setDeleted(true);
        userRepositories.save(user);
    }

    public Page<UserEntity> pagination(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 2);
        return userRepositories.findByDeletedFalse(pageable);
    }

}
