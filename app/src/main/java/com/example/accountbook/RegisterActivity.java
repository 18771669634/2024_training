package com.example.accountbook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import database.MyDatabaseHelper;
import database.MySQLiteHelper;
import database.ToastUtil;
import javabean.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private MySQLiteHelper myHelper;
    private EditText username_re, password_re, passwordcheck_re;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        username_re = findViewById(R.id.username_re);
        password_re = findViewById(R.id.password_re);
        passwordcheck_re = findViewById(R.id.passwordcheck_re);

        findViewById(R.id.register_re).setOnClickListener(this);

        // 获得数据库帮助器实例
        myHelper = MySQLiteHelper.getInstance(this);
        // 打开数据库读写连接
        myHelper.openWriteLink();
        myHelper.openReadLink();

    }

    protected void onDestroy() {
        super.onDestroy();
        // 关闭数据库连接
        if (myHelper != null) {
            myHelper.closeLink();
        }
    }

    public void onClick(View v) {
        String un = username_re.getText().toString().trim();
        String pwd = password_re.getText().toString().trim();
        String pwd_ck = passwordcheck_re.getText().toString().trim();

        // 检查用户名是否为空
        if (un.isEmpty()) {
            ToastUtil.show(getApplicationContext(), "用户名不能为空");
            return;  // 中止操作
        }

        // 检查密码是否为空
        if (pwd.isEmpty() || pwd_ck.isEmpty()) {
            ToastUtil.show(getApplicationContext(), "密码不能为空");
            return;  // 中止操作
        }

        // 检查两次输入的密码是否一致
        if (!pwd.equals(pwd_ck)) {
            ToastUtil.show(getApplicationContext(), "两次密码输入不一致");
            return;  // 中止操作
        }

        // 检查用户名是否已存在
        if (myHelper.isUsernameExists(un)) {
            ToastUtil.show(getApplicationContext(), "用户名已存在");
            return;  // 中止操作
        }

        // 如果所有条件都满足，进行注册
        User u = new User(un, pwd);
        long register = myHelper.register(u);
        if (register != -1) {
            ToastUtil.show(getApplicationContext(), "注册成功");
            Intent i = new Intent(RegisterActivity.this, Login_Accountbook_Activity.class);
            startActivity(i);
        } else {
            ToastUtil.show(getApplicationContext(), "注册失败");
        }
    }

}