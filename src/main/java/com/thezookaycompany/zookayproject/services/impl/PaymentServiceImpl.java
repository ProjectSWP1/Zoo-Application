package com.thezookaycompany.zookayproject.services.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.thezookaycompany.zookayproject.model.dto.OrdersDto;
import com.thezookaycompany.zookayproject.model.dto.PaymentResponse;
import com.thezookaycompany.zookayproject.model.entity.Orders;
import com.thezookaycompany.zookayproject.model.entity.Payment;
import com.thezookaycompany.zookayproject.model.entity.Ticket;
import com.thezookaycompany.zookayproject.repositories.OrdersRepository;
import com.thezookaycompany.zookayproject.repositories.PaymentRepository;
import com.thezookaycompany.zookayproject.repositories.TicketRepository;
import com.thezookaycompany.zookayproject.services.PaymentService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Value("${stripe.api.key}")
    private String secretKey;

    @PostConstruct
    public void setup (){
        Stripe.apiKey = secretKey;
    }


    @Override
    public PaymentResponse createPaymentIntent(OrdersDto ordersDto) throws StripeException {


        Orders orders = ordersRepository.findOrdersByOrderID(ordersDto.getOrderID());
        Payment payment = paymentRepository.findPaymentByOrder(orders);
        if(payment != null){
            return null;
        }
        Ticket ticket = ticketRepository.findTicketByTicketId(orders.getTicket().getTicketId());
        //tinh tong tien order
        double totalOrderPrice = ticket.getTicketPrice() * orders.getQuantity();
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        // createPayment for product cost how much...
                        .setAmount((long)totalOrderPrice)
                        .setCurrency("vnd")
                        .putMetadata("productName", ticket.getTicketId())
                        .setDescription("Paid for orderId: "+ orders.getOrderID())
                        // In the latest version of the API, specifying the `automatic_payment_methods` parameter is optional because Stripe enables its functionality by default.
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        // Create a PaymentIntent with the order amount and currency
        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return new PaymentResponse(paymentIntent.getClientSecret(),paymentIntent.getId());

    }

    @Override
    public String confirmPayment(OrdersDto ordersDto, PaymentResponse paymentResponse) throws StripeException {
        PaymentIntent retrieve = PaymentIntent.retrieve(paymentResponse.getIntentId());
        Orders orders = ordersRepository.findOrdersByOrderID(ordersDto.getOrderID());
        if(retrieve.getStatus()!=null){
            Payment payment = new Payment();
            payment.setSuccess(true);
            payment.setOrder(orders);
            paymentRepository.save(payment);
            orders.setOrderPayments(payment);
            ordersRepository.save(orders);
        }
        return "Purchased successfully";
    }

}
