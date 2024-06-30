package com.example.User.Service;

import com.example.User.Entity.OrderEntity;
import com.example.User.Request.OrderRquest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderSerVice {
    List<OrderEntity> listOrder();
    OrderEntity addOrder(OrderRquest orderRquest);
}
