package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.entity.Orders;

import java.util.List;
import java.util.Map;

public interface OrdersService {
    Orders findOrdersByOrderID (Integer orderID);
    List<Map<String, Object>> listOrderDetailsTicket (Integer orderID);
    List<Orders> findAllOrders();
    List<Orders> findAllByOrderIDAsc();
    List<Orders> findAllByOrderIDDesc();
    List<Orders> findOrdersByDescriptionContainingKeyword(String keyword);
 }
