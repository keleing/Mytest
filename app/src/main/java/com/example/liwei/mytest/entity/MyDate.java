package com.example.liwei.mytest.entity;

/**
 * Created by LiWei on 2016/5/8.
 */
public class MyDate {
    int year;
    int month;
    int day;
    public MyDate(){
    }
    public MyDate(int year,int month,int day){
        this.year=year;
        this.month=month;
        this.day=day;
    }
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
