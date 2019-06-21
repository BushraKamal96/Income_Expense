package com.example.acer.incomeexpense;

import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.acer.incomeexpense.Adapter.DailyAdapter;
import com.example.acer.incomeexpense.Database.DatabaseHelper;
import com.example.acer.incomeexpense.models.Records_model;
import com.example.acer.incomeexpense.models.User_model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DailyRecord extends AppCompatActivity {

    private ArrayList<Records_model> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DailyAdapter dailyAdapter;
    DatabaseHelper db;
    TextView total, summaryTV;
    Toolbar toolbar;
    private SharedPreferences.Editor preferences;
    AlertDialog alertDialog;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");

    public static String userNum = "";
    Integer UserWalletLimit, totalSum, summeryValue;
    String summery;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_record);

        userNum =getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).getString("Number", "0");
        preferences = getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).edit();

        db = new DatabaseHelper(this);
        String date = dateFormat.format(Calendar.getInstance().getTime());


        toolbar = findViewById(R.id.dailytoolbar2);
        setSupportActionBar(toolbar);

        arrayList.addAll(db.getRecentrecords(date));
        Log.e("Size", String.valueOf(arrayList.size()));

        recyclerView = findViewById(R.id.recent_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        dailyAdapter = new DailyAdapter(arrayList, this);
        recyclerView.setAdapter(dailyAdapter);

        total = findViewById(R.id.dailytotalprice);
        total.setText(String.valueOf(db.recenttotalsum(date)) + " Rs");

        User_model user = db.getUserWallet(userNum);
        UserWalletLimit = Integer.valueOf(user.getLimit());
        totalSum = (int) db.recenttotalsum(date);

        if (totalSum > UserWalletLimit){
            summeryValue = totalSum-UserWalletLimit;
            summery="Warning !!! You have spent Rs. "+ summeryValue.toString() + " more from your Limit. Be careful for next time.";

        }
        if (totalSum.equals(UserWalletLimit)){
            summery="Good Job ! You have spend according to your Limit. Keep it up.";
        }
        if (totalSum < UserWalletLimit){
            summeryValue = UserWalletLimit - totalSum;
            summery="Great Job! You have save Rs. " + summeryValue.toString() + " from your Limit. Keep saving :) ";
        }

      summaryTV= findViewById(R.id.summaryid);
        summaryTV.setText(summery);

    }
}
