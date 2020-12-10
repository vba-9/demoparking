package com.example.demo;

public class BookInfo {
    String date,startingTime,endingTime,slot,mobilenumber;


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

    public BookInfo() {
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

    public BookInfo(String date, String startingTime, String endingTime, String slot,String mobilenumber) {
        //this.phone = phone;
        this.date = date;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.slot = slot;
        this.mobilenumber=mobilenumber;
    }



}
