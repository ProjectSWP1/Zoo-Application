package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.entity.Orders;
import com.thezookaycompany.zookayproject.repositories.OrderRepository;
import com.thezookaycompany.zookayproject.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private  OrderRepository orderRepository;

    @Override
    public Orders findOrdersByOrderID(Integer orderID) {
        return orderRepository.findOrdersByOrderID(String.valueOf(orderID));
    }
}
