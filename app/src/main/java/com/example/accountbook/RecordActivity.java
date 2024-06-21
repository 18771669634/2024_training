package com.example.accountbook;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import adapter.RecordFragPagerAdapter;
import fragment_record.InFragment;
import fragment_record.OutFragment;


public class RecordActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        // 从 Intent 中获取用户名
        username = getIntent().getStringExtra("username");

        // 返回 点击事件
        findViewById(R.id.record_iv_goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 查找控件
        tabLayout = findViewById(R.id.record_tabs);
        viewPager = findViewById(R.id.record_vp);
        // 设置ViewPager加载页面
        initPager();

    }

    private void initPager() {
        // 初始化两个ViewPager页面的集合
        List<Fragment> fragmentList = new ArrayList<>();
        // 创建收入和支出两个页面，放置在Fragment中
        OutFragment outFrag = new OutFragment();    // 支出
        InFragment inFrag = new InFragment();   //收入

        // 创建 Bundle 用于传递数据
        Bundle args = new Bundle();
        args.putString("username", username);

        // 设置 Bundle 给 Fragment
        outFrag.setArguments(args);
        inFrag.setArguments(args);

        fragmentList.add(outFrag);
        fragmentList.add(inFrag);

        // 创建适配器  参数分别是啥，需要去了解
        RecordFragPagerAdapter pagerAdapter = new RecordFragPagerAdapter(getSupportFragmentManager(), fragmentList);

        // 设置适配器
        viewPager.setAdapter(pagerAdapter);

        // 将tabLayout 与 viewPager 进行关联
        tabLayout.setupWithViewPager(viewPager);

    }
}