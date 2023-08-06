package com.example.duan1_quanlysalon.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Bill implements Serializable {
    @SerializedName("idBill")
    private int id;
    private String phoneNumberCustomer;
    private String nameCustomer;
    private String userNameEmployee;
    private String bookTime;
    private int sumService;
    private int sumProduct;
    private String time;
    private String status;
    private int totalPrice;

    public Bill(int id, String phoneNumberCustomer, String nameCustomer, String userNameEmployee, String bookTime, int sumService, int sumProduct, String time, String status, int totalPrice) {
        this.id = id;
        this.phoneNumberCustomer = phoneNumberCustomer;
        this.nameCustomer = nameCustomer;
        this.userNameEmployee = userNameEmployee;
        this.bookTime = bookTime;
        this.sumService = sumService;
        this.sumProduct = sumProduct;
        this.time = time;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public Bill(String phoneNumberCustomer, String nameCustomer, String userNameEmployee, String bookTime, String status) {
        this.phoneNumberCustomer = phoneNumberCustomer;
        this.nameCustomer = nameCustomer;
        this.userNameEmployee = userNameEmployee;
        this.bookTime = bookTime;
        this.status = status;
    }
    public Bill(int id, String phoneNumberCustomer, String nameCustomer, String userNameEmployee, String bookTime, String status) {
        this.id = id;
        this.phoneNumberCustomer = phoneNumberCustomer;
        this.nameCustomer = nameCustomer;
        this.userNameEmployee = userNameEmployee;
        this.bookTime = bookTime;
        this.status = status;
    }
    public Bill(int id, String phoneNumberCustomer, String userNameEmployee, String time, String status, int totalPrice, String nameCustomer, int sumService, int sumProduct){
        this.id = id;
        this.phoneNumberCustomer = phoneNumberCustomer;
        this.userNameEmployee = userNameEmployee;
        this.time = time;
        this.status = status;
        this.totalPrice = totalPrice;
        this.nameCustomer = nameCustomer;
        this.sumService = sumService;
        this.sumProduct = sumProduct;
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

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getUserNameEmployee() {
        return userNameEmployee;
    }

    public void setUserNameEmployee(String userNameEmployee) {
        this.userNameEmployee = userNameEmployee;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public int getSumService() {
        return sumService;
    }

    public void setSumService(int sumService) {
        this.sumService = sumService;
    }

    public int getSumProduct() {
        return sumProduct;
    }

    public void setSumProduct(int sumProduct) {
        this.sumProduct = sumProduct;
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
