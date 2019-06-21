package com.example.acer.incomeexpense;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.acer.incomeexpense.Service.LoanService;
import com.example.acer.incomeexpense.Service.ReminderService;

public class Splash extends AppCompatActivity {


    Typeface tf;
    TextView textv;


    protected void startApp() {

        SharedPreferences sharedPreferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        String number = sharedPreferences.getString("Number", null);
        String password = sharedPreferences.getString("Password", null);

        if (number != null && password != null) {

            Intent orderIntent = new Intent(Splash.this, MainActivity.class);
            startActivity(orderIntent);
            Intent intent= new Intent(Splash.this, ReminderService.class);
            startService(intent);

            Intent intent2 = new Intent(Splash.this, LoanService.class);
            startService(intent2);

            finish();
        } else {

            Intent mIntent = new Intent(Splash.this, LoginActivity.class);
            startActivity(mIntent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread mythread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    startApp();
                } catch (InterruptedException e) {

                    Log.e("Exception", e.getMessage());

                }
            }
        };
        mythread.start();

        textv = findViewById(R.id.tv);
        tf= Typeface.createFromAsset(this.getAssets(), "fonts/Billabong.ttf");
        textv.setTypeface(tf);


    }
}
