package com.rit.basa_bari.models;

public class Sub_Let_Upload {
    private String image;
    private String month;
    private String gender;
    private int subRent;
    private String phone;
    private String location;
    private String description;

    public Sub_Let_Upload() {
    }

    public Sub_Let_Upload(String image, String month, String gender, int subRent, String phone, String location, String description) {
        this.image = image;
        this.month = month;
        this.gender = gender;
        this.subRent = subRent;
        this.phone = phone;
        this.location = location;
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public int getSubRent() {
        return subRent;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getMonth() {
        return month;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }
}

