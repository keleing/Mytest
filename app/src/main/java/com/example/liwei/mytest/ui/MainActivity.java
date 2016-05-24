package com.example.liwei.mytest.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liwei.mytest.constant.DataConstant;
import com.example.liwei.mytest.database.MyDataBase;
import com.example.liwei.mytest.R;
import com.example.liwei.mytest.adapter.MyRecordAdapter;
import com.example.liwei.mytest.adapter.ViewPagerAdapter;
import com.example.liwei.mytest.entity.Income;
import com.example.liwei.mytest.entity.MyImage;
import com.example.liwei.mytest.entity.Payment;

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

    /*侧拉菜单*/
    private NavigationView navigationView;
    private LinearLayout layoutMenuHead;
    private ImageView menuImageHead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_and_menu);
        initialize();
    }


    private void initialize() {
        /*数据库*/
        datebase=new MyDataBase(this, DataConstant.DATABASE_NAME,null,1);
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
        reflushData();

        /*侧拉菜单*/
        navigationView= (NavigationView) findViewById(R.id.nav_view);
        layoutMenuHead= (LinearLayout) navigationView.getHeaderView(0);
        menuImageHead= (ImageView) layoutMenuHead.findViewById(R.id.iv_menuHeadImage);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.background);
        bitmap=MyImage.toRoundBitmap(bitmap);
        menuImageHead.setImageBitmap(bitmap);
        menuImageHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"123",Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0,0,"支出");
        menu.add(0, 1, 1, "收入");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                Intent intent=new Intent(MainActivity.this,PayActivity.class);
                startActivityForResult(intent,0);
                break;
            case 1:
                Intent intent1=new Intent(MainActivity.this,IncomeActivity.class);
                startActivityForResult(intent1,1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        reflushData();
        super.onActivityResult(requestCode, resultCode, data);
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
                String currentMount = edWallet.getText().toString();
                if (mount_wallet.equals(currentMount) == false) {
                    datebase.updateDataNewAmount(DataConstant.TABLE_WALLET_NAME, "mount", currentMount, DataConstant.TABLE_WALLET_ID);
                }
                view_wallet.findViewById(R.id.wallet_ed_money).setEnabled(false);
            }
        });
        dialog.setView(view_wallet);
        edWallet.setText(datebase.queryData(DataConstant.TABLE_WALLET_NAME, DataConstant.TABLE_WALLET_ID, "mount") + "");
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
                    datebase.updateDataNewAmount(DataConstant.TABLE_ZHIFUBAO_NAME, "mount", currentZhifubaoMount, DataConstant.TABLE_ZHIFUBAO_ID);
                }
                if (mount_yuebao.equals(currentYuebaoMount) == false) {
                    datebase.updateDataNewAmount(DataConstant.TABLE_ZHIFUBAO_NAME, "mount", currentYuebaoMount, DataConstant.TABLE_YUEBAO_ID);
                }
                edZhifubao.setEnabled(false);
                edYuebao.setEnabled(false);
            }
        });
        dialog.setView(view_zhifubao);
        edZhifubao.setText(datebase.queryData(DataConstant.TABLE_ZHIFUBAO_NAME, DataConstant.TABLE_ZHIFUBAO_ID, "mount") + "");
        edYuebao.setText(datebase.queryData(DataConstant.TABLE_ZHIFUBAO_NAME, DataConstant.TABLE_YUEBAO_ID,"mount")+"");
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
                String currentMount = edWeixin.getText().toString();
                if (mount_weixin.equals(currentMount) == false) {
                    datebase.updateDataNewAmount(DataConstant.TABLE_WEIXIN_NAME, "mount", currentMount, DataConstant.TABLE_WEXIN_ID);
                }
                view_weixin.findViewById(R.id.weixin_ed_money).setEnabled(false);
            }
        });
        dialog.setView(view_weixin);
        edWeixin.setText(datebase.queryData(DataConstant.TABLE_WEIXIN_NAME, DataConstant.TABLE_WEXIN_ID,"mount")+"");
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
                    datebase.updateDataNewAmount(DataConstant.TABLE_BANK_NAME, "mount", currentJianBankMount, DataConstant.TABLE_JIANBANK_ID);
                }
                String currentZhongBankMount = edZhongBankmoney.getText().toString();
                if (mount_zhongbank.equals(currentZhongBankMount) == false) {
                    datebase.updateDataNewAmount(DataConstant.TABLE_BANK_NAME, "mount", currentZhongBankMount, DataConstant.TABLE_ZHONGBANK_ID);
                }

                edJianBankmoney.setEnabled(false);
                edZhongBankmoney.setEnabled(false);
            }
        });
        dialog.setView(view_bank);
        edJianBankmoney.setText(datebase.queryData(DataConstant.TABLE_BANK_NAME, DataConstant.TABLE_JIANBANK_ID, "mount") + "");
        edZhongBankmoney.setText(datebase.queryData(DataConstant.TABLE_BANK_NAME, DataConstant.TABLE_ZHONGBANK_ID, "mount") + "");
        dialog.setTitle("银行卡");
        dialog.show();

    }


    private void myselfLayout() {
        viewPager.setCurrentItem(PAGE_MYSELF);
        imageViewMyself.setImageResource(R.drawable.myself_clicked);
        textViewMyself.setTextColor(getResources().getColor(R.color.clicked));
        imageViewAccount.setImageResource(R.drawable.account);
        textViewAccount.setTextColor(getResources().getColor(R.color.unclicked));
    }


    private void accountLayout() {
        viewPager.setCurrentItem(PAGE_ACCOUNT);
        imageViewAccount.setImageResource(R.drawable.account_clicked);
        textViewAccount.setTextColor(getResources().getColor(R.color.clicked));
        imageViewMyself.setImageResource(R.drawable.myself);
        textViewMyself.setTextColor(getResources().getColor(R.color.unclicked));

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
        }else{
        }
    }

    @Override
    public void onPageSelected(int position) {}
    @Override
    public void onPageScrollStateChanged(int state) {}

    public List<Map<String, Object>> getData() {
        List<Payment> listPayment=datebase.queryPayData();
        List<Income> listIncome=datebase.queryIncomeData();
        List<Map<String,Object>> data=new ArrayList<Map<String, Object>>();
        if(listPayment.size()!=0) {
            for (int i = 0; i < listPayment.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("title", listPayment.get(i).getPurpose() + "\n(共花费)：" + String.format("%.2f", listPayment.get(i).getAmount()));
                switch (listPayment.get(i).getWay()) {
                    case "中行卡":
                        map.put("image", R.drawable.bank);
                        break;
                    case "建行卡":
                        map.put("image", R.drawable.bank);
                        break;
                    case "微信":
                        map.put("image", R.drawable.weixin);
                        break;
                    case "支付宝":
                        map.put("image", R.drawable.zhifubao);
                        break;
                    case "余额宝":
                        map.put("image", R.drawable.zhifubao);
                        break;
                    case "钱包":
                        map.put("image", R.drawable.wallet);
                        break;
                    default:
                        map.put("image", R.drawable.wallet);
                }

                map.put("time", listPayment.get(i).getYear() + "年"
                        + listPayment.get(i).getMonth() + "月"
                        + listPayment.get(i).getDay() + "日"
                        + listPayment.get(i).getHour()+":"
                        + listPayment.get(i).getMinute());
                data.add(map);
            }
        }
        if(listIncome.size()!=0){
            for(int i=0;i<listIncome.size();i++){
                Map<String,Object> map= new HashMap<>();
                map.put("title",listIncome.get(i).getSource()+"\n(数量)："+String.format("%.2f", listIncome.get(i).getAmount()));
                switch (listIncome.get(i).getDestination()){
                    case "中行卡":
                        map.put("image",R.drawable.bank);
                        break;
                    case "建行卡":
                        map.put("image",R.drawable.bank);
                        break;
                    case "微信":
                        map.put("image",R.drawable.weixin);
                        break;
                    case "支付宝":
                        map.put("image",R.drawable.zhifubao);
                        break;
                    case "余额宝":
                        map.put("image",R.drawable.zhifubao);
                        break;
                    case "钱包":
                        map.put("image",R.drawable.wallet);
                        break;
                    default:
                        map.put("image",R.drawable.wallet);
                }
                map.put("time", listIncome.get(i).getYear() + "年"
                        + listIncome.get(i).getMonth() + "月"
                        + listIncome.get(i).getDay()+"日"
                        + listIncome.get(i).getHour()+":"
                        + listIncome.get(i).getMinute());
                data.add(map);
            }

        }
        return data;
    }

    private View CreateView(int layoutId) {
        dialog=new AlertDialog.Builder(this);
        inflater=LayoutInflater.from(this);
        return  inflater.inflate(layoutId, null);
    }

    public void reflushData() {
        textViewAllCount.setText(String.format("%.2f", datebase.getAllMount()));
        listItem=getData();;
        myRecordAdapter=new MyRecordAdapter(MainActivity.this,listItem);
        listView.setAdapter(myRecordAdapter);
    }

}
