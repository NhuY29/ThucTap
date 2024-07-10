package com.example.User.Service;

import com.example.User.Request.AuthenticationRequest;
import com.example.User.Request.logoutRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public interface AuthenticationService {
    String authenticate(AuthenticationRequest request);
    void logout(logoutRequest request);
}
