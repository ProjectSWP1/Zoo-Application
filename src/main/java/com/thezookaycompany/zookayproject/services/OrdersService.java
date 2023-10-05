package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.entity.Orders;

public interface OrderService {
    Orders findOrdersByOrderID (Integer orderID);
}
