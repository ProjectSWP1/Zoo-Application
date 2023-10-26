package com.thezookaycompany.zookayproject.model.dto;

import java.util.Date;

public class OrdersDto {
    private Integer orderID;
    private String description;

    private Date orderDate;

    private Double totalOrder;

    private String email;

    private String phoneNumber;

    public Double getTotalOrder() {
        return totalOrder;
    }

    public OrdersDto(Integer orderID, Double totalOrder) {
        this.orderID = orderID;
        this.totalOrder = totalOrder;
    }


    public void setTotalOrder(Double totalOrder) {
        this.totalOrder = totalOrder;
    }

    public OrdersDto(){

    }

    public OrdersDto(Integer orderID, String description, Date orderDate) {
        this.orderID = orderID;
        this.description = description;
        this.orderDate = orderDate;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
