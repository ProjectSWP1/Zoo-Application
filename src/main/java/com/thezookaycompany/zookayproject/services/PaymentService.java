package com.thezookaycompany.zookayproject.services;

import com.stripe.exception.StripeException;
import com.thezookaycompany.zookayproject.model.dto.OrdersDto;
import com.thezookaycompany.zookayproject.model.dto.PaymentDto;
import com.thezookaycompany.zookayproject.model.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse createPaymentIntent (OrdersDto ordersDto) throws StripeException;
    String confirmPayment (OrdersDto ordersDto, String intendId) throws StripeException;

    String handlePaymentFailed(String orderId);
    boolean checkPaymentStatus (OrdersDto ordersDto);

}
