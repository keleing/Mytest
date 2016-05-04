package com.example.liwei.mytest.adapter;

import android.graphics.pdf.PdfRenderer;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by LiWei on 2016/4/25.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> myListViewPager;

   public ViewPagerAdapter(List<View> list){
       this.myListViewPager=list;
   }
    @Override
    public int getCount() {
        return myListViewPager.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(myListViewPager.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(myListViewPager.get(position));
        return myListViewPager.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==object);
    }
}
