package com.example.navigationbar.Model;

public class ItemGridViewModel {
    String name,itemimg,price,mrpprice,discount,totalquantity,id,category,brand,color;
    // int price,mrpprice,discount;

    public ItemGridViewModel() {
    }

    public ItemGridViewModel(String name, String itemimg, String price, String mrpprice, String discount, String totalquantity, String id, String category, String brand, String color) {
        this.name = name;
        this.itemimg = itemimg;
        this.price = price;
        this.mrpprice = mrpprice;
        this.discount = discount;
        this.totalquantity = totalquantity;
        this.id = id;
        this.category = category;
        this.brand = brand;
        this.color = color;
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

    public String getTotalquantity() {
        return totalquantity;
    }

    public void setTotalquantity(String totalquantity) {
        this.totalquantity = totalquantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}