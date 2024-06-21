package com.example.accountbook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import adapter.AccountAdapter;
import database.AccountBookHelper;
import javabean.Account;

public class HistoricalActivity extends AppCompatActivity implements View.OnClickListener {

    private String username;
    ListView hisLv;
    Button hisBtn_time, hisBtn_money;

    List<Account> mDatas;   // 声明数据源

    AccountAdapter adapter;

    View headerView;    // 头布局
    TextView his_topOut_tv, his_topIn_tv;   // 今日支出，今日收入, 预算剩余

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical);

        // 返回 点击事件
        findViewById(R.id.historical_iv_goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 从 Intent 中获取用户名
        username = getIntent().getStringExtra("username");

        // ListView
        hisLv = findViewById(R.id.historical_lv);

        // 添加ListView的头布局
        addLVHeaderView();

        hisBtn_time = findViewById(R.id.historical_btn_time);
        hisBtn_money = findViewById(R.id.historical_btn_money);

        hisBtn_time.setOnClickListener(this);
        hisBtn_money.setOnClickListener(this);

        mDatas = new ArrayList<>();

        // 设置适配器：加载每一行数据到列表当中
        adapter = new AccountAdapter(this, mDatas);
        hisLv.setAdapter(adapter);

        // 加载数据库数据到ListView中,按添加顺序（即id）展示
        loadDate();

        // 设置顶部总收入，总支出数据的显示
        setTopTvDate();

    }


    private void setTopTvDate() {
        float totalMoney_in = AccountBookHelper.getSumMoneyTotal_Out_In(username, 1);
        float totalMoney_out = AccountBookHelper.getSumMoneyTotal_Out_In(username, 0);
        String StrTotalMoney_in = "￥ " + totalMoney_in;
        String StrTotalMoney_out = "￥ " + totalMoney_out;
        his_topOut_tv.setText(StrTotalMoney_out);
        his_topIn_tv.setText(StrTotalMoney_in);

    }

    // 添加ListView的头布局的方法
    private void addLVHeaderView() {
        // 将布局转换为 View 对象
        headerView = getLayoutInflater().inflate(R.layout.historical_top, null);
        // 设置头布局
        hisLv.addHeaderView(headerView);
        // 查找头布局需要用到的控件
        his_topOut_tv = headerView.findViewById(R.id.historical_top_out);
        his_topIn_tv = headerView.findViewById(R.id.historical_top_in);
    }

    // 获取该用户的所有账单,按添加顺序（即id）展示
    private void loadDate() {
        List<Account> lst = AccountBookHelper.getAccountListAllFromAccountbook(username);
        mDatas.clear(); // 将原来的数据清空
        mDatas.addAll(lst); // 将 更新后 的数据添加进去
        adapter.notifyDataSetChanged(); // 通知视图，数据已更新，并且需要重新绘制
    }

    // 获取该用户的所有账单,按 时间Time 顺序展示
    private void loadDateByTime() {
        List<Account> lst = AccountBookHelper.getAccountListAllFromAccountbookByTime(username);
        mDatas.clear(); // 将原来的数据清空
        mDatas.addAll(lst); // 将 更新后 的数据添加进去
        adapter.notifyDataSetChanged(); // 通知视图，数据已更新，并且需要重新绘制
    }

    // 获取该用户的所有账单,按 金钱money 顺序展示
    private void loadDateByMoney() {
        List<Account> lst = AccountBookHelper.getAccountListAllFromAccountbookByMoney(username);
        mDatas.clear(); // 将原来的数据清空
        mDatas.addAll(lst); // 将 更新后 的数据添加进去
        adapter.notifyDataSetChanged(); // 通知视图，数据已更新，并且需要重新绘制
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.historical_btn_time) {    // 按事件排序 按钮的点击事件
            loadDateByTime();
        } else if (v.getId() == R.id.historical_btn_money){ // 按金额排序 按钮的点击事件
            loadDateByMoney();
        }
    }


}