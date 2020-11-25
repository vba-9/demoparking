package com.example.demo;

public class Signupinfo {
    String name,phone,mailid,password,conpassword;

   public String getName() {
        return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMailid() {
        return mailid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConpassword() {
        return conpassword;
    }

    public void setConpassword(String conpassword) {
        this.conpassword = conpassword;
    }

    public void setMailid(String mailid) {
        this.mailid = mailid;
    }

    public Signupinfo() {
    }

    public Signupinfo(String name,String phone,String mailid,String password, String conpassword) {
       this.name = name;
       this.phone=phone;
       this.mailid=mailid;
       this.password=password;
       this.conpassword=conpassword;
    }
}
