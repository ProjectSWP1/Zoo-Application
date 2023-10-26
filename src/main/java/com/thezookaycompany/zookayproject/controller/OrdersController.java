package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.exception.PaymentNotSuccessfulException;
import com.thezookaycompany.zookayproject.model.dto.OrdersDto;
import com.thezookaycompany.zookayproject.model.dto.VoucherDto;
import com.thezookaycompany.zookayproject.model.entity.Orders;
import com.thezookaycompany.zookayproject.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/order")

public class OrdersController {
    private final String SUCCESS_RESPONSE = "success";
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
    @GetMapping("/{orderID}/orderDetails")
    public List<Map<String, Object>> listOrderDetailsTicket(@PathVariable Integer orderID) throws PaymentNotSuccessfulException {
        return ordersService.listOrderDetailsTicket(orderID);
    }
    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody OrdersDto ordersDto) {
        String response = ordersService.createOrders(ordersDto);
        if(response.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.badRequest().body(response);
    }


}
