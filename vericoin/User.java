package com.example.vericoin;

public class User {
    public String email;
    public String verizonId;
    public String verizonPassword;

    public User(){

    }

    public User(String email, String verizonId, String verizonPassword) {
        this.email = email;
        this.verizonId = verizonId;
        this.verizonPassword = verizonPassword;
    }
}
