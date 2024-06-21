package utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.accountbook.AccountMainPageActivity;
import com.example.accountbook.R;
import com.example.accountbook.RecordActivity;

import database.ToastUtil;

public class BudgetDialog extends Dialog implements View.OnClickListener {

    EditText moneyEt;
    Button cancel_btn, ensure_btn;

    public interface OnEnsureListener { // 接口
        public void onEnsure(float money);
    }

    BudgetDialog.OnEnsureListener onEnsureListener;  // 初始化接口对象

    // 设定回调接口的方法，传入接口对象
    public void setOnEnsureListener(BudgetDialog.OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public BudgetDialog(@NonNull Context context) {
        super(context);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_budget);

        moneyEt = findViewById(R.id.dialog_budget_et);

        cancel_btn = findViewById(R.id.dialog_budget_btn_cancel);
        ensure_btn = findViewById(R.id.dialog_budget_btn_ensure);

        cancel_btn.setOnClickListener(this);
        ensure_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.dialog_budget_btn_cancel) {
            cancel();

        } else if (v.getId() == R.id.dialog_budget_btn_ensure) {
            // 点击确定，则获取输入的数值
            String moneyDate = moneyEt.getText().toString();

            if(TextUtils.isEmpty(moneyDate)) {
                ToastUtil.show(getContext(), "输入数据不能为空");
                return;
            }
            float money = Float.parseFloat(moneyDate);
            if(money <= 0) {
                ToastUtil.show(getContext(), "预算金额不能小于等于0");
                return;
            }

            // 若数字大于0, 则设置点击确定的监听
            if(onEnsureListener != null) {
                onEnsureListener.onEnsure(money);
            }

            cancel();
        }
    }
}
