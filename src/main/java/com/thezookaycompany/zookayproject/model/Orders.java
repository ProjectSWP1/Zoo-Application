package com.thezookaycompany.zookayproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDate;
import java.util.Set;


@Entity
public class Orders {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer orderId;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false)
    private LocalDate orderDate;

    @OneToMany(mappedBy = "order")
    private Set<Payment> orderPayments;

    @ManyToMany(mappedBy = "orderDetailOrderss")
    private Set<Ticket> orderDetailTickets;

    @OneToMany(mappedBy = "order")
    private Set<Member> orderMembers;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(final Integer orderId) {
        this.orderId = orderId;
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
