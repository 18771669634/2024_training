package com.example.accountbook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button button = findViewById(R.id.login);
        button.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            Intent intent = new Intent();
            intent.setClass(MainActivity2.this, MainActivity3.class);

            intent.putExtra("USERNAME", username);
            intent.putExtra("PASSWORD", password);

            startActivity(intent);
        });

    }
}
