package com.example.liwei.mytest.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.VisibleForTesting;

import com.example.liwei.mytest.constant.DataConstant;
import com.example.liwei.mytest.entity.Income;
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

    public MyDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //银行表
        String sqlCreateTableBank="create table "+DataConstant.TABLE_BANK_NAME+" (id int,mount NUMERIC(10,2))";
        db.execSQL(sqlCreateTableBank);
        sqlCreateTableBank="insert into "+DataConstant.TABLE_BANK_NAME+" values(1,25.0) ";
        db.execSQL(sqlCreateTableBank);
        sqlCreateTableBank="insert into "+DataConstant.TABLE_BANK_NAME+" values(2,25.0) ";
        db.execSQL(sqlCreateTableBank);
        //微信表
        String sqlCreateTableWeixin="create table "+DataConstant.TABLE_WEIXIN_NAME+" (id int,mount NUMERIC(10,2))";
        db.execSQL(sqlCreateTableWeixin);
        sqlCreateTableWeixin="insert into "+DataConstant.TABLE_WEIXIN_NAME+" values(1,25.0) ";
        db.execSQL(sqlCreateTableWeixin);
        //支付宝表
        String sqlCreateTableZhifubao="create table "+DataConstant.TABLE_ZHIFUBAO_NAME+" (id int,mount NUMERIC(10,2))";
        db.execSQL(sqlCreateTableZhifubao);
        sqlCreateTableZhifubao="insert into "+DataConstant.TABLE_ZHIFUBAO_NAME+" values(1,25.0) ";
        db.execSQL(sqlCreateTableZhifubao);
        sqlCreateTableZhifubao="insert into "+DataConstant.TABLE_ZHIFUBAO_NAME+" values(2,25.0) ";
        db.execSQL(sqlCreateTableZhifubao);
        //钱包表
        String sqlCreateTableWallet="create table "+DataConstant.TABLE_WALLET_NAME+" (id int,mount NUMERIC(10,2))";
        db.execSQL(sqlCreateTableWallet);
        sqlCreateTableWallet="insert into "+DataConstant.TABLE_WALLET_NAME+" values(1,25.0) ";
        db.execSQL(sqlCreateTableWallet);
        //支付方式表
        String sqlCreateTablePayment="create table "+DataConstant.TABLE_PAYMENT_NAME+" (year int,month int,day int,purpose TEXT,way TEXT,mount NUMERIC(10,2))";
        db.execSQL(sqlCreateTablePayment);
        sqlCreateTablePayment="insert into "+DataConstant.TABLE_PAYMENT_NAME+" values(1,2,3,4,5,6) ";
        db.execSQL(sqlCreateTablePayment);
        //用户表
        String sqlCreateTableUser="create table "+DataConstant.TABLE_USER_NAME+" (userid text,password text)";
        db.execSQL(sqlCreateTableUser);
        sqlCreateTableUser="insert into "+DataConstant.TABLE_USER_NAME+" values('mytest','123') ";
        db.execSQL(sqlCreateTableUser);
        //收入表
        String sqlCreateTableIncome="create table "+ DataConstant.TABLE_Income_NAME+" (year int,month int,day int,source TEXT,destination TEXT,amount NUMERIC(10,2))";
        db.execSQL(sqlCreateTableIncome);
        sqlCreateTableIncome="insert into "+DataConstant.TABLE_Income_NAME+" values(1,2,3,4,5,6) ";
        db.execSQL(sqlCreateTableIncome);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop if table exists "+DataConstant.TABLE_BANK_NAME);
        db.execSQL("drop if table exists "+DataConstant.TABLE_WEIXIN_NAME);
        db.execSQL("drop if table exists "+DataConstant.TABLE_ZHIFUBAO_NAME);
        db.execSQL("drop if table exists "+DataConstant.TABLE_WALLET_NAME);
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
        Cursor cursor=sqLiteDatabase.query(DataConstant.TABLE_PAYMENT_NAME, row, null, null, null, null, null);
        cursor.moveToFirst();
        for(int i=0;i<cursor.getCount();i++){
            Payment payment=new Payment();
            payment.setYear(cursor.getInt(cursor.getColumnIndex("year")));
            payment.setMonth(cursor.getInt(cursor.getColumnIndex("month")));
            payment.setDay(cursor.getInt(cursor.getColumnIndex("day")));
            payment.setPurpose(cursor.getString(cursor.getColumnIndex("purpose")));
            payment.setWay(cursor.getString(cursor.getColumnIndex("way")));
            payment.setAmount(cursor.getFloat(cursor.getColumnIndex("mount")));
            listPayment.add(payment);
            cursor.moveToNext();
        }
        return listPayment;
    }
    public List<Income> queryIncomeData() {
        List<Income> listIncome=new ArrayList<Income>();
        sqLiteDatabase=this.getReadableDatabase();
        String[] row=new String[]{"year","month","day","source","destination","amount"};
        Cursor cursor=sqLiteDatabase.query(DataConstant.TABLE_Income_NAME, row, null, null, null, null, null);
        cursor.moveToFirst();
        for(int i=0;i<cursor.getCount();i++){
            Income income=new Income();
            income.setYear(cursor.getInt(cursor.getColumnIndex("year")));
            income.setMonth(cursor.getInt(cursor.getColumnIndex("month")));
            income.setDay(cursor.getInt(cursor.getColumnIndex("day")));
            income.setSource(cursor.getString(cursor.getColumnIndex("source")));
            income.setDestination(cursor.getString(cursor.getColumnIndex("destination")));
            income.setAmount(cursor.getFloat(cursor.getColumnIndex("amount")));
            listIncome.add(income);
            cursor.moveToNext();
        }
        return listIncome;
    }
    public float getAllMount(){
        float jianBankMount=0.00f,zhongBankMount=0.00f,weixinMount=0.00f,zhifubaoMount=0.00f,yuebaoMount=0.00f,walletMount=0.00f;
        float allMount1;
        jianBankMount= this.queryData(DataConstant.TABLE_BANK_NAME, DataConstant.TABLE_JIANBANK_ID, "mount");
        zhongBankMount= this.queryData(DataConstant.TABLE_BANK_NAME, DataConstant.TABLE_ZHONGBANK_ID, "mount");
        weixinMount= this.queryData(DataConstant.TABLE_WEIXIN_NAME, DataConstant.TABLE_WEXIN_ID,"mount");
        zhifubaoMount= this.queryData(DataConstant.TABLE_ZHIFUBAO_NAME, DataConstant.TABLE_ZHIFUBAO_ID, "mount");
        yuebaoMount=this.queryData(DataConstant.TABLE_ZHIFUBAO_NAME, DataConstant.TABLE_YUEBAO_ID, "mount");
        walletMount= this.queryData(DataConstant.TABLE_WALLET_NAME,DataConstant.TABLE_WALLET_ID,"mount");
        allMount1=jianBankMount+zhongBankMount+weixinMount+zhifubaoMount+yuebaoMount+walletMount;
        return allMount1;
    }
    public void addPayData(String table,Payment payment){
        sqLiteDatabase=this.getWritableDatabase();
        int year=payment.getYear();
        int month=payment.getMonth();
        int day=payment.getDay();
        String way=payment.getWay();
        String purpose=payment.getPurpose();
        double mount=payment.getAmount();
        String sql1="insert into "+table+" (year,month,day,purpose,way,mount) ";
        String sql2="values("+year+","+month+","+day+",'"+purpose+"','"+way+"',"+mount+")";
        String sql=sql1+sql2;
        sqLiteDatabase.execSQL(sql);
    }
    public void addIncomeData(String table,Income income){
        sqLiteDatabase=this.getWritableDatabase();
        int year=income.getYear();
        int month=income.getMonth();
        int day=income.getDay();
        String source=income.getSource();
        String destination=income.getDestination();
        float amount=income.getAmount();
        String sql1="insert into "+table+" (year,month,day,source,destination,amount) ";
        String sql2="values("+year+","+month+","+day+",'"+source+"','"+destination+"',"+amount+")";
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
