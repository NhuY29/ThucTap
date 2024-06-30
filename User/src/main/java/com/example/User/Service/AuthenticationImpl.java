package com.example.User.Service;

import com.example.User.Entity.UserEntity;
import com.example.User.Reponsitory.AuthenticationRepositories;
import com.example.User.Request.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationImpl implements AuthenticationService {
    @Autowired
    AuthenticationRepositories authenticationRepositories;

    @Override
    public String authenticate(AuthenticationRequest request) {
        Optional<UserEntity> userOptional = authenticationRepositories.findByUsername(request.getUsername());
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return " Dang Nhap Thanh Cong";
            } else {
                return "UserName hoac Password khong dung!";
            }
        } else {
            return "UserName hoac Password khong dung!";
        }
    }
}
