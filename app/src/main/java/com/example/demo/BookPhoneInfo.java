package com.example.demo;

public class BookPhoneInfo {
    String date,startingTime,endingTime,slot,mobilenumber,refcode;


    public String getDate() {

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public String getEndingTime() {
        return endingTime;
    }

    public String getRefcode() {
        return refcode;
    }

    public void setRefcode(String refcode) {
        this.refcode = refcode;
    }

    public BookPhoneInfo() {
    }

    public void setEndingTime(String endingTime) {
        this.endingTime = endingTime;
    }

    public String getSlot() {
        return slot;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public BookPhoneInfo(String date, String startingTime, String endingTime, String slot,String mobilenumber,String refcode) {
        //this.phone = phone;
        this.date = date;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.slot = slot;
        this.mobilenumber=mobilenumber;
        this.refcode=refcode;
    }
}
