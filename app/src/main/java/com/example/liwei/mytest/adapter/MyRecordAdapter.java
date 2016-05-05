package com.example.liwei.mytest.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.liwei.mytest.R;

import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * Created by LiWei on 2016/4/25.
 */
public class MyRecordAdapter extends BaseAdapter {
    private List<Map<String,Object>> list;
    private Context context;
    private LayoutInflater inflater;
    public MyRecordAdapter(Context context,List<Map<String,Object>> list){
        this.context=context;
        this.list=list;
        this.inflater=LayoutInflater.from(this.context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    public final class ZuJian{
        public ImageView image;
        public TextView title;
        public TextView time;
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
        ZuJian zujian=null;
        if(convertView==null){
            zujian=new ZuJian();
            convertView=inflater.inflate(R.layout.item,null);
            zujian.image = (ImageView) convertView.findViewById(R.id.item_image);
            zujian.title= (TextView) convertView.findViewById(R.id.item_title);
            zujian.time= (TextView) convertView.findViewById(R.id.item_time);
            convertView.setTag(zujian);
        }else{
            zujian= (ZuJian) convertView.getTag();
        }
        zujian.image.setImageResource((Integer) list.get(position).get("image"));
        zujian.title.setText(list.get(position).get("title").toString());
        zujian.time.setText(list.get(position).get("time").toString());
        return convertView;
    }

}
