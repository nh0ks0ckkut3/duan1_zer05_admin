package com.example.duan1_quanlysalon.model;

import java.io.Serializable;

public class Service implements Serializable {
    private int idService;
    private String name;
    private int price;
    private String classifyEmployee;
    private String imageService;

    public Service(int id, String name, int price, String classifyEmployee) {
        this.idService = id;
        this.name = name;
        this.price = price;
        this.classifyEmployee = classifyEmployee;
    }

    public Service(String name, int price, String classifyEmployee) {
        this.name = name;
        this.price = price;
        this.classifyEmployee = classifyEmployee;
    }

    public Service(int idService, String name, int price, String classifyEmployee, String imageService) {
        this.idService = idService;
        this.name = name;
        this.price = price;
        this.classifyEmployee = classifyEmployee;
        this.imageService = imageService;
    }

    public Service(String name, int price, String classifyEmployee, String imageService) {
        this.name = name;
        this.price = price;
        this.classifyEmployee = classifyEmployee;
        this.imageService = imageService;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getImageService() {
        return imageService;
    }

    public void setImageService(String imageService) {
        this.imageService = imageService;
    }

    public int getId() {
        return idService;
    }

    public void setId(int id) {
        this.idService = id;
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

    public String getClassifyEmployee() {
        return classifyEmployee;
    }

    public void setClassifyEmployee(String classifyEmployee) {
        this.classifyEmployee = classifyEmployee;
    }
}
