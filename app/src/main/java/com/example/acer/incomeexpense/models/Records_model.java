package com.example.acer.incomeexpense.models;

public class Records_model {

    String id;
    String item;
    String rupee;
    String date, month, year;
    String onlydate, onlymonth;

    String usernumber, address;

    public Records_model() {
    }

    public Records_model(String id, String item, String rupee, String date, String month, String year, String onlydate, String onlymonth, String usernumber, String address) {
        this.id = id;
        this.item = item;
        this.rupee = rupee;
        this.date = date;
        this.month = month;
        this.year = year;
        this.onlydate = onlydate;
        this.onlymonth = onlymonth;
        this.usernumber = usernumber;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getRupee() {
        return rupee;
    }

    public void setRupee(String rupee) {
        this.rupee = rupee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getOnlydate() {
        return onlydate;
    }

    public void setOnlydate(String onlydate) {
        this.onlydate = onlydate;
    }

    public String getOnlymonth() {
        return onlymonth;
    }

    public void setOnlymonth(String onlymonth) {
        this.onlymonth = onlymonth;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}