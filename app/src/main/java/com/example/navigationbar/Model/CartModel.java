package com.example.navigationbar.Model;

public class CartModel {
    String name, itemimg, price, mrpprice, discount, id, totalquantity,date;
    // int price,mrpprice,discount;

    public CartModel() {
    }

    public CartModel(String name, String itemimg, String price, String mrpprice, String discount, String id, String totalquantity, String date) {
        this.name = name;
        this.itemimg = itemimg;
        this.price = price;
        this.mrpprice = mrpprice;
        this.discount = discount;
        this.id = id;
        this.totalquantity = totalquantity;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemimg() {
        return itemimg;
    }

    public void setItemimg(String itemimg) {
        this.itemimg = itemimg;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMrpprice() {
        return mrpprice;
    }

    public void setMrpprice(String mrpprice) {
        this.mrpprice = mrpprice;
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

    public String getTotalquantity() {
        return totalquantity;
    }

    public void setTotalquantity(String totalquantity) {
        this.totalquantity = totalquantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}