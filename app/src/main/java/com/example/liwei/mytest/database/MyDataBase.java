package com.example.liwei.mytest.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.VisibleForTesting;

import com.example.liwei.mytest.entity.MyDate;
import com.example.liwei.mytest.entity.Payment;
import com.example.liwei.mytest.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiWei on 2016/4/27.
 */
public class MyDataBase extends SQLiteOpenHelper {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    private final static String DATABASE_NAME="mydatabase";
    private final static String TABLE_BANK_NAME="bank";
    private final static int TABLE_JIANBANK_ID=1;
    private final static int TABLE_ZHONGBANK_ID=2;
    private final static String TABLE_ZHIFUBAO_NAME="zhifubao";
    private final static int TABLE_ZHIFUBAO_ID=1;
    private final static int TABLE_YUEBAO_ID=2;
    private final static String TABLE_WEIXIN_NAME="weixin";
    private final static int TABLE_WEXIN_ID=1;
    private final static String TABLE_WALLET_NAME="wallet";
    private final static int TABLE_WALLET_ID=1;
    /*支付表*/
    private final static String TABLE_PAYMENT_NAME="payment";
    /*用户表*/
    private final static String TABLE_USER_NAME="user";


    public MyDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTableBank="create table "+TABLE_BANK_NAME+" (id int,mount NUMERIC(10,2))";
        db.execSQL(sqlCreateTableBank);
        sqlCreateTableBank="insert into "+TABLE_BANK_NAME+" values(1,25.0) ";
        db.execSQL(sqlCreateTableBank);
        sqlCreateTableBank="insert into "+TABLE_BANK_NAME+" values(2,25.0) ";
        db.execSQL(sqlCreateTableBank);

        String sqlCreateTableWeixin="create table "+TABLE_WEIXIN_NAME+" (id int,mount NUMERIC(10,2))";
        db.execSQL(sqlCreateTableWeixin);
        sqlCreateTableWeixin="insert into "+TABLE_WEIXIN_NAME+" values(1,25.0) ";
        db.execSQL(sqlCreateTableWeixin);

        String sqlCreateTableZhifubao="create table "+TABLE_ZHIFUBAO_NAME+" (id int,mount NUMERIC(10,2))";
        db.execSQL(sqlCreateTableZhifubao);
        sqlCreateTableZhifubao="insert into "+TABLE_ZHIFUBAO_NAME+" values(1,25.0) ";
        db.execSQL(sqlCreateTableZhifubao);
        sqlCreateTableZhifubao="insert into "+TABLE_ZHIFUBAO_NAME+" values(2,25.0) ";
        db.execSQL(sqlCreateTableZhifubao);

        String sqlCreateTableWallet="create table "+TABLE_WALLET_NAME+" (id int,mount NUMERIC(10,2))";
        db.execSQL(sqlCreateTableWallet);
        sqlCreateTableWallet="insert into "+TABLE_WALLET_NAME+" values(1,25.0) ";
        db.execSQL(sqlCreateTableWallet);

        String sqlCreateTablePayment="create table "+TABLE_PAYMENT_NAME+" (year int,month int,day int,purpose TEXT,way TEXT,mount NUMERIC(10,2))";
        db.execSQL(sqlCreateTablePayment);
        sqlCreateTablePayment="insert into "+TABLE_PAYMENT_NAME+" values(1,2,3,4,5,6) ";
        db.execSQL(sqlCreateTablePayment);

        String sqlCreateTableUser="create table "+TABLE_USER_NAME+" (userid text,password text)";
        db.execSQL(sqlCreateTableUser);
        sqlCreateTableUser="insert into "+TABLE_USER_NAME+" values('mytest','123') ";
        db.execSQL(sqlCreateTableUser);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop if table exists "+TABLE_BANK_NAME);
        db.execSQL("drop if table exists "+TABLE_WEIXIN_NAME);
        db.execSQL("drop if table exists "+TABLE_ZHIFUBAO_NAME);
        db.execSQL("drop if table exists "+TABLE_WALLET_NAME);
        onCreate(db);
    }
    public void updateDataNewAmount(String tableName,String row,String newValue,int id){
        sqLiteDatabase=this.getWritableDatabase();
        String sql="update "+tableName+" set "+row+"="+newValue+" where id="+id;
        sqLiteDatabase.execSQL(sql);
    }

    public void updateDataAddAmount(String tableName,String row,String addValue,int id){
        sqLiteDatabase=this.getWritableDatabase();
        String sql="update "+tableName+" set "+row+"="+row+"+"+addValue+" where id="+id;
        sqLiteDatabase.execSQL(sql);
    }
    public void updateDataReduceAmount(String tableName,String row,String addValue,int id){
        sqLiteDatabase=this.getWritableDatabase();
        String sql="update "+tableName+" set "+row+"="+row+"-"+addValue+" where id="+id;
        sqLiteDatabase.execSQL(sql);
    }

    public float queryData(String table,int id,String rowName) {
        sqLiteDatabase=this.getReadableDatabase();
        String[] cloum=new String[]{rowName};
        float mount=0.00f;
        try{
            Cursor cursor=sqLiteDatabase.query(table, cloum,"id ="+id, null, null, null, null);
            cursor.moveToFirst();
            mount=cursor.getFloat(cursor.getColumnIndex(rowName));
        }catch (Exception e){
            return 0.00f;
        }
        return mount;
    }
    public List<Payment> queryPayData() {
        List<Payment> listPayment=new ArrayList<Payment>();
        sqLiteDatabase=this.getReadableDatabase();
        String[] row=new String[]{"year","month","day","purpose","way","mount"};
        Cursor cursor=sqLiteDatabase.query(TABLE_PAYMENT_NAME, row, null, null, null, null, null);
        cursor.moveToFirst();
        for(int i=0;i<cursor.getCount();i++){
            Payment payment=new Payment();
            MyDate date=new MyDate();
            date.setYear(cursor.getInt(cursor.getColumnIndex("year")));
            date.setMonth(cursor.getInt(cursor.getColumnIndex("month")));
            date.setDay(cursor.getInt(cursor.getColumnIndex("day")));
            payment.setTime(date);
            payment.setPurpose(cursor.getString(cursor.getColumnIndex("purpose")));
            payment.setWay(cursor.getString(cursor.getColumnIndex("way")));
            payment.setAmount(cursor.getFloat(cursor.getColumnIndex("mount")));
            listPayment.add(payment);
            cursor.moveToNext();
        }
        return listPayment;
    }
    public float getAllMount(){
        float jianBankMount=0.00f,zhongBankMount=0.00f,weixinMount=0.00f,zhifubaoMount=0.00f,yuebaoMount=0.00f,walletMount=0.00f;
        float allMount1;
        jianBankMount= this.queryData(TABLE_BANK_NAME, TABLE_JIANBANK_ID, "mount");
        zhongBankMount= this.queryData(TABLE_BANK_NAME, TABLE_ZHONGBANK_ID, "mount");
        weixinMount= this.queryData(TABLE_WEIXIN_NAME, TABLE_WEXIN_ID,"mount");
        zhifubaoMount= this.queryData(TABLE_ZHIFUBAO_NAME, TABLE_ZHIFUBAO_ID, "mount");
        yuebaoMount=this.queryData(TABLE_ZHIFUBAO_NAME, TABLE_YUEBAO_ID, "mount");
        walletMount= this.queryData(TABLE_WALLET_NAME,TABLE_WALLET_ID,"mount");
        allMount1=jianBankMount+zhongBankMount+weixinMount+zhifubaoMount+yuebaoMount+walletMount;
        return allMount1;
    }
    public void addData(String table,Payment payment){
        sqLiteDatabase=this.getWritableDatabase();
        int year=payment.getTime().getYear();
        int month=payment.getTime().getMonth();
        int day=payment.getTime().getDay();
        String way=payment.getWay();
        String purpose=payment.getPurpose();
        double mount=payment.getAmount();
        String sql1="insert into "+table+" (year,month,day,purpose,way,mount) ";
        String sql2="values("+year+","+month+","+day+",'"+purpose+"','"+way+"',"+mount+")";
        String sql=sql1+sql2;
        sqLiteDatabase.execSQL(sql);
    }

    public User queryUserData(String table) {
        sqLiteDatabase=this.getReadableDatabase();
        String[] cloum=new String[]{"userid","password"};
        User userResult=new User();
        try{
            Cursor cursor=sqLiteDatabase.query(table, cloum,null, null, null, null, null);
            cursor.moveToFirst();
            userResult.setUserID(cursor.getString(cursor.getColumnIndex("userid")));
            userResult.setPassword(cursor.getString(cursor.getColumnIndex("password")));
        }catch (Exception e){
            return userResult;
        }
        return userResult;
    }
}
