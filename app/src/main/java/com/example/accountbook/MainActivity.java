package com.example.accountbook;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Log.d("ning", "onCreate");
    }
    public void clickHandlder(View source){
        //获取UI界面中ID为R.id.hello_android的文本框
        TextView tv = findViewById(R.id.hello_android);

        //改变文本框的文本内容
        tv.setText("Hello Android-" + new java.util.Date());
    }

    public void clickHandlderLog(View source){
        Log.d("ning", "HelloWorld");
    }
}
