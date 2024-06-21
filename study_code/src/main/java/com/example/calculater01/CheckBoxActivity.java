package com.example.calculater01;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CheckBoxActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_check_box);

        CheckBox ck_sys = findViewById(R.id.ck_sys);
        ck_sys.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String desc = String.format("您%s了这个CheckBox", isChecked ? "勾选" : "取消勾选");
        buttonView.setText(desc);
    }
}