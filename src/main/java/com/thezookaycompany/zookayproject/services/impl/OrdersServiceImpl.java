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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
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
        ticket.setVisitDate(ordersDto.getVisitDate());
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
        ticket.setVisitDate(ordersDto.getVisitDate());
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
    @Transactional()
    public List<OrdersDto> getAllOrdersDetail() {

        List<Orders> orders = ordersRepository.findAll(); // Use the method provided by your repository to fetch all orders
        return convertToOrdersDtoList(orders);
    }
    @Transactional()
    public Optional<OrdersDto> getOrderDetailsById(Integer orderID) {
        Optional<Orders> order = ordersRepository.findById(orderID);

        if (order.isPresent()) {
            OrdersDto ordersDto = convertToOrdersDto(order.get());
            return Optional.of(ordersDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public long countSoldTickets() {
        // Find all orders with successful payments
        List<Orders> successfulOrders = ordersRepository.findOrdersByPaymentSuccess(true);
        // Calculate the total quantity of successful orders
        long totalQuantitySold = successfulOrders.stream()
                .mapToLong(Orders::getQuantity)
                .sum();

        return totalQuantitySold;
    }
    @Override
    public long countSuccessfulTicketsOrderedToday() {
        // Get the current date and time
        LocalDateTime currentDate = LocalDateTime.now();

        // Calculate the start and end of the current day
        LocalDateTime startOfDay = currentDate.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = currentDate.withHour(23).withMinute(59).withSecond(59).withNano(999);

        // Query the database for orders placed today with successful payments
        List<Orders> successfulOrdersPlacedToday = ordersRepository.findOrdersByOrderDateBetweenAndOrderPaymentsSuccess(startOfDay, endOfDay, true);

        // Calculate the total quantity of tickets ordered today with successful payments
        long totalSuccessfulTicketsOrderedToday = successfulOrdersPlacedToday.stream()
                .mapToLong(Orders::getQuantity)
                .sum();

        return totalSuccessfulTicketsOrderedToday;
    }

    @Override
    public long countSuccessfulTicketsOrderedThisWeek() {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime startOfWeek = currentDate.with(DayOfWeek.MONDAY);
        LocalDateTime endOfWeek = currentDate.with(DayOfWeek.SUNDAY);

        List<Orders> successfulOrdersThisWeek = ordersRepository.findSuccessfulOrdersThisWeek(
                startOfWeek, endOfWeek, true);

        return successfulOrdersThisWeek.stream()
                .mapToLong(Orders::getQuantity)
                .sum();
    }
    @Override
    public long countSuccessfulTicketsOrderedThisMonth(int year, Month month) {
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.withDayOfMonth(month.maxLength()).withHour(23).withMinute(59).withSecond(59).withNano(999);

        List<Orders> successfulOrdersForMonth = ordersRepository.findSuccessfulOrdersThisMonth(
                startOfMonth, endOfMonth, true);

        return successfulOrdersForMonth.stream()
                .mapToLong(Orders::getQuantity)
                .sum();
    }

    @Override
    public long countSuccessfulTicketsOrderedThisYear(int year) {
        LocalDateTime startOfYear = LocalDateTime.of(year, 1, 1, 0, 0, 0, 0);
        LocalDateTime endOfYear = startOfYear.withDayOfYear(365).withHour(23).withMinute(59).withSecond(59).withNano(999); // Assuming non-leap year

        List<Orders> successfulOrdersForYear = ordersRepository.findSuccessfulOrdersThisYear(
                startOfYear, endOfYear, true);

        return successfulOrdersForYear.stream()
                .mapToLong(Orders::getQuantity)
                .sum();
    }


    private List<OrdersDto> convertToOrdersDtoList(List<Orders> orders) {


        List<OrdersDto> ordersDtoList = new ArrayList<>();

        for (Orders order : orders) {
            OrdersDto ordersDto = new OrdersDto();
            // Set properties of OrdersDto from the corresponding properties of Orders
            ordersDto.setOrderID(order.getOrderID());
            ordersDto.setDescription(order.getDescription());
            ordersDto.setOrderDate(order.getOrderDate());
            ordersDto.setEmail(order.getEmail());
            ordersDto.setTicketQuantity(order.getQuantity());
            ordersDto.setTicketPrice(order.getTicket().getTicketPrice());



            // Get phoneNumber from the associated Member
            if (order.getMember() != null) {
                ordersDto.setPhoneNumber(order.getMember().getPhoneNumber());
            }

            // Get expDate from the associated Ticket
            if (order.getTicket() != null) {
                ordersDto.setVisitDate(order.getTicket().getVisitDate());
                ordersDto.setTicketId(order.getTicket().getTicketId());

                // Calculate totalOrder as quantity * ticket price
                double totalOrder = order.getQuantity() * order.getTicket().getTicketPrice();
                ordersDto.setTotalOrder(totalOrder);
            }

            // Check if a payment record exists for this order
            Payment payment = paymentRepository.findPaymentByOrder_OrderID(order.getOrderID()).orElse(null);
            if (payment != null && payment.getSuccess()) {

                ordersDto.setSuccess(true);
            } else {
                ordersDto.setSuccess(false);
            }

            // Add the converted OrdersDto to the list
            ordersDtoList.add(ordersDto);
        }

        return ordersDtoList;
    }

    private OrdersDto convertToOrdersDto(Orders order) {
        OrdersDto ordersDto = new OrdersDto();

        // Set properties of OrdersDto from the corresponding properties of Orders
        ordersDto.setOrderID(order.getOrderID());
        ordersDto.setDescription(order.getDescription());
        ordersDto.setOrderDate(order.getOrderDate());
        ordersDto.setEmail(order.getEmail());
        ordersDto.setTicketQuantity(order.getQuantity());
        ordersDto.setTicketPrice(order.getTicket().getTicketPrice());

        // Get phoneNumber from the associated Member
        if (order.getMember() != null) {
            ordersDto.setPhoneNumber(order.getMember().getPhoneNumber());
        }

        // Get expDate from the associated Ticket
        if (order.getTicket() != null) {
            ordersDto.setVisitDate(order.getTicket().getVisitDate());
            ordersDto.setTicketId(order.getTicket().getTicketId());

            // Calculate totalOrder as quantity * ticket price
            double totalOrder = order.getQuantity() * order.getTicket().getTicketPrice();
            ordersDto.setTotalOrder(totalOrder);
        }

        // Check if a payment record exists for this order
        Payment payment = paymentRepository.findPaymentByOrder_OrderID(order.getOrderID()).orElse(null);
        if (payment != null && payment.getSuccess()) {

            ordersDto.setSuccess(true);

        } else {
            ordersDto.setSuccess(false);
        }

        return ordersDto;
    }

    @Override
    public double calculateTotalPriceOfZoo() {
        List<Orders> orders = ordersRepository.findAll();

        double totalPrice = 0.0;

        for (Orders order : orders) {
            if (order.getOrderPayments() != null && order.getOrderPayments().getSuccess()) {
                double ticketPrice = order.getTicket().getTicketPrice();
                int quantity = order.getQuantity();
                double orderTotal = quantity * ticketPrice;
                totalPrice += orderTotal;
            }
        }

        return totalPrice;
    }
}


