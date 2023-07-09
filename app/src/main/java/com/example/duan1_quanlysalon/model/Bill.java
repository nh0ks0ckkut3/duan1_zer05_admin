package com.example.duan1_quanlysalon.model;

import java.io.Serializable;

public class Bill implements Serializable {
    private int id;
    private String phoneNumberCustomer;
    private String userNameEmployee;
    private int idService;
    private int idProduct;
    private String time;
    private String status;
    private int totalPrice;

    public Bill(int id, String phoneNumberCustomer, String userNameEmployee, int idService, int idProduct, String time, String status, int totalPrice) {
        this.id = id;
        this.phoneNumberCustomer = phoneNumberCustomer;
        this.userNameEmployee = userNameEmployee;
        this.idService = idService;
        this.idProduct = idProduct;
        this.time = time;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public Bill(String phoneNumberCustomer, String userNameEmployee, int idService, int idProduct, String time, String status, int totalPrice) {
        this.phoneNumberCustomer = phoneNumberCustomer;
        this.userNameEmployee = userNameEmployee;
        this.idService = idService;
        this.idProduct = idProduct;
        this.time = time;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public Bill(String phoneNumberCustomer, String userNameEmployee, int idService, String time, String status) {
        this.phoneNumberCustomer = phoneNumberCustomer;
        this.userNameEmployee = userNameEmployee;
        this.idService = idService;
        this.time = time;
        this.status = status;
    }

    public Bill(String phoneNumberCustomer, int idService, String time, String status) {
        this.phoneNumberCustomer = phoneNumberCustomer;
        this.idService = idService;
        this.time = time;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumberCustomer() {
        return phoneNumberCustomer;
    }

    public void setPhoneNumberCustomer(String phoneNumberCustomer) {
        this.phoneNumberCustomer = phoneNumberCustomer;
    }

    public String getUserNameEmployee() {
        return userNameEmployee;
    }

    public void setUserNameEmployee(String userNameEmployee) {
        this.userNameEmployee = userNameEmployee;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
