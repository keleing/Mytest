package com.example.liwei.mytest.constant;

import com.example.liwei.mytest.database.MyDataBase;

/**
 * Created by LiWei on 2016/5/17.
 */
public class DataConstant {
    /*数据库*/
    public final static String DATABASE_NAME="mydatabase"; //数据库名字

    public final static String TABLE_BANK_NAME="bank";// 银行表
    public final static int TABLE_JIANBANK_ID=1;//建行id
    public final static int TABLE_ZHONGBANK_ID=2;//中行id

    public final static String TABLE_ZHIFUBAO_NAME="zhifubao";//支付宝表
    public final static int TABLE_ZHIFUBAO_ID=1;//支付宝id
    public final static int TABLE_YUEBAO_ID=2;//余额宝id

    public final static String TABLE_WEIXIN_NAME="weixin";//微信表
    public final static int TABLE_WEXIN_ID=1;//微信id

    public final static String TABLE_WALLET_NAME="wallet";//钱包表
    public final static int TABLE_WALLET_ID=1;//钱包id

    public final static String TABLE_USER_NAME="user";
}
