package com.unitech.unitech.dto.request;

public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String pin;
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPin() {
        return pin;
    }

    public String getPassword() {
        return password;
    }
}
