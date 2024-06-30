package com.example.User.Service;

import com.example.User.Entity.OrderEntity;
import com.example.User.Reponsitory.OrderRepositories;
import com.example.User.Request.OrderRquest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderImpl implements OrderSerVice {
    @Autowired
    OrderRepositories orderRepositories;

    @Override
    public List<OrderEntity> listOrder() {
        return orderRepositories.findAll();
    }

    @Override
    public OrderEntity addOrder(OrderRquest orderRquest) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatusOrder(orderRquest.getStatusOrder());
        orderEntity.setAmountOrder(orderRquest.getAmountOrder());
        orderEntity.setDateOrder(orderRquest.getDateOrder());
        orderEntity.setUser(orderRquest.getUser());
        orderEntity.setUserId(orderRquest.getUserId());
        return orderRepositories.save(orderEntity);
    }
}
