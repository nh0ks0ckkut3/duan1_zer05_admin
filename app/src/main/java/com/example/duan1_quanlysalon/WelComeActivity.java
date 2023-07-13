package com.example.duan1_quanlysalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class WelComeActivity extends AppCompatActivity {

    TextView btnStart;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wel_come);
        btnStart = findViewById(R.id.textView2);

        Intent intent = new Intent(WelComeActivity.this,LoginActivity.class);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                finish();
                startActivity(intent);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!flag){
                    finish();
                    startActivity(intent);
                }
            }
        },5000);
    }
}