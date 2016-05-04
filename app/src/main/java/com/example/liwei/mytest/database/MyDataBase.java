package com.example.liwei.mytest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LiWei on 2016/4/27.
 */
public class MyDataBase extends SQLiteOpenHelper {
    private Context context;

    private final static String TABLE_BANK_NAME="bank";
    private final static String TABLE_ZHIFUBAO_NAME="zhifubao";
    private final static String TABLE_WEIXIN_NAME="weixin";
    private final static String TABLE_WALLET_NAME="wallet";


    public MyDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTableBank="create table "+TABLE_BANK_NAME+" (id int,mount NUMERIC(10,2))";
        db.execSQL(sqlCreateTableBank);
        sqlCreateTableBank="insert into "+TABLE_BANK_NAME+" values(1,25.01) ";
        db.execSQL(sqlCreateTableBank);
        sqlCreateTableBank="insert into "+TABLE_BANK_NAME+" values(2,25.01) ";
        db.execSQL(sqlCreateTableBank);

        String sqlCreateTableWeixin="create table "+TABLE_WEIXIN_NAME+" (id int,mount NUMERIC(10,2))";
        db.execSQL(sqlCreateTableWeixin);
        sqlCreateTableWeixin="insert into "+TABLE_WEIXIN_NAME+" values(1,25.01) ";
        db.execSQL(sqlCreateTableWeixin);

        String sqlCreateTableZhifubao="create table "+TABLE_ZHIFUBAO_NAME+" (id int,mount NUMERIC(10,2))";
        db.execSQL(sqlCreateTableZhifubao);
        sqlCreateTableZhifubao="insert into "+TABLE_ZHIFUBAO_NAME+" values(1,25.01) ";
        db.execSQL(sqlCreateTableZhifubao);
        sqlCreateTableZhifubao="insert into "+TABLE_ZHIFUBAO_NAME+" values(2,25.01) ";
        db.execSQL(sqlCreateTableZhifubao);

        String sqlCreateTableWallet="create table "+TABLE_WALLET_NAME+" (id int,mount NUMERIC(10,2))";
        db.execSQL(sqlCreateTableWallet);
        sqlCreateTableWallet="insert into "+TABLE_WALLET_NAME+" values(1,25.01) ";
        db.execSQL(sqlCreateTableWallet);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop if table exists "+TABLE_BANK_NAME);
        db.execSQL("drop if table exists "+TABLE_WEIXIN_NAME);
        db.execSQL("drop if table exists "+TABLE_ZHIFUBAO_NAME);
        db.execSQL("drop if table exists "+TABLE_WALLET_NAME);
        onCreate(db);
    }
}
