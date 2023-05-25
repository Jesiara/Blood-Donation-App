package com.bawp.jesisproject;

public class Users {
    String name,email,bloodgroup,phonennumber,batch;

    public Users(String name, String email, String bloodgroup, String phonennumber, String batch) {
        this.name = name;
        this.email = email;
        this.bloodgroup = bloodgroup;
        this.phonennumber = phonennumber;
        this.batch = batch;
    }

    public Users() {
        this.name = name;
        this.email = email;
        this.bloodgroup = bloodgroup;
        this.phonennumber = phonennumber;
        this.batch = batch;
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

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getPhonennumber() {
        return phonennumber;
    }

    public void setPhonennumber(String phonennumber) {
        this.phonennumber = phonennumber;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}