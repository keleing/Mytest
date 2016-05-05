package com.example.liwei.mytest.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liwei.mytest.database.MyDataBase;
import com.example.liwei.mytest.R;
import com.example.liwei.mytest.adapter.MyRecordAdapter;
import com.example.liwei.mytest.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private ViewPagerAdapter pageradapter;
    private List<View> list;

    private LinearLayout layoutAccont;
    private LinearLayout layoutMyself;
    private ImageView imageViewAccount;
    private ImageView imageViewMyself;
    private TextView textViewAccount;
    private TextView textViewMyself;
    /**/
    private ListView listView;
    private MyRecordAdapter myRecordAdapter;
    private List<Map<String, Object>> listItem;
    private View pager_view1;
    private View pager_view2;

    private final static int PAGE_ACCOUNT=0;
    private final static int PAGE_MYSELF=1;
    /*总余额*/
    private TextView textViewAllCount;
    /*中间按钮*/
    private AlertDialog.Builder dialog;//布局变成View用到的
    private LayoutInflater inflater;
    /**中间按钮——银行对话框布局**/
    private LinearLayout layout_bank;
    private View view_bank;
    private EditText edJianBankmoney;
    private EditText edZhongBankmoney;
    private String mount_jianbank;
    private String mount_zhongbank;
    /**中间按钮——微信对话框布局**/
    private LinearLayout layout_weixin;
    private View view_weixin;
    private EditText edWeixin;
    private String mount_weixin;
    /**中间按钮——支付宝对话框布局**/
    private LinearLayout layout_zhifubao;
    private View view_zhifubao;
    private EditText edZhifubao;
    private EditText edYuebao;
    private String mount_zhifubao;
    private String mount_yuebao;
    /**中间按钮——钱包对话框布局**/
    private LinearLayout layout_wallet;
    private View view_wallet;
    private EditText edWallet;
    private String mount_wallet;

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
        setContentView(R.layout.activity_main);
        initialize();
    }


    private void initialize() {
        /*数据库*/
        datebase=new MyDataBase(this,DATABASE_NAME,null,1);
        /*底部控件*/
        layoutAccont= (LinearLayout) findViewById(R.id.layoutAccount);
        layoutAccont.setOnClickListener(this);
        layoutMyself= (LinearLayout) findViewById(R.id.layoutMyself);
        layoutMyself.setOnClickListener(this);
        viewPager= (ViewPager) findViewById(R.id.viewpager_content);
        imageViewAccount= (ImageView) findViewById(R.id.imageView_account);
        imageViewMyself= (ImageView) findViewById(R.id.imageView_myself);
        textViewAccount= (TextView) findViewById(R.id.textView_account);
        textViewMyself= (TextView) findViewById(R.id.textView_myself);
        /*pager*/
        LayoutInflater inflater=getLayoutInflater().from(this);
        pager_view1=inflater.inflate(R.layout.account, null);
        pager_view2=inflater.inflate(R.layout.myself,null);
        list=new ArrayList<View>();
        list.add(pager_view1);
        list.add(pager_view2);
        pageradapter=new ViewPagerAdapter(list);
        viewPager.setAdapter(pageradapter);
        viewPager.addOnPageChangeListener(this);
        /*pager1——顶部控件（总余额）*/
        textViewAllCount= (TextView) pager_view1.findViewById(R.id.tv_allCount);
        textViewAllCount.setText(String.format("%.2f", datebase.getAllMount()));
        /**pager1——中间控件**/
        layout_bank= (LinearLayout) pager_view1.findViewById(R.id.bank);
        layout_bank.setOnClickListener(this);
        layout_weixin= (LinearLayout) pager_view1.findViewById(R.id.weixin);
        layout_weixin.setOnClickListener(this);
        layout_zhifubao= (LinearLayout) pager_view1.findViewById(R.id.zhifubao);
        layout_zhifubao.setOnClickListener(this);
        layout_wallet= (LinearLayout) pager_view1.findViewById(R.id.wallet);
        layout_wallet.setOnClickListener(this);

        listView= (ListView) pager_view1.findViewById(R.id.listview_record);
        listItem=new ArrayList<Map<String, Object>>();
        listItem=getData();
        myRecordAdapter=new MyRecordAdapter(MainActivity.this,listItem);
        listView.setAdapter(myRecordAdapter);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layoutAccount:
                accountLayout();
                return;
            case R.id.layoutMyself:
                myselfLayout();
                return;
            case R.id.bank:
                bankClicked();
                return;
            case R.id.weixin:
                weixinClicked();
                return;
            case R.id.zhifubao:
                zhifubaoClicked();
                return;
            case R.id.wallet:
                walletClicked();
                return;
        }
    }

    private void walletClicked() {
        view_wallet=this.CreateView(R.layout.dialog_wallet);
        edWallet = (EditText) view_wallet.findViewById(R.id.wallet_ed_money);
        mount_wallet=edWallet.getText().toString();
        view_wallet.findViewById(R.id.iv_wallet_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_wallet.findViewById(R.id.wallet_ed_money).setEnabled(true);
            }
        });
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String currentMount=edWallet.getText().toString();
                if(mount_wallet.equals(currentMount)==false){
                    datebase.updateData(TABLE_WALLET_NAME,"mount",currentMount,TABLE_WALLET_ID);
                }
                view_wallet.findViewById(R.id.wallet_ed_money).setEnabled(false);
            }
        });
        dialog.setView(view_wallet);
        edWallet.setText(datebase.queryData(TABLE_WALLET_NAME,TABLE_WALLET_ID,"mount")+"");
        dialog.setTitle("钱包");
        dialog.show();
    }

    private void zhifubaoClicked() {
        view_zhifubao=this.CreateView(R.layout.dialog_zhifubao);
        /*支付宝*/
        edZhifubao = (EditText) view_zhifubao.findViewById(R.id.ed_zhifubao_money);
        mount_zhifubao=edZhifubao.getText().toString();
        view_zhifubao.findViewById(R.id.iv_zhifubao_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edZhifubao.setEnabled(true);
            }
        });
        /*余额宝*/
        edYuebao = (EditText) view_zhifubao.findViewById(R.id.ed_yuebao_money);
        mount_yuebao=edYuebao.getText().toString();
        view_zhifubao.findViewById(R.id.iv_yuebao_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edYuebao.setEnabled(true);
            }
        });

        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String currentZhifubaoMount = edZhifubao.getText().toString();
                String currentYuebaoMount = edYuebao.getText().toString();
                if (mount_zhifubao.equals(currentZhifubaoMount) == false) {
                    datebase.updateData(TABLE_ZHIFUBAO_NAME,"mount",currentZhifubaoMount,TABLE_ZHIFUBAO_ID);
                }
                if (mount_yuebao.equals(currentYuebaoMount) == false) {
                    datebase.updateData(TABLE_ZHIFUBAO_NAME,"mount",currentYuebaoMount,TABLE_YUEBAO_ID);
                }
                edZhifubao.setEnabled(false);
                edYuebao.setEnabled(false);
            }
        });
        dialog.setView(view_zhifubao);
        edZhifubao.setText(datebase.queryData(TABLE_ZHIFUBAO_NAME, TABLE_ZHIFUBAO_ID, "mount")+"");
        edYuebao.setText(datebase.queryData(TABLE_ZHIFUBAO_NAME, TABLE_YUEBAO_ID,"mount")+"");
        dialog.setTitle("支付宝");
        dialog.show();
    }

    private void weixinClicked() {
        view_weixin=this.CreateView(R.layout.dialog_weixin);
        edWeixin = (EditText) view_weixin.findViewById(R.id.weixin_ed_money);
        mount_weixin=edWeixin.getText().toString();
        view_weixin.findViewById(R.id.iv_weixin_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_weixin.findViewById(R.id.weixin_ed_money).setEnabled(true);
            }
        });
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String currentMount=edWeixin.getText().toString();
                if(mount_weixin.equals(currentMount)==false){
                    datebase.updateData(TABLE_WEIXIN_NAME, "mount", currentMount, TABLE_WEXIN_ID);
                }
                view_weixin.findViewById(R.id.weixin_ed_money).setEnabled(false);
            }
        });
        dialog.setView(view_weixin);
        edWeixin.setText(datebase.queryData(TABLE_WEIXIN_NAME, TABLE_WEXIN_ID,"mount")+"");
        dialog.setTitle("微信钱包");
        dialog.show();
    }

    private void bankClicked() {
        view_bank=this.CreateView(R.layout.dialog_bank);
        /*建行卡*/
        edJianBankmoney = (EditText) view_bank.findViewById(R.id.ed_jianbank_money);
        mount_jianbank=edJianBankmoney.getText().toString();
        view_bank.findViewById(R.id.iv_jianbank_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edJianBankmoney.setEnabled(true);
            }
        });
        /*中行卡*/
        edZhongBankmoney= (EditText) view_bank.findViewById(R.id.ed_zhongbank_money);
        mount_zhongbank=edZhongBankmoney.getText().toString();
        view_bank.findViewById(R.id.iv_zhongbank_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edZhongBankmoney.setEnabled(true);
            }
        });
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String currentJianBankMount = edJianBankmoney.getText().toString();
                if (mount_jianbank.equals(currentJianBankMount) == false) {
                    datebase.updateData(TABLE_BANK_NAME, "mount", currentJianBankMount, TABLE_JIANBANK_ID);
                }
                String currentZhongBankMount = edZhongBankmoney.getText().toString();
                if (mount_zhongbank.equals(currentZhongBankMount) == false) {
                    datebase.updateData(TABLE_BANK_NAME, "mount", currentZhongBankMount, TABLE_ZHONGBANK_ID);
                }

                edJianBankmoney.setEnabled(false);
                edZhongBankmoney.setEnabled(false);
            }
        });
        dialog.setView(view_bank);
        edJianBankmoney.setText(datebase.queryData(TABLE_BANK_NAME, TABLE_JIANBANK_ID, "mount")+"");
        edZhongBankmoney.setText(datebase.queryData(TABLE_BANK_NAME, TABLE_ZHONGBANK_ID, "mount")+"");
        dialog.setTitle("银行卡");
        dialog.show();

    }


    private void myselfLayout() {
        imageViewMyself.setImageResource(R.drawable.myself_clicked);
        textViewMyself.setTextColor(getResources().getColor(R.color.clicked));
        imageViewAccount.setImageResource(R.drawable.account);
        textViewAccount.setTextColor(getResources().getColor(R.color.unclicked));
        viewPager.setCurrentItem(PAGE_MYSELF);

    }


    private void accountLayout() {
        imageViewAccount.setImageResource(R.drawable.account_clicked);
        textViewAccount.setTextColor(getResources().getColor(R.color.clicked));
        imageViewMyself.setImageResource(R.drawable.myself);
        textViewMyself.setTextColor(getResources().getColor(R.color.unclicked));
        viewPager.setCurrentItem(PAGE_ACCOUNT);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(position==PAGE_ACCOUNT){
            imageViewAccount.setImageResource(R.drawable.account_clicked);
            textViewAccount.setTextColor(getResources().getColor(R.color.clicked));
            imageViewMyself.setImageResource(R.drawable.myself);
            textViewMyself.setTextColor(getResources().getColor(R.color.unclicked));
        }else if(position==PAGE_MYSELF){
            imageViewMyself.setImageResource(R.drawable.myself_clicked);
            textViewMyself.setTextColor(getResources().getColor(R.color.clicked));
            imageViewAccount.setImageResource(R.drawable.account);
            textViewAccount.setTextColor(getResources().getColor(R.color.unclicked));
        }
    }

    @Override
    public void onPageSelected(int position) {}
    @Override
    public void onPageScrollStateChanged(int state) {}

    public List<Map<String, Object>> getData() {
        List<Map<String,Object>> data=new ArrayList<Map<String, Object>>();
        for(int i=0;i<10;i++){
            Map<String,Object> map= new HashMap<>();
            map.put("title","1");
            map.put("image",R.drawable.bank);
            map.put("time",1995);
            data.add(map);
        }
        return data;
    }

    private View CreateView(int layoutId) {
        dialog=new AlertDialog.Builder(this);
        inflater=LayoutInflater.from(this);
        return  inflater.inflate(layoutId, null);
    }
}
