package com.thezookaycompany.zookayproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "ticket")
    private Set<Voucher> ticketVouchers;

    @ManyToMany
    @JoinTable(
            name = "OrderDetails",
            joinColumns = @JoinColumn(name = "ticketid"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private Set<Orders> orderDetailOrderss;

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

    public Set<Voucher> getTicketVouchers() {
        return ticketVouchers;
    }

    public void setTicketVouchers(final Set<Voucher> ticketVouchers) {
        this.ticketVouchers = ticketVouchers;
    }

    public Set<Orders> getOrderDetailOrderss() {
        return orderDetailOrderss;
    }

    public void setOrderDetailOrderss(final Set<Orders> orderDetailOrderss) {
        this.orderDetailOrderss = orderDetailOrderss;
    }

}
