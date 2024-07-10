package com.example.User.Controller;

import com.example.User.Request.UserRequest;
import com.example.User.Entity.UserEntity;
import com.example.User.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.User.DTO.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.lang.Math.log;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
//    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/MyInfo")
    UserEntity GetMyInfo(){
        return userService.GetMyInfo();
    }
    @PostMapping("")
    public ResponseEntity<UserEntity> createUser(@RequestParam("username") String username,
                                                 @RequestParam("password") String password,
                                                 @RequestParam("firstname") String firstname,
                                                 @RequestParam("lastname") String lastname,
                                                 @RequestParam("dob") String dob,
                                                 @RequestParam("image") MultipartFile imageFile) {
        try {
            LocalDate dateOfBirth = LocalDate.parse(dob);

            UserEntity newUser = userService.createUser(username, password, firstname, lastname, dateOfBirth, imageFile);
            return ResponseEntity.ok(newUser);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("")
    List<UserDTO> getAllUser() {
//        var authentication =  SecurityContextHolder.getContext().getAuthentication();
//        log.info("Username: {}",authentication.getName());
//        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return userService.listUser();
    }



    @GetMapping("/pagination")
    Page<UserEntity> getAllUser(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        return userService.pagination(pageNo);
    }

    @GetMapping("/{id}")
    UserDTO getById(@PathVariable("id") UUID id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    UserEntity updateUser(@PathVariable("id") UUID id, @RequestBody UserRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable("id") UUID id) {
        userService.deleteUser(id);
    }
}
