package com.example.liwei.mytest.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.liwei.mytest.R;
import com.example.liwei.mytest.ui.IncomeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by LiWei on 2016/5/18.
 */
public class MySpannerAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private LayoutInflater inflater;
    private ImageView image;
    private TextView text;
    public MySpannerAdapter(Context context){
        this.context=context;
        list=new ArrayList<>();
        list.add("中行卡");
        list.add("建行卡");
        list.add("微信");
        list.add("支付宝");
        list.add("余额宝");
        list.add("钱包");
    }
    MySpannerAdapter(List<String> list){
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.spinner_item,null);
        image= (ImageView) view.findViewById(R.id.Iv_spinnerImage);
        text= (TextView) view.findViewById(R.id.tv_spinnerText);
        text.setText(list.get(position));
        return view;
    }
}
