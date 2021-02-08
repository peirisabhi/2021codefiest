package com.zonebecreations.a2021codefiest.model;

public class Customer {
    String name;
    String email;
    String mobile;
    String address;
    int activeStatus;

    public Customer() {
    }

    public Customer(String name, String email, String mobile, String address, int activeStatus) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.activeStatus = activeStatus;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(int activeStatus) {
        this.activeStatus = activeStatus;
    }
}
