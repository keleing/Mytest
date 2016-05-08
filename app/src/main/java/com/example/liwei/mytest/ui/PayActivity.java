package com.example.liwei.mytest.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.liwei.mytest.R;
import com.example.liwei.mytest.database.MyDataBase;
import com.example.liwei.mytest.entity.MyDate;
import com.example.liwei.mytest.entity.Payment;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PayActivity extends Activity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private Payment payment;  //支付
    private MyDate payDate;
    private Button btnPayTime;
    private EditText edPayYear;//显示当前时间
    private EditText edPayMonth;
    private EditText edPayDay;
    private EditText edPayPurpose;
    private Spinner spinner;//支付方式
    private SpinnerAdapter adapter;
    private List<String> listPayWay;
    private EditText edPayAmount;//支付数量
    private Button btnPaySave;
    private Button btnPayExit;

    private MyDataBase dataBase;
    private final static String DATABASE_NAME="mydatabase";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_layout);
        initialize();
    }

    private void initialize() {

        payment=new Payment();
        /*支付时间*/
        btnPayTime= (Button) findViewById(R.id.btn_payTime);
        btnPayTime.setOnClickListener(this);
        edPayYear= (EditText) findViewById(R.id.ed_payYear);
        edPayMonth= (EditText) findViewById(R.id.ed_payMonth);
        edPayDay= (EditText) findViewById(R.id.ed_payDay);
        payDate=new MyDate();
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
        btnPayExit= (Button) findViewById(R.id.btn_payExit);
        btnPayExit.setOnClickListener(this);
        /*数据库*/
        dataBase=new MyDataBase(this,DATABASE_NAME,null,1);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_payTime:
                selectTimeClicked();
                break;
            case R.id.btn_paySave:
                savePaymentClicked();
                break;
            case R.id.btn_payExit:
                exitPaymentClicked();
                break;
        }
    }
    private void selectTimeClicked() {
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

    private void savePaymentClicked() {
        payDate.setYear(Integer.parseInt(edPayYear.getText().toString()));
        payDate.setMonth(Integer.parseInt(edPayMonth.getText().toString()));
        payDate.setDay(Integer.parseInt(edPayDay.getText().toString()));
        payment.setTime(payDate);
        payment.setPurpose(edPayPurpose.getText().toString());
        payment.setWay(spinner.getSelectedItem().toString());
        payment.setAmount(Float.parseFloat(edPayAmount.getText().toString()));
        try{
            dataBase.addData("payment",payment);
            Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
            this.finish();
        }catch (Exception e){
            Toast.makeText(this,"保存失败",Toast.LENGTH_SHORT).show();
        }


    }

    private void exitPaymentClicked() {

    }




}
