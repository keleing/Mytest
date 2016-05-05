package com.example.liwei.mytest.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.VisibleForTesting;

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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop if table exists "+TABLE_BANK_NAME);
        db.execSQL("drop if table exists "+TABLE_WEIXIN_NAME);
        db.execSQL("drop if table exists "+TABLE_ZHIFUBAO_NAME);
        db.execSQL("drop if table exists "+TABLE_WALLET_NAME);
        onCreate(db);
    }
    public void updateData(String tableName,String row,String newValue,int id){
        sqLiteDatabase=this.getWritableDatabase();
        String sql="update "+tableName+" set "+row+"="+newValue+" where id="+id;
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
    public float getAllMount(){
        float jianBankMount=0.00f,zhongBankMount=0.00f,weixinMount=0.00f,zhifubaoMount=0.00f,yuebaoMount=0.00f,walletMount=0.00f;
        float allMount1;
        jianBankMount= this.queryData(TABLE_BANK_NAME, TABLE_JIANBANK_ID, "mount");
        zhongBankMount= this.queryData(TABLE_BANK_NAME, TABLE_ZHONGBANK_ID, "mount");
        weixinMount= this.queryData(TABLE_WEIXIN_NAME, TABLE_WEXIN_ID,"mount");
        zhifubaoMount= this.queryData(TABLE_ZHIFUBAO_NAME, TABLE_ZHIFUBAO_ID, "mount");
        yuebaoMount=this.queryData(TABLE_ZHIFUBAO_NAME, TABLE_YUEBAO_ID,"mount");
        walletMount= this.queryData(TABLE_WALLET_NAME,TABLE_WALLET_ID,"mount");
        allMount1=jianBankMount+zhongBankMount+weixinMount+zhifubaoMount+yuebaoMount+walletMount;
        return allMount1;
    }
}
