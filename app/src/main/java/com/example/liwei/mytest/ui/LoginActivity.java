package com.example.liwei.mytest.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.liwei.mytest.entity.MyImage;
import com.example.liwei.mytest.entity.User;

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
        Bitmap newBitmap= MyImage.toRoundBitmap(bitmap);//转化成圆形头像
        ivHeadImage.setImageBitmap(newBitmap);

        btnLoginOk= (Button) findViewById(R.id.btn_loginOk);//登陆按钮
        btnLoginOk.setOnClickListener(this);

        edUserID= (EditText) findViewById(R.id.ed_userID);//账号
        edPassword= (EditText) findViewById(R.id.ed_password);//密码

        datebase=new MyDataBase(this, DataConstant.DATABASE_NAME,null,1);
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
