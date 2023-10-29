package com.thezookaycompany.zookayproject.services;

import com.stripe.exception.StripeException;
import com.thezookaycompany.zookayproject.model.dto.OrdersDto;
import com.thezookaycompany.zookayproject.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentService {

    String createPaymentIntent (OrdersDto ordersDto) throws StripeException;
}
