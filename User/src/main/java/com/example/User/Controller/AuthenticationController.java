package com.example.User.Controller;

import com.example.User.Request.AuthenticationRequest;
import com.example.User.Request.logoutRequest;
import com.example.User.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("")
    String authenticate(@RequestBody AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }
    @PostMapping("/logout")
    public void logout(@RequestBody logoutRequest request){
        authenticationService.logout(request);
    }
}
