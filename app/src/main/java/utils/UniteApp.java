package utils;

import android.app.Application;

import database.AccountBookHelper;

/* 表示全局应用的类*/
public class UniteApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化数据库 成为 全局资源
        AccountBookHelper.initDB(getApplicationContext());
    }
}
