package com.example.liwei.mytest.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.liwei.mytest.R;
import com.example.liwei.mytest.adapter.MySpannerAdapter;
import com.example.liwei.mytest.constant.DataConstant;
import com.example.liwei.mytest.database.MyDataBase;
import com.example.liwei.mytest.entity.Income;
import com.example.liwei.mytest.entity.MyDate;

import org.w3c.dom.Text;

import java.util.Calendar;

public class IncomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnIncomeDate,btnIncomeTime;//时间
    private EditText edIncomeYear,edIncomeMonth,edIncomeDay,edIncomeHour,edIncomeMinute;

    private EditText edIncomeSource;//来源

    private Spinner spIncomeDestination;//类型
    private MySpannerAdapter adapter;

    private EditText edIncomeAmount;//数量
    private Button btnIncomeSave;

    private MyDataBase dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_layout);
        initialize();
    }

    private void initialize() {
        btnIncomeDate= (Button) findViewById(R.id.btn_incomeDate);//时间
        btnIncomeDate.setOnClickListener(this);
        edIncomeYear= (EditText) findViewById(R.id.ed_incomeYear);
        edIncomeMonth= (EditText) findViewById(R.id.ed_incomeMonth);
        edIncomeDay= (EditText) findViewById(R.id.ed_incomeDay);
        btnIncomeTime= (Button) findViewById(R.id.btn_incomeTime);
        btnIncomeTime.setOnClickListener(this);
        edIncomeHour= (EditText) findViewById(R.id.ed_incomeHour);
        edIncomeMinute= (EditText) findViewById(R.id.ed_incomeMinute);

        edIncomeSource= (EditText) findViewById(R.id.ed_incomeSource);//收入来源

        spIncomeDestination= (Spinner) findViewById(R.id.spinner_IncomeDestination);//收入去向
        adapter=new MySpannerAdapter(this);
        spIncomeDestination.setAdapter(adapter);

        edIncomeAmount= (EditText) findViewById(R.id.ed_IncomeAmount);//收入数量

        btnIncomeSave= (Button) findViewById(R.id.btn_incomeSave);//保存
        btnIncomeSave.setOnClickListener(this);

        dataBase=new MyDataBase(this, DataConstant.DATABASE_NAME,null,1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_incomeDate:
                incomeDateClicked();
                break;
            case R.id.btn_incomeTime:
                incomeTimeClicked();
                break;
            case R.id.btn_incomeSave:
                incomeSaveClicked();
                break;
        }
    }

    private void incomeDateClicked() {
        Calendar calendar=Calendar.getInstance();
        MyDate currentDate=new MyDate();
        currentDate.setYear(calendar.get(calendar.YEAR));
        currentDate.setMonth(calendar.get(calendar.MONTH));
        currentDate.setDay(calendar.get(calendar.DAY_OF_MONTH));
        DatePickerDialog datePickerDialog=new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                edIncomeYear.setText(year+"");
                edIncomeMonth.setText(monthOfYear+1+"");
                edIncomeDay.setText(dayOfMonth+"");
            }
        },
                currentDate.getYear(),
                currentDate.getMonth(),
                currentDate.getDay());
        datePickerDialog.show();
    }

    private void incomeTimeClicked() {
        Calendar calendar=Calendar.getInstance();
        MyDate myDate=new MyDate();
        myDate.setHour(calendar.get(calendar.HOUR_OF_DAY));
        myDate.setMinute(calendar.get(calendar.MINUTE));
        TimePickerDialog timePickerDialog=new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if(hourOfDay<10){
                    edIncomeHour.setText("0"+hourOfDay);
                }else{
                    edIncomeHour.setText(hourOfDay+"");
                }
                if(minute<10){
                    edIncomeMinute.setText("0"+minute);
                }else{
                    edIncomeMinute.setText(minute+"");
                }
            }
        },
                myDate.getHour(),
                myDate.getMinute(),
                true);
        timePickerDialog.show();
    }

    private void incomeSaveClicked() {
        if(edIncomeDay.getText().toString().equals("")){
            Toast.makeText(this,"请选择日期",Toast.LENGTH_SHORT).show();
            return;
        }
        if(edIncomeHour.getText().toString().equals("")){
            Toast.makeText(this,"请选择时间",Toast.LENGTH_SHORT).show();
            return;
        }
        if(edIncomeSource.getText().toString().equals("")){
            Toast.makeText(this,"请输入来源",Toast.LENGTH_SHORT).show();
            return;
        }
        if(edIncomeAmount.getText().toString().equals("")){
            Toast.makeText(this,"请输入数量",Toast.LENGTH_SHORT).show();
            return;
        }
        Income income=new Income();
        income.setYear(Integer.parseInt(edIncomeYear.getText().toString()));
        income.setMonth(Integer.parseInt(edIncomeMonth.getText().toString()));
        income.setDay(Integer.parseInt(edIncomeDay.getText().toString()));
        income.setHour(Integer.parseInt(edIncomeHour.getText().toString()));
        income.setMinute(Integer.parseInt(edIncomeMinute.getText().toString()));
        income.setSource(edIncomeSource.getText().toString());
        TextView textView= (TextView) spIncomeDestination.getSelectedView().findViewById(R.id.tv_spinnerText);
        income.setDestination(textView.getText().toString());
        income.setAmount(Float.parseFloat(edIncomeAmount.getText().toString()));
        try{
            dataBase.addIncomeData(DataConstant.TABLE_Income_NAME, income);
            addAmount(textView.getText().toString(), edIncomeAmount.getText().toString());
            Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
            this.finish();
        }catch (Exception e){
            Toast.makeText(this,"保存失败",Toast.LENGTH_SHORT).show();
        }
    }

    private void addAmount(String destination, String amount) {
        switch (destination){
            case "建行卡":
                dataBase.updateDataAddAmount(DataConstant.TABLE_BANK_NAME, "mount", amount, DataConstant.TABLE_JIANBANK_ID);
                break;
            case "中行卡":
                dataBase.updateDataAddAmount(DataConstant.TABLE_BANK_NAME, "mount", amount, DataConstant.TABLE_ZHONGBANK_ID);
                break;
            case "微信":
                dataBase.updateDataAddAmount(DataConstant.TABLE_WEIXIN_NAME, "mount", amount, DataConstant.TABLE_WEXIN_ID);
                break;
            case "支付宝":
                dataBase.updateDataAddAmount(DataConstant.TABLE_ZHIFUBAO_NAME, "mount", amount, DataConstant.TABLE_ZHIFUBAO_ID);
                break;
            case "余额宝":
                dataBase.updateDataAddAmount(DataConstant.TABLE_ZHIFUBAO_NAME, "mount", amount, DataConstant.TABLE_YUEBAO_ID);
                break;
            default:
                dataBase.updateDataAddAmount(DataConstant.TABLE_WALLET_NAME, "mount", amount, DataConstant.TABLE_WALLET_ID);
                break;
        }
    }
}
