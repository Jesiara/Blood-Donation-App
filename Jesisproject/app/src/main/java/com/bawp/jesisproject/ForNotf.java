package com.bawp.jesisproject;

public class ForNotf {

    String name,email,batch,number,sms;
    public ForNotf(){}
    public ForNotf(String name, String email, String batch, String number,String sms) {
        this.name = name;
        this.email = email;
        this.batch = batch;
        this.number = number;
        this.sms=sms;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
