package com.example.duan1_quanlysalon.model;

public class ServiceDetail {
    private int id;
    private int idService;
    private int idBill;

    public ServiceDetail(int id, int idService, int idBill) {
        this.id = id;
        this.idService = idService;
        this.idBill = idBill;
    }

    public ServiceDetail(int idService, int idBill) {
        this.idService = idService;
        this.idBill = idBill;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }
}
