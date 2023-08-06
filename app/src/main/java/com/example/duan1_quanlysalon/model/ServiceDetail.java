package com.example.duan1_quanlysalon.model;

public class ServiceDetail {
    private int id;
    private int id_service;
    private int id_bill;

    public ServiceDetail(int id, int id_service, int id_bill) {
        this.id = id;
        this.id_service = id_service;
        this.id_bill = id_bill;
    }

    public ServiceDetail(int id_service, int id_bill) {
        this.id_service = id_service;
        this.id_bill = id_bill;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_service() {
        return id_service;
    }

    public void setId_service(int id_service) {
        this.id_service = id_service;
    }

    public int getId_bill() {
        return id_bill;
    }

    public void setId_bill(int id_bill) {
        this.id_bill = id_bill;
    }
}
