package com.thezookaycompany.zookayproject.model.dto;

public class VoucherDto {
    private String voucherId;
    private String ticketId;
    public double coupon;

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
}
