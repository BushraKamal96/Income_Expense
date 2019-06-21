package com.example.acer.incomeexpense.models;

public class Loan_model {

    String id;
    String personName;
    String loan;
    String date, month, year;
    String usernumber, return_date, loan_type, onlydate;

    public Loan_model() {
    }

    public Loan_model(String id, String personName, String loan, String date, String month, String year, String usernumber, String return_date, String loan_type, String onlydate) {
        this.id = id;
        this.personName = personName;
        this.loan = loan;
        this.date = date;
        this.month = month;
        this.year = year;
        this.usernumber = usernumber;
        this.return_date = return_date;
        this.loan_type = loan_type;
        this.onlydate = onlydate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
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

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getLoan_type() {
        return loan_type;
    }

    public void setLoan_type(String loan_type) {
        this.loan_type = loan_type;
    }

    public String getOnlydate() {
        return onlydate;
    }

    public void setOnlydate(String onlydate) {
        this.onlydate = onlydate;
    }
}
