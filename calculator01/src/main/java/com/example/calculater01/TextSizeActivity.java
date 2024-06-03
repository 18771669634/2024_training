package com.example.calculater01;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TextSizeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_text_size);
        TextView hello = findViewById(R.id.tv_sp);
        hello.setTextColor(Color.GREEN);
    }
}