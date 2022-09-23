package com.unitech.unitech.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String pin;

    private String password;

    private LocalDateTime creationDate;


    public User() {
    }

    public User(String id, String firstName, String lastName, String pin, String password, LocalDateTime creationDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.password = password;
        this.creationDate = creationDate;
    }

    public User(String firstName, String lastName, String pin, String password, LocalDateTime creationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.password = password;
        this.creationDate = creationDate;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
