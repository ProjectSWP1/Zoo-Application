package com.thezookaycompany.zookayproject.services.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Token;
import com.stripe.param.PaymentIntentCreateParams;
import com.thezookaycompany.zookayproject.model.dto.OrdersDto;
import com.thezookaycompany.zookayproject.model.dto.PaymentDto;
import com.thezookaycompany.zookayproject.model.entity.Payment;
import com.thezookaycompany.zookayproject.model.entity.Ticket;
import com.thezookaycompany.zookayproject.repositories.PaymentRepository;
import com.thezookaycompany.zookayproject.repositories.TicketRepository;
import com.thezookaycompany.zookayproject.services.OrdersService;
import com.thezookaycompany.zookayproject.services.PaymentService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private PaymentRepository paymentRepository;

    @Value("${stripe.api.key}")
    private String secretKey;

    @PostConstruct
    public void setup (){
        Stripe.apiKey = secretKey;
    }

    @Override
    public String createPaymentIntent(OrdersDto ordersDto, PaymentDto createPayment) throws StripeException {

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
    public PaymentDto createCardToken(PaymentDto paymentDto){
        Map<String, Object> card = new HashMap<>();
        card.put("number",paymentDto.getCardNumber());
        card.put("exp_month",paymentDto.getExpMonth());
        card.put("exp_year",paymentDto.getExpYear());
        card.put("cvc",paymentDto.getCvc());
        Map<String, Object> params = new HashMap<>();
        params.put("card",card);
        try {
            Token token = Token.create(params);
            if(token !=null && token.getId() != null){

            }
        } catch (StripeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
