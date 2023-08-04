package com.example.duan1_quanlysalon.model;

public class ProductDetail {
    private int id;
    private int idProduct;
    private int idBill;

    public ProductDetail(int id, int idProduct, int idBill) {
        this.id = id;
        this.idProduct = idProduct;
        this.idBill = idBill;
    }

    public ProductDetail(int idProduct, int idBill) {
        this.idProduct = idProduct;
        this.idBill = idBill;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }
}
