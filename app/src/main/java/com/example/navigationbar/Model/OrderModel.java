package com.example.navigationbar.Model;

public class OrderModel {

    String Names,adress,discount,id,itemimg,mrpprice,name, number,price, status,totalquantity, uuid,date;

    public OrderModel() {
    }

    public OrderModel(String names, String adress, String discount, String id, String itemimg, String mrpprice, String name, String number, String price, String status, String totalquantity, String uuid, String date) {
        Names = names;
        this.adress = adress;
        this.discount = discount;
        this.id = id;
        this.itemimg = itemimg;
        this.mrpprice = mrpprice;
        this.name = name;
        this.number = number;
        this.price = price;
        this.status = status;
        this.totalquantity = totalquantity;
        this.uuid = uuid;
        this.date = date;
    }

    public String getNames() {
        return Names;
    }

    public void setNames(String names) {
        Names = names;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemimg() {
        return itemimg;
    }

    public void setItemimg(String itemimg) {
        this.itemimg = itemimg;
    }

    public String getMrpprice() {
        return mrpprice;
    }

    public void setMrpprice(String mrpprice) {
        this.mrpprice = mrpprice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalquantity() {
        return totalquantity;
    }

    public void setTotalquantity(String totalquantity) {
        this.totalquantity = totalquantity;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
