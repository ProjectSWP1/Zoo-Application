package com.thezookaycompany.zookayproject.model.dto;

import com.nimbusds.jose.shaded.gson.annotations.SerializedName;
import com.thezookaycompany.zookayproject.model.entity.Payment;

public class PaymentDto {

    private String cardNumber;
    private String expMonth;
    private String expYear;
    private String cvc;
    private String token;
    private String email;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
