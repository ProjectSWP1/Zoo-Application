package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.entity.Orders;
import com.thezookaycompany.zookayproject.model.entity.Ticket;

import java.util.List;

public interface OrdersService {
    Orders findOrdersByOrderID (Integer orderID);
    List<Ticket> listOrderDetailsTicket (Integer orderID);
    List<Orders> findAllOrders();
    List<Orders> findAllByOrderIDAsc();
    List<Orders> findAllByOrderIDDesc();
    List<Orders> findOrdersByDescriptionContainingKeyword(String keyword);
 }
