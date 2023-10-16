package com.thezookaycompany.zookayproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "voucherId")
public class Voucher {

    @Id
    @Column(name = "VoucherID", nullable = false, updatable = false, length = 5)
    private String voucherId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TicketID", nullable = false)
    private Ticket ticket;
    @Column(name = "Coupon", nullable = false)
    private double coupon;

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

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(final Ticket ticket) {
        this.ticket = ticket;
    }

}
