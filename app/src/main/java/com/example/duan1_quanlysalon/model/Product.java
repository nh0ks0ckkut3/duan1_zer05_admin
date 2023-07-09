package com.example.duan1_quanlysalon.model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private int price;
    private String unit;
    private int amount;
    private String brand;
    private String classify;

    public Product(int id, String name, int price, String unit, int amount, String brand, String classify) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.amount = amount;
        this.brand = brand;
        this.classify = classify;
    }

    public Product(String name, int price, String unit, int amount, String brand, String classify) {
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.amount = amount;
        this.brand = brand;
        this.classify = classify;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }
}
