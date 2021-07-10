package com.example.navigationbar.Model;

import java.util.HashMap;

public class Order {


     String Names,ad,number,status,orderdate,uuid;
     long Total,discount;
     HashMap<String, CartModel> orders;

    public Order() {
    }

    public Order(String names, String ad, String number, String status, String orderdate, String uuid, long total, long discount, HashMap<String, CartModel> orders) {
        Names = names;
        this.ad = ad;
        this.number = number;
        this.status = status;
        this.orderdate = orderdate;
        this.uuid = uuid;
        Total = total;
        this.discount = discount;
        this.orders = orders;
    }

    public String getNames() {
        return Names;
    }

    public void setNames(String names) {
        Names = names;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getTotal() {
        return Total;
    }

    public void setTotal(long total) {
        Total = total;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public HashMap<String, CartModel> getOrders() {
        return orders;
    }

    public void setOrders(HashMap<String, CartModel> orders) {
        this.orders = orders;
    }
}
