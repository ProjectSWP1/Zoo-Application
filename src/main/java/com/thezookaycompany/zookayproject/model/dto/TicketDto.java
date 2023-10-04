package com.thezookaycompany.zookayproject.model.dto;

import java.util.Date;

public class TicketDto {
    private String ticketId;
    private double ticketPrice;

    private Date bookDate;
    public  TicketDto(){

    }

    public TicketDto(String ticketId, double ticketPrice, Date bookDate) {
        this.ticketId = ticketId;
        this.ticketPrice = ticketPrice;
        this.bookDate = bookDate;
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
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }
}
