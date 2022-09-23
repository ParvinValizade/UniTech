package com.unitech.unitech.dto;


public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String pin;
    private String password;

    public UserDto() {
    }

    public UserDto(String id, String firstName, String lastName, String pin, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
