package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.model.entity.Orders;
import com.thezookaycompany.zookayproject.repositories.OrderRepository;
import com.thezookaycompany.zookayproject.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/order")

public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;

    @GetMapping("/get-order/{orderID}")
    Orders findOrdersByOrderID(@PathVariable("orderID") Integer orderID) {

        return orderService.findOrdersByOrderID(orderID);

    }

}
