package com.thezookaycompany.zookayproject.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;


@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "ticketId")
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

    //user book ngày tham quan Zoo
        @Column(nullable = true)
        @Temporal(TemporalType.DATE)
        private Date visitDate;

    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
    @JsonBackReference
    private Voucher ticketVouchers;


    @Column(name = "description")
    private String description;


    @OneToMany(mappedBy = "ticket")
    @JsonBackReference
    private Set<Orders> ticketInOrders;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "orderID", nullable = true)
//    private Orders orderDetail;

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date expDate) {
        this.visitDate = expDate;
    }

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

    public Voucher getTicketVouchers() {
        return ticketVouchers;
    }

    public void setTicketVouchers(final Voucher ticketVouchers) {
        this.ticketVouchers = ticketVouchers;
    }

    public Set<Orders> getTicketInOrders() {
        return ticketInOrders;
    }
    public void setTicketInOrders(Set<Orders> ticketInOrders) {
        this.ticketInOrders = ticketInOrders;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}