package com.example.navigationbar.Model;

public class AdressModel {
    String fullname,phoneno,pincode,state,city,houseno,roadname,typeAd;

    public AdressModel() {
    }

    public AdressModel(String fullname, String phoneno, String pincode, String state, String city, String houseno, String roadname, String typeAd) {
        this.fullname = fullname;
        this.phoneno = phoneno;
        this.pincode = pincode;
        this.state = state;
        this.city = city;
        this.houseno = houseno;
        this.roadname = roadname;
        this.typeAd = typeAd;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHouseno() {
        return houseno;
    }

    public void setHouseno(String houseno) {
        this.houseno = houseno;
    }

    public String getRoadname() {
        return roadname;
    }

    public void setRoadname(String roadname) {
        this.roadname = roadname;
    }

    public String getTypeAd() {
        return typeAd;
    }

    public void setTypeAd(String typeAd) {
        this.typeAd = typeAd;
    }
}
