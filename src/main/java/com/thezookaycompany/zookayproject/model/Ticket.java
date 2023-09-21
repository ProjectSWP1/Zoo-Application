package com.thezookaycompany.zookayproject.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;


@Entity
public class Ticket {

    @Id
    @Column(nullable = false, updatable = false, length = 4)
    private String ticketId;

    @Column(nullable = false)
    private Double ticketPrice;

    @Column(nullable = false)
    private LocalDate bookDate;

    @OneToOne(mappedBy = "ticket")
    private Voucher ticketVouchers;

    @ManyToMany
    @JoinTable(
            name = "OrderDetails",
            joinColumns = @JoinColumn(name = "TicketID"),
            inverseJoinColumns = @JoinColumn(name = "OrderID")
    )
    private Set<Orders> orderDetail;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(final String ticketId) {
        this.ticketId = ticketId;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(final Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public LocalDate getBookDate() {
        return bookDate;
    }

    public void setBookDate(final LocalDate bookDate) {
        this.bookDate = bookDate;
    }

    public Voucher getTicketVouchers() {
        return ticketVouchers;
    }

    public void setTicketVouchers(final Voucher ticketVouchers) {
        this.ticketVouchers = ticketVouchers;
    }

    public Set<Orders> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(final Set<Orders> orderDetail) {
        this.orderDetail = orderDetail;
    }

}
