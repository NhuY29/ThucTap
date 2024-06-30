package com.example.User.Service;

import com.example.User.Request.AuthenticationRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    String authenticate(AuthenticationRequest request);
}
