package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.exception.OrderNotFoundException;
import com.thezookaycompany.zookayproject.exception.PaymentNotSuccessfulException;
import com.thezookaycompany.zookayproject.model.dto.OrdersDto;
import com.thezookaycompany.zookayproject.model.entity.*;
import com.thezookaycompany.zookayproject.repositories.MemberRepository;
import com.thezookaycompany.zookayproject.repositories.OrdersRepository;
import com.thezookaycompany.zookayproject.repositories.PaymentRepository;
import com.thezookaycompany.zookayproject.repositories.TicketRepository;
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

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private MemberRepository memberRepository;

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
            if (payment != null && payment.getSuccess()) {
                List<Map<String, Object>> result = new ArrayList<>();
                // FIXME:điều chỉnh lại nhe
//                for (Ticket ticket : order.getOrderDetailTickets()) {
//                    Map<String, Object> ticketInfo = new HashMap<>();
//                    ticketInfo.put("orderID", order.getOrderID());
//                    ticketInfo.put("ticketId", ticket.getTicketId());
//                    result.add(ticketInfo);
//                }
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
    public String createMemberOrders(OrdersDto ordersDto, Account account) {
        Orders orders = new Orders();
        orders.setOrderDate(LocalDateTime.now());
        orders.setDescription(ordersDto.getDescription());
        // tinh totalOrderPrice
        // set member nguoi dat order
        orders.setEmail(account.getEmail());
        orders.setMember(memberRepository.findMemberByEmail(account.getEmail()));

        //******** gọi ticket ra cập nhật expDate save và truyền vào entity orders
        Ticket ticket = ticketRepository.findTicketByTicketId(ordersDto.getTicketId());
        ticket.setExpDate(ordersDto.getExpDate());
        ticketRepository.save(ticket);
        orders.setQuantity(ordersDto.getTicketQuantity());
        orders.setTicket(ticket);
        ordersRepository.save(orders);
        return "Order's created successfully";
    }

    @Override
    public String createGuestOrders(OrdersDto ordersDto){

        Orders orders = new Orders();
        orders.setEmail(ordersDto.getEmail());
        orders.setOrderDate(LocalDateTime.now());
        orders.setDescription(ordersDto.getDescription());

        Ticket ticket = ticketRepository.findTicketByTicketId(ordersDto.getTicketId());
        ticket.setExpDate(ordersDto.getExpDate());
        ticketRepository.save(ticket);

        orders.setQuantity(ordersDto.getTicketQuantity());
        orders.setTicket(ticket);
        ordersRepository.save(orders);
        return "Order's created successfully";
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
