package com.rit.basa_bari.models;

public class HostelUpload {
    private String hoimage;
    private String homonth;
    private String hogender;
    private int hostelRent;
    private String hophone;
    private String holocation;
    private String hodescription;

    public HostelUpload() {
    }

    public HostelUpload(String hoimage, String homonth, String hogender, int hostelRent, String hophone, String holocation, String hodescription) {
        this.hoimage = hoimage;
        this.homonth = homonth;
        this.hogender = hogender;
        this.hostelRent = hostelRent;
        this.hophone = hophone;
        this.holocation = holocation;
        this.hodescription = hodescription;
    }

    public String getHoimage() {
        return hoimage;
    }

    public String getHomonth() {
        return homonth;
    }

    public String getHogender() {
        return hogender;
    }

    public int getHostelRent() {
        return hostelRent;
    }

    public String getHophone() {
        return hophone;
    }

    public String getHolocation() {
        return holocation;
    }

    public String getHodescription() {
        return hodescription;
    }
}
