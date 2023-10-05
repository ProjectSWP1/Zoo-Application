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
import java.util.List;

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
    public List<Ticket> listOrderDetailsTicket(Integer orderID) {
        Orders order = entityManager.find(Orders.class, orderID);

        if (order != null) {
            // Convert the Set<Ticket> to a List<Ticket>
            return new ArrayList<>(order.getOrderDetailTickets());
        } else {
            throw new OrderNotFoundException("Order not found");
        }
    }
}
