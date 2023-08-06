package com.example.duan1_quanlysalon.model;

public class ProductDetail {
    private int id;
    private int id_product;
    private int id_bill;
    private int num;

    public ProductDetail(int id, int id_product, int id_bill, int num) {
        this.id = id;
        this.id_product = id_product;
        this.id_bill = id_bill;
        this.num = num;
    }
    public ProductDetail(int id_product, int id_bill){
        this.id_product = id_product;
        this.id_bill = id_bill;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getId_bill() {
        return id_bill;
    }

    public void setId_bill(int id_bill) {
        this.id_bill = id_bill;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
