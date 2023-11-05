package com.thezookaycompany.zookayproject.model.dto;

import java.util.Date;

public class VoucherDto {
    private String voucherId;
    private String ticketId;
    public double coupon;

    public String description;

    public Date expirationDate;

    public VoucherDto() {
    }

    public VoucherDto(String voucherId, String ticketId) {
        this.voucherId = voucherId;
        this.ticketId = ticketId;
    }

    public VoucherDto(String voucherId, String ticketId, double coupon) {
        this.voucherId = voucherId;
        this.ticketId = ticketId;
        this.coupon = coupon;
    }

    public double getCoupon() {
        return coupon;
    }

    public void setCoupon(double coupon) {
        this.coupon = coupon;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
