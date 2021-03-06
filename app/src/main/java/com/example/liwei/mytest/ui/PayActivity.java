package com.example.liwei.mytest.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.liwei.mytest.R;
import com.example.liwei.mytest.constant.DataConstant;
import com.example.liwei.mytest.database.MyDataBase;
import com.example.liwei.mytest.entity.MyDate;
import com.example.liwei.mytest.entity.Payment;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PayActivity extends Activity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Payment payment;  //支付

    private Button btnPayDate;
    private EditText edPayYear;//显示当前时间
    private EditText edPayMonth;
    private EditText edPayDay;
    private Button btnPayTime;
    private EditText edPayHour;
    private EditText edPayMinute;

    private EditText edPayPurpose;//支付目的
    private Spinner spinner;//支付方式
    private SpinnerAdapter adapter;
    private List<String> listPayWay;
    private EditText edPayAmount;//支付数量
    private Button btnPaySave;

    private MyDataBase dataBase;

    /*数据库*/
    private MyDataBase datebase;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_layout);
        initialize();
    }

    private void initialize() {

        payment=new Payment();
        /*支付时间*/
        btnPayDate= (Button) findViewById(R.id.btn_payDate);
        btnPayDate.setOnClickListener(this);
        edPayYear= (EditText) findViewById(R.id.ed_payYear);
        edPayMonth= (EditText) findViewById(R.id.ed_payMonth);
        edPayDay= (EditText) findViewById(R.id.ed_payDay);
        btnPayTime= (Button) findViewById(R.id.btn_payTime);
        btnPayTime.setOnClickListener(this);
        edPayHour= (EditText) findViewById(R.id.ed_payHour);
        edPayMinute= (EditText) findViewById(R.id.ed_payMinute);
        /*支付目的*/
        edPayPurpose= (EditText) findViewById(R.id.ed_payPurpose);
        /*支付方式*/
        listPayWay=new ArrayList<>();
        listPayWay.add("中行卡");
        listPayWay.add("建行卡");
        listPayWay.add("微信");
        listPayWay.add("支付宝");
        listPayWay.add("余额宝");
        listPayWay.add("钱包");
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listPayWay);
        spinner= (Spinner) findViewById(R.id.spinner_payWay);
        spinner.setAdapter(adapter);
        /*支付数量*/
        edPayAmount= (EditText) findViewById(R.id.ed_payAmount);
        /*支付保存*/
        btnPaySave= (Button) findViewById(R.id.btn_paySave);
        btnPaySave.setOnClickListener(this);
        /*数据库*/
        dataBase=new MyDataBase(this,DATABASE_NAME,null,1);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_payDate:
                selectDateClicked();
                break;
            case R.id.btn_payTime:
                selectTimeClicked();
                break;
            case R.id.btn_paySave:
                savePaymentClicked();
                break;

        }
    }

    private void selectDateClicked() {
        Calendar calendar=Calendar.getInstance();
        int nowYear=calendar.get(calendar.YEAR);
        int nowMonth=calendar.get(calendar.MONTH);
        int nowDay=calendar.get(calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this,this,nowYear, nowMonth, nowDay);
        datePickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        edPayYear.setText(year + "");
        edPayMonth.setText((monthOfYear + 1) + "");
        edPayDay.setText(dayOfMonth + "");
    }

    private void selectTimeClicked() {
        Calendar calendar=Calendar.getInstance();
        int nowHour=calendar.get(calendar.HOUR_OF_DAY);
        int nowMinute=calendar.get(calendar.MINUTE);
        TimePickerDialog timePickerDialog=new TimePickerDialog(this,this,nowHour,nowMinute,true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(hourOfDay<10){
            edPayHour.setText("0"+hourOfDay);
        }else{
            edPayHour.setText(hourOfDay+"");
        }

        if(minute<10){
            edPayMinute.setText("0"+minute);
        }else{
            edPayMinute.setText(minute+"");
        }


    }


    private void savePaymentClicked() {
        if(edPayYear.getText().toString().equals("")){
            Toast.makeText(this,"日期不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(edPayHour.getText().toString().equals("")){
            Toast.makeText(this,"时间不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(edPayPurpose.getText().toString().equals("")){
            Toast.makeText(this,"请填写用途",Toast.LENGTH_SHORT).show();
            return;
        }
        if(edPayAmount.getText().toString().equals("")){
            Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
            return;
        }
        payment.setYear(Integer.parseInt(edPayYear.getText().toString()));//年
        payment.setMonth(Integer.parseInt(edPayMonth.getText().toString()));//月
        payment.setDay(Integer.parseInt(edPayDay.getText().toString()));//日
        payment.setHour(Integer.parseInt(edPayHour.getText().toString()));//小时
        payment.setMinute(Integer.parseInt(edPayMinute.getText().toString()));//分钟

        payment.setPurpose(edPayPurpose.getText().toString());
        payment.setWay(spinner.getSelectedItem().toString());
        payment.setAmount(Float.parseFloat(edPayAmount.getText().toString()));
        try{
            dataBase.addPayData(DataConstant.TABLE_PAYMENT_NAME, payment);
            reduceAmount(spinner.getSelectedItem().toString(), edPayAmount.getText().toString());
            Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
            this.finish();
        }catch (Exception e){
            Toast.makeText(this,"保存失败",Toast.LENGTH_SHORT).show();
        }


    }



    private void reduceAmount(String way,String amount) {
        switch (way){
            case "建行卡":
                dataBase.updateDataReduceAmount(TABLE_BANK_NAME, "mount", amount, TABLE_JIANBANK_ID);
                break;
            case "中行卡":
                dataBase.updateDataReduceAmount(TABLE_BANK_NAME, "mount", amount, TABLE_ZHONGBANK_ID);
                break;
            case "微信":
                dataBase.updateDataReduceAmount(TABLE_WEIXIN_NAME, "mount", amount, TABLE_WEXIN_ID);
                break;
            case "支付宝":
                dataBase.updateDataReduceAmount(TABLE_ZHIFUBAO_NAME, "mount", amount, TABLE_ZHIFUBAO_ID);
                break;
            case "余额宝":
                dataBase.updateDataReduceAmount(TABLE_ZHIFUBAO_NAME, "mount", amount, TABLE_YUEBAO_ID);
                break;
            default:
                dataBase.updateDataReduceAmount(TABLE_WALLET_NAME, "mount", amount, TABLE_WALLET_ID);
                break;

        }
    }



}
