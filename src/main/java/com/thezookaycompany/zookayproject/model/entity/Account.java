package com.thezookaycompany.zookayproject.model.entity;

import jakarta.persistence.*;


@Entity
@Table(name =  "Account")
public class Account {

    @Column(nullable = false, updatable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 30)
    private String password;

    @Id
    @Column(nullable = false, unique = true, length = 30)
    private String email;

    public Employees getEmailEmployees() {
        return emailEmployees;
    }

    public void setEmailEmployees(Employees emailEmployees) {
        this.emailEmployees = emailEmployees;
    }

    @OneToOne(mappedBy = "email")
    private Employees emailEmployees;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phoneNumber", nullable = false)
    private Member phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RoleID", nullable = false)
    private Role role;

    public Account(String username, String password, String email, Member phoneNumber, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

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

    public Member getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final Member phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
