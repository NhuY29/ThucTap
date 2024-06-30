package com.example.User.Request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private UUID id;
    private String username;

    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    private String firstname;
    private String lastname;
    private LocalDate dob;
}
