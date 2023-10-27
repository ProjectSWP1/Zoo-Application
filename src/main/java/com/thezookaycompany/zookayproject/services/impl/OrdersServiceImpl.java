package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.exception.OrderNotFoundException;
import com.thezookaycompany.zookayproject.exception.PaymentNotSuccessfulException;
import com.thezookaycompany.zookayproject.model.dto.OrdersDto;
import com.thezookaycompany.zookayproject.model.entity.Orders;
import com.thezookaycompany.zookayproject.model.entity.Payment;
import com.thezookaycompany.zookayproject.model.entity.Ticket;
import com.thezookaycompany.zookayproject.model.entity.Voucher;
import com.thezookaycompany.zookayproject.repositories.OrdersRepository;
import com.thezookaycompany.zookayproject.repositories.PaymentRepository;
import com.thezookaycompany.zookayproject.services.OrdersService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private PaymentRepository paymentRepository;

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
    public List<Map<String, Object>> listOrderDetailsTicket(Integer orderID) throws PaymentNotSuccessfulException {
        Orders order = entityManager.find(Orders.class, orderID);
        if (order != null) {
            Payment payment = paymentRepository.findPaymentByOrder_OrderID(orderID).orElse(null);
            if (payment != null && payment.isStatus()) {
                List<Map<String, Object>> result = new ArrayList<>();
                for (Ticket ticket : order.getOrderDetailTickets()) {
                    Map<String, Object> ticketInfo = new HashMap<>();
                    ticketInfo.put("orderID", order.getOrderID());
                    ticketInfo.put("ticketId", ticket.getTicketId());
                    result.add(ticketInfo);
                }
                return result;
            } else {
                throw new PaymentNotSuccessfulException("Payment í not successful");
            }
        } else {
            throw new OrderNotFoundException("Order not found");
        }
    }
    @Override
    public String createOrders(OrdersDto ordersDto) {
        // Create a new Orders instance
        Orders newOrders = new Orders();

        // Set the properties of newOrders based on the data in ordersDto
        newOrders.setDescription(ordersDto.getDescription());
        newOrders.setOrderDate(LocalDateTime.now());
        newOrders.setEmail(ordersDto.getEmail());

        // Save the newOrders entity to the database using the ordersRepository
        ordersRepository.save(newOrders);

        // Return a success message
        return "New orders have been added successfully";
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
