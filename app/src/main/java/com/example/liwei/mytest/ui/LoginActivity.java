package com.example.liwei.mytest.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.liwei.mytest.R;
import com.example.liwei.mytest.constant.DataConstant;
import com.example.liwei.mytest.database.MyDataBase;
import com.example.liwei.mytest.entity.MyDate;
import com.example.liwei.mytest.entity.User;

import java.io.InputStream;
import java.sql.DatabaseMetaData;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivHeadImage;
    private Button btnLoginOk;
    private EditText edUserID;
    private EditText edPassword;

    private MyDataBase datebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initialize();



    }

    private void initialize() {
        ivHeadImage= (ImageView) findViewById(R.id.iv_headImage);//头像Imageview
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.background);//获取头像资源
        Bitmap newBitmap=toRoundBitmap(bitmap);//转化成圆形头像
        ivHeadImage.setImageBitmap(newBitmap);

        btnLoginOk= (Button) findViewById(R.id.btn_loginOk);//登陆按钮
        btnLoginOk.setOnClickListener(this);

        edUserID= (EditText) findViewById(R.id.ed_userID);//账号
        edPassword= (EditText) findViewById(R.id.ed_password);//密码

        datebase=new MyDataBase(this, DataConstant.DATABASE_NAME,null,1);
    }

    public Bitmap toRoundBitmap(Bitmap bitmap) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_loginOk:
                this.loginClicked();
                break;
        }
    }

    private void loginClicked() {
        String userId,password;
        userId=edUserID.getText().toString();
        password=edPassword.getText().toString();
        if(IsLoginSucceed(userId,password)){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            this.finish();
        }else {
            Toast.makeText(this,"账号与密码不匹配",Toast.LENGTH_SHORT).show();
        }

    }

    private boolean IsLoginSucceed(String userId, String password) {
        User user=datebase.queryUserData(DataConstant.TABLE_USER_NAME);
        if(user.getUserID().equals(userId)&&user.getPassword().equals(password)){
            return true;
        }else{
            return false;
        }

    }
}
