package com.example.cinema.dto;

public class CinemaDTO {

    private long id;

    private String name;

    private String address;

    private String email;

    private String phone;

    private String managersID;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getManagersID() {
        return managersID;
    }

    public void setManagersID(String managersID) {
        this.managersID = managersID;
    }
}