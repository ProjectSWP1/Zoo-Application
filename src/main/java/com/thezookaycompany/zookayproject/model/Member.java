package com.thezookaycompany.zookayproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Set;


@Entity
public class Member {

    @Id
    @Column(nullable = false, updatable = false, length = 12)
    private String phoneNumber;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 50)
    private String address;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, length = 10)
    private String gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @OneToMany(mappedBy = "phoneNumber")
    private Set<Account> phoneNumberAccounts;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(final Orders order) {
        this.order = order;
    }

    public Set<Account> getPhoneNumberAccounts() {
        return phoneNumberAccounts;
    }

    public void setPhoneNumberAccounts(final Set<Account> phoneNumberAccounts) {
        this.phoneNumberAccounts = phoneNumberAccounts;
    }

}
