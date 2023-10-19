package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.model.entity.Cage;
import com.thezookaycompany.zookayproject.model.entity.Orders;
import com.thezookaycompany.zookayproject.model.entity.Ticket;
import com.thezookaycompany.zookayproject.repositories.OrdersRepository;
import com.thezookaycompany.zookayproject.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/order")

public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @GetMapping("/get-order/{orderID}")
    Orders findOrdersByOrderID(@PathVariable("orderID") Integer orderID) {

        return ordersService.findOrdersByOrderID(orderID);

    }
    @GetMapping("/get-order")
    public List<Orders> finAllOrders (){
        return ordersService.findAllOrders();
    }

    @GetMapping("/get-order-desc/{keyword}")
    public List<Orders> getCagesByDescription(@PathVariable String keyword) {
        return ordersService.findOrdersByDescriptionContainingKeyword(keyword);
    }
    @GetMapping("/get-order/ascending")
    public List<Orders> getOrdersByOrderIDAscending() {
        return ordersService.findAllByOrderIDAsc();
    }

    @GetMapping("/get-order/descending")
    public List<Orders> getOrdersByOrderIDDescending() {
        return ordersService.findAllByOrderIDDesc();
    }
    @GetMapping("/get-order-details-ticket/{orderID}")
    public List<Ticket> listOrderTicketDetails(@PathVariable Integer orderID) {
        return ordersService.listOrderDetailsTicket(orderID);
    }

}
