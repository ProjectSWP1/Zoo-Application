package com.thezookaycompany.zookayproject.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;


@Entity
public class Orders {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderID;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @OneToOne(mappedBy = "order")
    private Payment orderPayments;

    @ManyToMany(mappedBy = "orderDetail")
    private Set<Ticket> orderDetailTickets;

    @OneToMany(mappedBy = "order")
    private Set<Member> orderMembers;


    public Double calculateTotalPriceOrder() {
        Double total = 0.0;
        for (Ticket ticket : orderDetailTickets) {
            total += ticket.getTicketPrice();
        }
        return total;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(final Integer orderId) {
        this.orderID = orderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(final Date orderDate) {
        this.orderDate = orderDate;
    }

    public Payment getOrderPayments() {
        return orderPayments;
    }

    public void setOrderPayments(final Payment orderPayments) {
        this.orderPayments = orderPayments;
    }

    public Set<Ticket> getOrderDetailTickets() {
        return orderDetailTickets;
    }

    public void setOrderDetailTickets(final Set<Ticket> orderDetailTickets) {
        this.orderDetailTickets = orderDetailTickets;
    }

    public Set<Member> getOrderMembers() {
        return orderMembers;
    }

    public void setOrderMembers(final Set<Member> orderMembers) {
        this.orderMembers = orderMembers;
    }

}
