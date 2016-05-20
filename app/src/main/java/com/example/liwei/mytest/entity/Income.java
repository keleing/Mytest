package com.example.liwei.mytest.entity;

/**
 * Created by LiWei on 2016/5/18.
 */
public class Income extends MyDate{
    private String source;
    private String destination;
    private float amount;

    public Income(){

    }

    public Income(String source, String destination, float amount,int year,int month,int day){
        this.source = source;
        this.destination = destination;
        this.amount = amount;
        this.year=year;
        this.month=month;
        this.day=day;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
