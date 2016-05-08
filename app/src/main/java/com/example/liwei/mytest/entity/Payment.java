package com.example.liwei.mytest.entity;

import java.util.Date;

/**
 * Created by LiWei on 2016/4/25.
 */
public class Payment {
    private MyDate date;
    private float amount;
    private String purpose;
    private String way;

    public Payment() {
    }
    public Payment(float amount, MyDate date, String purpose, String way) {
        this.amount = amount;
        this.date = date;
        this.purpose = purpose;
        this.way = way;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public MyDate getTime() {
        return date;
    }

    public void setTime(MyDate date) {
        this.date = date;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }
}
