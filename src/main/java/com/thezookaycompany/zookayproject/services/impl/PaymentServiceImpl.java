package com.thezookaycompany.zookayproject.services.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.thezookaycompany.zookayproject.model.dto.OrdersDto;
import com.thezookaycompany.zookayproject.model.entity.Payment;
import com.thezookaycompany.zookayproject.model.entity.Ticket;
import com.thezookaycompany.zookayproject.repositories.PaymentRepository;
import com.thezookaycompany.zookayproject.repositories.TicketRepository;
import com.thezookaycompany.zookayproject.services.OrdersService;
import com.thezookaycompany.zookayproject.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Override
    public String createPaymentIntent(OrdersDto ordersDto) throws StripeException {

        Ticket ticket = ticketRepository.findTicketByTicketId(ordersDto.getTicketId());
        double totalOrderPrice = ticket.getTicketPrice() * ordersDto.getTicketQuantity();
        // create payment intent to confirm
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        // object orderDto chứa total amount order
                        //tmp
                        .setAmount((long) (totalOrderPrice* 1000L))
                        .putMetadata("order_id",String.valueOf(ordersDto.getOrderID()))
                        .setCurrency("vnd")
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        // Create a PaymentIntent with the order amount and currency
        PaymentIntent paymentIntent = PaymentIntent.create(params);
        Payment payment = new Payment();
        payment.setOrder(ordersService.findOrdersByOrderID(ordersDto.getOrderID()));
        payment.setStatus(true);
        paymentRepository.save(payment);

        return "Purchased successfully";
    }
}
