package com.example.acer.incomeexpense;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.incomeexpense.Database.DatabaseHelper;
import com.example.acer.incomeexpense.Service.LoanService;
import com.example.acer.incomeexpense.Service.ReminderService;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    EditText number, password;
    Button log, signin ;
    String inputNumber, inputPassword;
    private SharedPreferences.Editor preferences;
    DatabaseHelper openHelper;

    TextView login, signtxt;
    Typeface tf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        preferences = getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).edit();
        openHelper = new DatabaseHelper(this);


        login = findViewById(R.id.loginnlogo);
        tf= Typeface.createFromAsset(this.getAssets(), "fonts/Billabong.ttf");
        login.setTypeface(tf);
        number =  findViewById(R.id.number);
        tf= Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Light.ttf");
        number.setTypeface(tf);
        password = findViewById(R.id.password);
        tf= Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Light.ttf");
password.setTypeface(tf);

       signtxt = findViewById(R.id.testing);

        //signin = findViewById(R.id.SigninButton);
        signtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin = new Intent(LoginActivity.this, SigninActivity.class);
                startActivity(signin);
                finish();
            }
        });

        log =  findViewById(R.id.loginbtn);
        log.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                inputNumber = number.getText().toString();
                inputPassword = password.getText().toString();

                boolean inserted = openHelper.getData(inputNumber, inputPassword);


                if (inserted) {

                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                    Intent intents = new Intent(LoginActivity.this, ReminderService.class);
                    startService(intents);

                    Intent intent2 = new Intent(LoginActivity.this, LoanService.class);
                    startService(intent2);


                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    preferences.putString("Number", inputNumber);
                    preferences.putString("Password", inputPassword);
                    preferences.apply();

                } else {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();

                }
//
            }
        });

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 42);
        calendar.set(Calendar.SECOND, 0);

        Intent intent= new Intent(LoginActivity.this,DailyAlarm.class);

        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1234,intent,0);

        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);



    }


}

