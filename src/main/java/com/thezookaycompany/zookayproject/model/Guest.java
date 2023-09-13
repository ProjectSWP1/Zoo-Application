package com.thezookaycompany.zookayproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int GuestID;
    private String name;
    private String email;
    private String phone;

    public Guest() {
    }

    public Guest(int guestID, String name, String email, String phone) {
        GuestID = guestID;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getGuestID() {
        return GuestID;
    }

    public void setGuestID(int guestID) {
        GuestID = guestID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
