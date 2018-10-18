package com.rit.basa_bari.models;

public class FlatUpload {
    private String image;
    private String month;
    private String gender;
    private int flatRent;
    private String phone;
    private String location;
    private String description;

    public FlatUpload(String image, String month, String gender, int flatRent, String phone, String location, String description) {
        this.image = image;
        this.month = month;
        this.gender = gender;
        this.flatRent = flatRent;
        this.phone = phone;
        this.location = location;
        this.description = description;
    }

    public FlatUpload() {
    }

    public String getImage() {

        return image;
    }

    public int getFlatRent() {
        return flatRent;
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
