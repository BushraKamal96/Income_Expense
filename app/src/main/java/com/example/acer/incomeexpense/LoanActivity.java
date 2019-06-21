package com.example.acer.incomeexpense;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.acer.incomeexpense.Adapter.LoanAdapter;
import com.example.acer.incomeexpense.Database.DatabaseHelper;
import com.example.acer.incomeexpense.models.Loan_model;

import java.util.ArrayList;

public class LoanActivity extends AppCompatActivity {

    private ArrayList<Loan_model> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    LoanAdapter loanAdapter;
    DatabaseHelper db;
    Toolbar toolbar;
    private SharedPreferences.Editor preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan);

        db = new DatabaseHelper(this);


        toolbar = findViewById(R.id.loantoolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        arrayList.addAll(db.getallloanrecords());
        Log.e("Size", String.valueOf(arrayList.size()));


        recyclerView = findViewById(R.id.loan_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loanAdapter = new LoanAdapter(arrayList, this);
        recyclerView.setAdapter(loanAdapter);


    }
}
