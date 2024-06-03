package com.example.accountbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import database.MyDatabaseHelper;
import database.Post;
import database.ToastUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SQLReadStoreActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView showText;
    private MyDatabaseHelper mHelper;
    private OkHttpClient client;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlread_store);

        findViewById(R.id.store_to_sqlite).setOnClickListener(this);
        findViewById(R.id.read_print).setOnClickListener(this);
        showText = findViewById(R.id.show);

        // 初始化 OkHttpClient
        client = new OkHttpClient();

    }

    @Override
    protected void onStart() {
        super.onStart();
        // 获得数据库帮助器实例
        mHelper = MyDatabaseHelper.getInstance(this);
        // 打开数据库读写连接
        mHelper.openWriteLink();
        mHelper.openReadLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 关闭数据库连接
        mHelper.closeLink();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.store_to_sqlite) {
            fetchAndStoreJson();

        } else if (v.getId() == R.id.read_print) {
            readFromDatabase();
        }
    }

    // 使用 OkHttp 获取 JSON 数据并存储到 SQLite
    private void fetchAndStoreJson() {
        String URL = "https://jsonplaceholder.typicode.com/posts/3";

        Request request = new Request.Builder()
                .url(URL)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            public void onResponse(@NonNull Call call, @NonNull Response response)
                    throws IOException {
                if (false == response.isSuccessful()) {
                    runOnUiThread(() -> ToastUtil.show(SQLReadStoreActivity.this, "服务错误: " + response.message()));
                    return;
                }

                // 读取响应
                final String jsonData = response.body().string();
                response.close();

                // 解析 JSON 数据
                Gson gson = new Gson();
                Post post = gson.fromJson(jsonData, Post.class);

                // 存储到SQLite
                if(mHelper.insert(post) > 0) {
                    runOnUiThread(() -> ToastUtil.show(SQLReadStoreActivity.this, "数据存储成功"));
                }
            }

            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("MainActivity", "Network Request Failed", e);
                runOnUiThread(() -> ToastUtil.show(SQLReadStoreActivity.this, "网络请求失败"));
            }
        });
    }

    // 从 SQLite 读取数据并展示
    private void readFromDatabase() {
        List<Post> list = new ArrayList<>();
        list = mHelper.queryAll();
        // 检查列表是否为空
        if (list.isEmpty()) {
            showText.setText("没有数据");
        } else {
            // 使用StringBuilder构建显示内容
            StringBuilder displayText = new StringBuilder();

            // 遍历列表中的每个Post对象
            for (Post post : list) {
                if (post != null) {
                    // 使用Post类的toString()方法
                    displayText.append(post.toString()).append("\n\n");

                }
            }

            // 将构建好的内容设置到TextView
            showText.setText(displayText.toString());
        }
    }
}
