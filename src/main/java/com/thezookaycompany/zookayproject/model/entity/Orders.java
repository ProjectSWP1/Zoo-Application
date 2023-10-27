package com.thezookaycompany.zookayproject.model.entity;

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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "orderID")
public class Orders {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderID;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false)
    private LocalDateTime orderDate; // Order Date => Instance.now();

    @Column(nullable = false, length = 30)
    private String email; // nếu phone number không có sẽ là khách hàng chưa đăng ký còn ko lấy từ Member

    @Column(nullable = false, length = 12)
    private String phoneNumber; // nếu phone number không có sẽ là khách hàng chưa đăng ký



//    @OneToOne(mappedBy = "order")
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Payment orderPayments; // tạo tạm thời trước rồi tạo payment để bắt đầu giao dịch

    @OneToMany(mappedBy = "orderDetail")
    private Set<Ticket> orderDetailTickets;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phoneNumber", nullable = false)
    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public double calculateTotalPriceOrder() {
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

    public Set<Ticket> getOrderDetailTickets() {
        return orderDetailTickets;
    }

    public void setOrderDetailTickets(final Set<Ticket> orderDetailTickets) {
        this.orderDetailTickets = orderDetailTickets;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
