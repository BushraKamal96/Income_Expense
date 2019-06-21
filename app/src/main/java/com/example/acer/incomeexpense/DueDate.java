package com.example.acer.incomeexpense;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.acer.incomeexpense.Adapter.DailyAdapter;
import com.example.acer.incomeexpense.Adapter.DuedateAdapter;
import com.example.acer.incomeexpense.Database.DatabaseHelper;
import com.example.acer.incomeexpense.models.Loan_model;
import com.example.acer.incomeexpense.models.Records_model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DueDate extends AppCompatActivity {


    private SharedPreferences.Editor preferences;

    ArrayList<Loan_model> arrayList= new ArrayList<>();
    RecyclerView rView;
    DuedateAdapter adapter;
    DatabaseHelper db;
    TextView total;
    Toolbar toolbar;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_due_date);

        preferences = getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).edit();

        db = new DatabaseHelper(this);
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());

        arrayList.addAll(db.getReturnLoanrecords(date));

        rView = findViewById(R.id.rview_returndate);
        rView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DuedateAdapter(arrayList, this);
        rView.setAdapter(adapter);



        toolbar = findViewById(R.id.toolbar_returndate);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DueDate.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}
