package com.example.accountbook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class PrintLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_printlogin);

        // 获取TextView控件，用于显示用户名和密码
        TextView displayUsername = findViewById(R.id.displayUsername);
        TextView displayPassword = findViewById(R.id.displayPassword);

        // 获取启动此Activity的Intent，并提取字符串
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        String password = intent.getStringExtra("PASSWORD");

        // 将字符串设置到TextView控件中
        displayUsername.setText("用户名: " + username);
        displayPassword.setText("密码: " + password);
    }
}