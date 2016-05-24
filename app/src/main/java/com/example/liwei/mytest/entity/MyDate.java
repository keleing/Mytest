package com.example.liwei.mytest.entity;

/**
 * Created by LiWei on 2016/5/8.
 */
public class MyDate {
    int year;
    int month;
    int day;
    int hour;
    int minute;
    public MyDate(){
    }
    public MyDate(int year,int month,int day,int hour,int minute){
        this.year=year;
        this.month=month;
        this.day=day;
        this.hour=hour;
        this.minute=minute;
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

    public void setHour(int hour){
        this.hour=hour;
    }

    public int getHour(){
        return hour;
    }

    public void setMinute(int minute){
        this.minute=minute;
    }

    public int getMinute(){
        return minute;
    }
}
