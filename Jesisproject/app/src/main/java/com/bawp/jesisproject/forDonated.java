package com.bawp.jesisproject;

public class forDonated {
    String name,email,batch,number;

    public forDonated(){}

    public forDonated(String name, String email, String batch, String number) {
        this.name = name;
        this.email = email;
        this.batch = batch;
        this.number = number;
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
