package com.thezookaycompany.zookayproject.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.stripe.model.Coupon;
import jakarta.persistence.*;
import org.hibernate.annotations.Formula;

import java.util.Date;
import java.util.Set;
import java.time.LocalDateTime;


@Entity
public class Orders {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderID;


    @Column(nullable = true, length = 200)
    private String description;

    @Column(nullable = false)
    private LocalDateTime orderDate; // Order Date => Instance.now();

    @Column(nullable = false, length = 30)
    private String email; // nếu phone number không có sẽ là khách hàng chưa đăng ký còn ko lấy từ Member

    //@OneToOne(mappedBy = "order")
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Payment orderPayments; // tạo tạm thời trước rồi tạo payment để bắt đầu giao dịch


//    @OneToMany(mappedBy = "orderDetail")
//    private Set<Ticket> orderDetailTickets;

    // các vé trong 1 ngày là giống nhau và mỗi ngày mỗi khác => và 1 order chỉ có thể đặt 1 loại vé cho 1 ngày
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticketId", nullable = true)
    private Ticket ticket;

    @Column(name = "quantity", nullable = true)
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phoneNumber", nullable = true)
    @JsonBackReference
    private Member member;


    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(final LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Payment getOrderPayments() {
        return orderPayments;
    }

    public void setOrderPayments(final Payment orderPayments) {
        this.orderPayments = orderPayments;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
