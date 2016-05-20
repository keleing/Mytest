package com.example.liwei.mytest.entity;

import java.util.Date;

/**
 * Created by LiWei on 2016/4/25.
 */
public class Payment extends MyDate{
    private float amount;
    private String purpose;
    private String way;

    public Payment() {
    }
    public Payment(int year,int month,int day,float amount, MyDate date, String purpose, String way) {
        this.year=year;
        this.month=month;
        this.day=day;
        this.amount = amount;
        this.purpose = purpose;
        this.way = way;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
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
