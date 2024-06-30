package com.example.User.Controller;

import com.example.User.Request.UserRequest;
import com.example.User.Entity.UserEntity;
import com.example.User.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.example.User.DTO.UserDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("")
    UserEntity createUser(@RequestBody @Valid UserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("")
    List<UserDTO> getAllUser() {
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
