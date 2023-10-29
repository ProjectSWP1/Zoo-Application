package com.thezookaycompany.zookayproject.services;

import com.stripe.exception.StripeException;
import com.thezookaycompany.zookayproject.model.dto.OrdersDto;
import com.thezookaycompany.zookayproject.model.dto.PaymentDto;

public interface PaymentService {

    String createPaymentIntent (OrdersDto ordersDto, PaymentDto createPayment) throws StripeException;
}
