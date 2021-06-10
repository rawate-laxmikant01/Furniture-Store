package com.example.navigationbar.Model;


public class RegistrationModel {
    String name,number,email,password,Id;

    public RegistrationModel() {
    }

    public RegistrationModel(String name, String number, String email, String password,String Id) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.password = password;
        this.Id=Id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
}

