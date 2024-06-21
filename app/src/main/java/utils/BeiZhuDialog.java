package utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.accountbook.R;

public class BeiZhuDialog extends Dialog implements View.OnClickListener {
    EditText et;
    Button cancel_btn, ensure_btn;

    public interface OnEnsureListener { // 接口
        public void onEnsure();
    }

    OnEnsureListener onEnsureListener;  // 初始化接口对象

    // 设定回调接口的方法，传入接口对象
    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public BeiZhuDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_beizhu);
        et = findViewById(R.id.dialog_beizhu_et);
        cancel_btn = findViewById(R.id.dialog_beizhu_btn_cancel);
        ensure_btn = findViewById(R.id.dialog_beizhu_btn_ensure);
        cancel_btn.setOnClickListener(this);
        ensure_btn.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.dialog_beizhu_btn_cancel) {
            cancel();

        } else if (v.getId() == R.id.dialog_beizhu_btn_ensure) {
            if(onEnsureListener != null) {
                onEnsureListener.onEnsure();
            }
            cancel();
        }
    }

    // 获取输入数据的方法
    public String getEditText() {
        return et.getText().toString().trim();
    }
}
