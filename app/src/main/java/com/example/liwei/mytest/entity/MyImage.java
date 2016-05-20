package com.example.liwei.mytest.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

/**
 * Created by LiWei on 2016/5/20.
 */
public class MyImage {

    public MyImage(){}

    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();//圆形图片宽高
        int height = bitmap.getHeight();

        int r = 0; //正方形的边长

        if(width > height) {//取最短边做边长
            r = height;
        } else {
            r = width;
        }
        //构建一个bitmap
        Bitmap backgroundBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(backgroundBmp);//new一个Canvas，在backgroundBmp上画图
        Paint paint = new Paint();
        paint.setAntiAlias(true); //设置边缘光滑，去掉锯齿
        RectF rect = new RectF(0, 0, r, r);//宽高相等，即正方形
        /*通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
        且都等于r/2时，画出来的圆角矩形就是圆形*/
        canvas.drawRoundRect(rect, r / 2, r / 2, paint);
        //设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(bitmap, null, rect, paint);
        //返回已经绘画好的backgroundBmp
        return backgroundBmp;
    }
}
