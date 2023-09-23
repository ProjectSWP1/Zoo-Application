package com.thezookaycompany.zookayproject.model.dto;

import org.antlr.v4.runtime.misc.NotNull;

public class LoginDto {
    private String email;
    @NotNull
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
