package com.example.accountbook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        findViewById(R.id.hello_world).setOnClickListener(this);
        findViewById(R.id.skip_login).setOnClickListener(this);
        findViewById(R.id.SQL_read_store).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if(v.getId() == R.id.hello_world) {
            Log.d("ning", "HelloWorld");
        } else if (v.getId() == R.id.skip_login) {
            intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.SQL_read_store) {
            intent = new Intent(MainActivity.this, SQLReadStoreActivity.class);
            startActivity(intent);
        }
    }
}
