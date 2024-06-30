package com.example.User.Controller;

import com.example.User.Entity.OrderEntity;
import com.example.User.Request.OrderRquest;
import com.example.User.Service.OrderSerVice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderSerVice orderSerVice;

    @GetMapping("")
    List<OrderEntity> lisOrder() {
        return orderSerVice.listOrder();
    }
    @PostMapping("")
   OrderEntity addOrder(@RequestBody OrderRquest orderRquest) {
       return orderSerVice.addOrder(orderRquest);
    }
}
