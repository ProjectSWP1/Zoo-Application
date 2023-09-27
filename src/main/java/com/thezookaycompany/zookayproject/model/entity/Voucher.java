package com.thezookaycompany.zookayproject.model.entity;

import jakarta.persistence.*;


@Entity
public class Voucher {

    @Id
    @Column(name = "VoucherID", nullable = false, updatable = false, length = 30)
    private String VoucherID;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TicketID", nullable = false)
    private Ticket ticket;

    public String getVoucherID() {
        return VoucherID;
    }

    public void setVoucherID(String voucherID) {
        VoucherID = voucherID;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(final Ticket ticket) {
        this.ticket = ticket;
    }

}
