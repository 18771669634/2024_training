package com.example.accountbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapter.AccountAdapter;
import database.AccountBookHelper;
import javabean.Account;
import utils.BudgetDialog;

public class AccountMainPageActivity extends AppCompatActivity implements View.OnClickListener {

    private String username;

    ListView todayLv;   // 展示今日支出和收入的情况

    List<Account> mDatas;   // 声明数据源
    AccountAdapter adapter;

    int year, month, day;

    View headerView;    // 头布局
    TextView today_topOut_tv, today_topIn_tv, top_remainMoney_tv;   // 今日支出，今日收入, 预算剩余
    SharedPreferences preferences;  // 记录预算金额,需要初始化


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_main_page);

        // SharedPreferences初始化
        preferences = getSharedPreferences("budget", MODE_PRIVATE);

        // 从 Intent 中获取用户名
        username = getIntent().getStringExtra("username");

        // 记一笔 按钮的点击事件
        findViewById(R.id.ac_main_btn_edit).setOnClickListener(this);

        // 更多 按钮的点击事件
        findViewById(R.id.ac_main_btn_more).setOnClickListener(this);

        // 设置今日时间 year, month, day
        initTime();

        // ListView
        todayLv = findViewById(R.id.ac_main_page_lv);

        // 添加ListView的头布局
        addLVHeaderView();

        mDatas = new ArrayList<>();

        // 设置适配器：加载每一行数据到列表当中
        adapter = new AccountAdapter(this, mDatas);
        // 将适配器设置给 ListView
        todayLv.setAdapter(adapter);
    }

    // 添加ListView的头布局的方法
    private void addLVHeaderView() {
        // 将布局转换为 View 对象
        headerView = getLayoutInflater().inflate(R.layout.item_ac_mainlv_top, null);
        todayLv.addHeaderView(headerView);  // 设置头布局
        // 查找头布局需要用到的控件
        today_topOut_tv = headerView.findViewById(R.id.item_ac_mainlv_top_out);
        today_topIn_tv = headerView.findViewById(R.id.item_ac_mainlv_top_in);

        // 预算 有点击事件
        top_remainMoney_tv = headerView.findViewById(R.id.item_ac_mainlv_top_remain_money);
        // 设置 预算 的点击事件
        top_remainMoney_tv.setOnClickListener(this);


    }

    // 获取今日时间
    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    // 当 Acitvity 获取 焦点 时，会调用的方法
    @Override
    protected void onResume() { // 因为在记录页面返回主页面时，数据会再次更新，所以把数据更新放在这里
        super.onResume();
        loadAccountDate();  // 加载数据库数据到ListView中
        setTopTvDate(); // 设置顶部数据的显示
    }

    // 加载数据库数据到ListView中,当天的账单
    private void loadAccountDate() {
        List<Account> lst = AccountBookHelper.getAccountListOneDayFromAccountbook(username, year, month, day);
        mDatas.clear(); // 将原来的数据清空
        mDatas.addAll(lst); // 将 更新后 的数据添加进去
        adapter.notifyDataSetChanged(); // 通知视图数据已更新，并且需要重新绘制
    }

    // 设置头布局的 今日支出 和 今日收入 显示
    private void setTopTvDate() {
        float todayMoney_in = AccountBookHelper.getSumMoneyOneDay_Out_In(username, year, month, day, 1);
        float todayMoney_out = AccountBookHelper.getSumMoneyOneDay_Out_In(username, year, month, day, 0);
        String StrTodayMoney_in = "￥ " + todayMoney_in;
        String StrTodayMoney_out = "￥ " + todayMoney_out;
        today_topOut_tv.setText(StrTodayMoney_out);
        today_topIn_tv.setText(StrTodayMoney_in);

        // 设置 预算剩余 显示
        // 从SharedPreferences获取预算金额
        float bgMoney = preferences.getFloat("budgetMoney",0);

        if(bgMoney == 0) {
            top_remainMoney_tv.setText("￥ 0");
        } else {
            float remainMoney = bgMoney - todayMoney_out;
            top_remainMoney_tv.setText("￥ " + remainMoney);
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        if(v.getId() == R.id.ac_main_btn_edit) {    // 记一笔 按钮的点击事件
            intent = new Intent(AccountMainPageActivity.this, RecordActivity.class);
            intent.putExtra("username", username);  // 传递用户名
            startActivity(intent);
        } else if (v.getId() == R.id.ac_main_btn_more){ // 历史记录 按钮的点击事件
            intent = new Intent(AccountMainPageActivity.this, HistoricalActivity.class);
            intent.putExtra("username", username);  // 传递用户名
            startActivity(intent);
        } else if (v.getId() == R.id.item_ac_mainlv_top_remain_money) { // 预算 的点击事件
            showBudgetDialog(); // 显示 预算 设置对话框
        }
    }

    // 显示 预算 设置对话框
    private void showBudgetDialog() {
        BudgetDialog budgetDialog = new BudgetDialog(this); // 创建 BudgetDialog 对象
        budgetDialog.show();    // 预算设置框显示出来

        // 设置回调接口
        budgetDialog.setOnEnsureListener(new BudgetDialog.OnEnsureListener() {
            @Override
            public void onEnsure(float money) {
                // 将预算金额写入 共享参数 中，存储
                SharedPreferences.Editor edit = preferences.edit();
                edit.putFloat("budgetMoney", money);
                edit.apply();

                // 计算 剩余预算
                float todayMoney_out = AccountBookHelper.getSumMoneyOneDay_Out_In(username, year, month, day, 0);
                // 预算剩余 = 预算 - 支出
                float remainMoney = money - todayMoney_out;
                top_remainMoney_tv.setText("￥ "+ remainMoney);
            }
        });
    }
}