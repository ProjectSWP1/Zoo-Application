package com.thezookaycompany.zookayproject.model;

import jakarta.persistence.*;


@Entity
@Table(name =  "Account")
public class Account {

    @Id
    @Column(nullable = false, updatable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 30)
    private String password;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(nullable = false)
    private Integer empId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phone_number_id", nullable = false)
    private Member phoneNumber;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(final Integer empId) {
        this.empId = empId;
    }

    public Member getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final Member phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
