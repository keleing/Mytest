package com.example.liwei.mytest.entity;

import java.util.Date;

/**
 * Created by LiWei on 2016/4/25.
 */
public class Payment {
    private double amount;
    private Date time;
    private String purpose;
    private String way;

    public Payment(double amount, Date time, String purpose, String way) {
        this.amount = amount;
        this.time = time;
        this.purpose = purpose;
        this.way = way;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
