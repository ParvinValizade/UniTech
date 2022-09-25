package com.unitech.unitech.dto.request;

public class SignInRequest {

    private String pin;
    private String password;

    public SignInRequest(String pin, String password) {
        this.pin = pin;
        this.password = password;
    }

    public String getPin() {
        return pin;
    }

    public String getPassword() {
        return password;
    }
}
