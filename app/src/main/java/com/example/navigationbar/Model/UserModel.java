package com.example.navigationbar.Model;

public class UserModel {
    String number,id;

    public UserModel() {
    }

    public UserModel(String number, String id) {
        this.number = number;
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
