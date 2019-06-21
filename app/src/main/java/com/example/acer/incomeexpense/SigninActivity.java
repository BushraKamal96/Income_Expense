package com.example.acer.incomeexpense;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.acer.incomeexpense.Database.DatabaseHelper;
import com.example.acer.incomeexpense.Service.LoanService;
import com.example.acer.incomeexpense.Service.ReminderService;
import com.example.acer.incomeexpense.models.User_model;

import java.util.ArrayList;
import java.util.Calendar;

public class SigninActivity extends AppCompatActivity {

    EditText email, password, name, number;
    Button signinbtn;
    String inputEmail, inputPassword, inputName, inputNumber;
    private SharedPreferences.Editor preferences;
    private ArrayList<User_model> arrayList = new ArrayList<>();
    DatabaseHelper openHelper;
    SQLiteDatabase db;

    TextView tv;
    Typeface tf;
    AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        preferences = getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).edit();
        openHelper = new DatabaseHelper(this);

        tv = findViewById(R.id.signinlogo);
        tf= Typeface.createFromAsset(this.getAssets(), "fonts/Billabong.ttf");
        tv.setTypeface(tf);



        email = (EditText) findViewById(R.id.myemail);
        tf= Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Light.ttf");
        email.setTypeface(tf);

        password = (EditText) findViewById(R.id.mypass);
        tf= Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Light.ttf");
        password.setTypeface(tf);

        name = (EditText) findViewById(R.id.myname);
        tf= Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Light.ttf");
        name.setTypeface(tf);

        number = (EditText) findViewById(R.id.mynum);
        tf= Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Light.ttf");
        number.setTypeface(tf);


        signinbtn = (Button) findViewById(R.id.SigninButton);



        String regexPassword = "(?=.*[\\d]).{5,}";

        awesomeValidation.addValidation(SigninActivity.this,R.id.myname,"[a-zA-Z\\s]+",R.string.name);
        awesomeValidation.addValidation(SigninActivity.this,R.id.myemail,android.util.Patterns.EMAIL_ADDRESS,R.string.email);
        awesomeValidation.addValidation(SigninActivity.this,R.id.number,RegexTemplate.TELEPHONE,R.string.phnumber);
        awesomeValidation.addValidation(SigninActivity.this,R.id.mypass,regexPassword,R.string.password);

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  db = openHelper.getWritableDatabase();
                Log.e("checkkkkkk", "signbtn");


                inputEmail = email.getText().toString();
                inputPassword = password.getText().toString();
                inputName = name.getText().toString();
                inputNumber = number.getText().toString();

                preferences.putString("Name", inputName);
                preferences.putString("Number", inputNumber);
                preferences.putString("Password", inputPassword);
                preferences.apply();

                if(awesomeValidation.validate()) {

                    long id = openHelper.insertUserData(inputEmail, inputName, inputPassword, inputNumber);

                    Log.e("checking", "signbtn");

                    if (id!= -1) {
                        Toast.makeText(SigninActivity.this, "Inserted", Toast.LENGTH_SHORT).show();


                        Intent intentservice = new Intent(SigninActivity.this, ReminderService.class);
                        // intentservice.setAction("com.testApp.service.MY_SERVICE");
                        startService(intentservice);

                        Intent intent2 = new Intent(SigninActivity.this, LoanService.class);
                        startService(intent2);


                        Log.e("check", "signbtn");
                        Intent intent = new Intent(SigninActivity.this, UserInfo.class);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    // inserted and call the intent here
                    Toast.makeText(SigninActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    Log.e("checkerror", "signbtn");

                }
            }
        });

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 42);
        calendar.set(Calendar.SECOND, 0);

        Intent intent= new Intent(SigninActivity.this,DailyAlarm.class);

        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1234,intent,0);

        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);




    }
    }

//    public long insertData(String Email, String Password, String Name, String Number) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.COLUMN_EMAIL, Email);
//        contentValues.put(DatabaseHelper.COLUMN_PASSWORD, Password);
//        contentValues.put(DatabaseHelper.COLUMN_NAME, Name);
//        contentValues.put(DatabaseHelper.COLUMN_PHONE, Number);
//        long id = db.insert(DatabaseHelper.USER_TABLE_NAME, null, contentValues);
//        return id;
//
//    }





