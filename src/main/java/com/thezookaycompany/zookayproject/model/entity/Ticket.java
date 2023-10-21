package com.thezookaycompany.zookayproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "ticketId")
public class Ticket {

//    private LocalDate localDate;
//
//    public Ticket(LocalDate localDate) {
//
//        this.localDate = localDate;
//    }

    @Id
    @Column(name = "TicketID", nullable = false, updatable = false, length = 4)
    private String ticketId;

    @Column(nullable = false, name="TicketPrice")
    private Double ticketPrice;


    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date bookDate;


    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
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
        if (ticketVouchers != null && ticketVouchers.getCoupon() != 0) {
            return ticketPrice * ticketVouchers.getCoupon();
        } else {
            return ticketPrice;
        }
    }


    public void setTicketPrice(final Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(final Date bookDate) {
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
