package utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.accountbook.R;

public class SelectTimeDialog extends Dialog implements View.OnClickListener {

    DatePicker datePicker;
    Button ensure_btn, cancel_btn;

    public interface OnEnsureListener{
        public void onEnsure(String time,int year,int month,int day);   // 方便点击确认后传入对象
    }

    OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public SelectTimeDialog(@NonNull Context context) {
        super(context);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_calendar);

        datePicker = findViewById(R.id.dialog_time_dp);
        ensure_btn = findViewById(R.id.dialog_time_btn_ensure);
        cancel_btn = findViewById(R.id.dialog_time_btn_cancel);

        ensure_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.dialog_time_btn_cancel) {
            cancel();
        } else if (v.getId() == R.id.dialog_time_btn_ensure) {
            int year = datePicker.getYear();  //选择年份
            int month = datePicker.getMonth() + 1;
            int dayOfMonth = datePicker.getDayOfMonth();

            String monthStr = String.valueOf(month);
            if (month < 10){
                monthStr = "0" + month; // MM月
            }

            String dayStr = String.valueOf(dayOfMonth);
            if (dayOfMonth < 10){
                dayStr="0"+dayOfMonth;  // DD日
            }

            String timeFormat = year + "年" + monthStr + "月" + dayStr + "日";
            if (onEnsureListener!=null) {
                onEnsureListener.onEnsure(timeFormat, year, month, dayOfMonth);
            }
            cancel();

        }
    }

}
