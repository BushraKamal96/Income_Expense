package com.example.acer.incomeexpense.models;

public class User_model {

    private int id;
    private String email, name, password, number, wallet, limit;

    public User_model() {
    }

    public User_model(int id, String email, String name, String password, String number, String wallet, String limit) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.number = number;
        this.wallet = wallet;
        this.limit = limit;
    }

    public User_model(String wallet, String limit) {
        this.wallet= wallet;
        this.limit= limit;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}