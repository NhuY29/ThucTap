package com.example.User.Request;

import com.example.User.Entity.UserEntity;
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
public class OrderRquest {
    private Long orderId;

    private int amountOrder;

    private LocalDate dateOrder;

    private String statusOrder;

    private UserEntity user;
    private UUID userId;
}
