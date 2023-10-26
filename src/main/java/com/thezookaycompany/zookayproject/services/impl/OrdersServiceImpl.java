package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.exception.OrderNotFoundException;
import com.thezookaycompany.zookayproject.model.entity.Orders;
import com.thezookaycompany.zookayproject.model.entity.Ticket;
import com.thezookaycompany.zookayproject.repositories.OrdersRepository;
import com.thezookaycompany.zookayproject.services.OrdersService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public Orders findOrdersByOrderID(Integer orderID) {
        return ordersRepository.findOrdersByOrderID(orderID);
    }
    private final EntityManager entityManager;
    @Autowired
    public OrdersServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
            public List<Map<String, Object>> listOrderDetailsTicket(Integer orderID) {
                Orders order = entityManager.find(Orders.class, orderID);
                if (order != null) {
                    List<Map<String, Object>> result = new ArrayList<>();
                    for (Ticket ticket : order.getOrderDetailTickets()) {
                        Map<String, Object> ticketInfo = new HashMap<>();
                        ticketInfo.put("orderID", order.getOrderID());
                        ticketInfo.put("ticketId", ticket.getTicketId());
                        result.add(ticketInfo);
                    }
                    return result;
                } else {
                    throw new OrderNotFoundException("Order not found");
        }
    }
    @Override
    public List<Orders> findAllOrders() {
        return ordersRepository.findAll();
    }

    @Override
    public List<Orders> findAllByOrderIDAsc() {
        return ordersRepository.findAllByOrderIDAsc();
    }

    @Override
    public List<Orders> findAllByOrderIDDesc() {
        return ordersRepository.findAllByOrderIDDesc();
    }

    @Override
    public List<Orders> findOrdersByDescriptionContainingKeyword(String keyword) {
        return ordersRepository.findOrdersByDescriptionContainingKeyword(keyword);
    }
}
