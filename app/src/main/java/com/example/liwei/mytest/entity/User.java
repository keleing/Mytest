package com.example.liwei.mytest.entity;

/**
 * Created by LiWei on 2016/5/17.
 */
public class User {
    private String userID;
    private String password;
    private int headImageId;

    public User() {}

    public User(String userID, String password, int headImageId) {
        this.userID = userID;
        this.password = password;
        this.headImageId = headImageId;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHeadImageId() {
        return headImageId;
    }

    public void setHeadImageId(int headImageId) {
        this.headImageId = headImageId;
    }
}
