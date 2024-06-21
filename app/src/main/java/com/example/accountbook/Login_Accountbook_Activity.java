package com.example.accountbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import database.MySQLiteHelper;
import database.ToastUtil;

public class Login_Accountbook_Activity extends AppCompatActivity implements View.OnClickListener {

    private MySQLiteHelper myHelper;
    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_accountbook);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        Intent intent;
        if(v.getId() == R.id.login) {
            String un = username.getText().toString().trim();
            String pwd = password.getText().toString().trim();

            // 检查用户名是否为空
            if (un.isEmpty()) {
                ToastUtil.show(getApplicationContext(), "用户名不能为空");
                return;  // 中止操作
            }

            // 检查密码是否为空
            if (pwd.isEmpty()) {
                ToastUtil.show(getApplicationContext(), "密码不能为空");
                return;  // 中止操作
            }

            // 检查用户是否已存在
            if (!myHelper.isUsernameExists(un)) {
                ToastUtil.show(getApplicationContext(), "用户不存在，请先注册");
                return;  // 中止操作
            }

            boolean login = myHelper.login(un, pwd);

            if(login) {
                ToastUtil.show(getApplicationContext(), "登录成功");
                intent = new Intent(Login_Accountbook_Activity.this, AccountMainPageActivity.class);
                intent.putExtra("username", un);  // 传递用户名
                startActivity(intent);
                finish();  // 关闭登录界面
            } else {
                ToastUtil.show(getApplicationContext(), "登录失败,密码错误");
            }

        } else if (v.getId() == R.id.register) {
            intent = new Intent(Login_Accountbook_Activity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }
}