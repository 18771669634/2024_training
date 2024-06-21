package com.example.calculater01;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView cal_result;
    // 第一个操作数
    private String firstNum = "";
    // 运算符
    private String operator = "";
    // 第二个操作数
    private String secondNum = "";
    // 当前的计算结果
    private String result = "";
    // 要显示的文本内容
    private String showText = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        // 从局部文件中获取名叫cal_result的文本视图
        cal_result = findViewById(R.id.cal_result);

        // 每个按钮注册点击事件监听
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.btn_divide).setOnClickListener(this);
        findViewById(R.id.btn_multiply).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_dot).setOnClickListener(this);
        findViewById(R.id.btn_equal).setOnClickListener(this);
        findViewById(R.id.btn_minus).setOnClickListener(this);
        findViewById(R.id.btn_reciprocal).setOnClickListener(this);
        findViewById(R.id.btn_nine).setOnClickListener(this);
        findViewById(R.id.btn_eight).setOnClickListener(this);
        findViewById(R.id.btn_seven).setOnClickListener(this);
        findViewById(R.id.btn_six).setOnClickListener(this);
        findViewById(R.id.btn_five).setOnClickListener(this);
        findViewById(R.id.btn_four).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_zero).setOnClickListener(this);
        findViewById(R.id.btn_plus).setOnClickListener(this);
        findViewById(R.id.ib_sqrt).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String inputText;

        // 如果是开根号按钮
        if(v.getId() == R.id.ib_sqrt) {
            inputText = "√";
        }else {
            // 除了开根号之外的按钮
            // v是button，转成TextView就可以获得文本
            inputText = ((TextView)v).getText().toString();
        }

        if(v.getId() == R.id.btn_clear) {

        } else if (v.getId() == R.id.btn_cancel) {

        } else if (v.getId() == R.id.btn_dot) {

        } else if (v.getId() == R.id.btn_divide) {

        } else if (v.getId() == R.id.btn_multiply) {

        } else if (v.getId() == R.id.btn_plus){

        } else if(v.getId() == R.id.btn_minus) {

        } else if (v.getId() == R.id.ib_sqrt) {

        } else if (v.getId() == R.id.btn_reciprocal) {

        } else if (v.getId() == R.id.btn_equal) {

        } else if (v.getId() == R.id.btn_zero) {

        }else if (v.getId() == R.id.btn_one) {

        }else if (v.getId() == R.id.btn_two) {

        }else if (v.getId() == R.id.btn_three) {

        }else if (v.getId() == R.id.btn_four) {

        }else if (v.getId() == R.id.btn_five) {

        }else if (v.getId() == R.id.btn_six) {

        }else if (v.getId() == R.id.btn_seven) {

        }else if (v.getId() == R.id.btn_eight) {

        }else if (v.getId() == R.id.btn_nine) {

        }else {

        }
    }
}