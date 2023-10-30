package com.thezookaycompany.zookayproject.model.dto;

import java.util.Date;

public class TicketDto {
    private String ticketId;
    private double ticketPrice;

    private Date expDate;

    private String description;
    public  TicketDto(){

    }

    public TicketDto(String ticketId, double ticketPrice, Date bookDate) {
        this.ticketId = ticketId;
        this.ticketPrice = ticketPrice;
        this.expDate = bookDate;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Date getBookDate() {
        return expDate;
    }

    public void setBookDate(Date bookDate) {
        this.expDate = bookDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }
}
