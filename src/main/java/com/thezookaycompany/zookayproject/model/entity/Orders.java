package com.thezookaycompany.zookayproject.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
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
    private LocalDate orderDate;

    @OneToMany(mappedBy = "order")
    private Set<Payment> orderPayments;

    @ManyToMany(mappedBy = "orderDetail")
    private Set<Ticket> orderDetailTickets;

    @OneToMany(mappedBy = "order")
    private Set<Member> orderMembers;

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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(final LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Set<Payment> getOrderPayments() {
        return orderPayments;
    }

    public void setOrderPayments(final Set<Payment> orderPayments) {
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
