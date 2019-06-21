package com.example.acer.incomeexpense;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.incomeexpense.Adapter.IncomeAdapter;
import com.example.acer.incomeexpense.Adapter.RecordAdapter;
import com.example.acer.incomeexpense.Database.DatabaseHelper;
import com.example.acer.incomeexpense.models.Income_model;
import com.example.acer.incomeexpense.models.Records_model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class IncomeActivity extends AppCompatActivity {

    private ArrayList<Income_model> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private IncomeAdapter incomeAdapter;
    DatabaseHelper db;
    TextView total;
    ImageView filterimg;
    Toolbar toolbar;
    private SharedPreferences.Editor preferences;
    AlertDialog alertDialog;
    String selectedMonth, selectedYear;
    int monthv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_activity);

        preferences = getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).edit();

        toolbar = findViewById(R.id.incometoolbar2);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        db = new DatabaseHelper(this);

        arrayList.addAll(db.getallincome());
        Log.e("Size", String.valueOf(arrayList.size()));

        recyclerView = findViewById(R.id.income_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        incomeAdapter = new IncomeAdapter(arrayList, this);
        recyclerView.setAdapter(incomeAdapter);

        total = findViewById(R.id.cashprice);
        total.setText("Rs " + String.valueOf(db.incometotalsum()));

        filterimg= findViewById(R.id.income_filter);
        filterimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");


                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View v = inflater.inflate(R.layout.spinner_dialog, null);


                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IncomeActivity.this);
                alertDialogBuilder.setMessage("Select Filter");
                alertDialogBuilder.setView(v);

                final Spinner mspinner = v.findViewById(R.id.spinner);
                final Spinner yspinners = v.findViewById(R.id.spinner2);

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(IncomeActivity.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.SelectMonth));

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mspinner.setAdapter(adapter);

                mspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        monthv = i;
                        selectedMonth = adapterView.getItemAtPosition(i).toString();


                        //here i is your month in string
                        //String monyInt = i+"";
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                        ;
                    }
                });
                yspinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedYear = adapterView.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                int currentYear = Integer.parseInt(simpleDateFormat.format(Calendar.getInstance().getTime()));
                final List<Integer> year = new ArrayList<Integer>();

                for (int i = currentYear; i >= 2000; i--) {
                    year.add(i);

                }
                final ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(IncomeActivity.this,
                        android.R.layout.simple_spinner_item, year);
                yspinners.setAdapter(dataAdapter);


                alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!mspinner.getSelectedItem().toString().equalsIgnoreCase("Choose a month")) {
                            Toast.makeText(IncomeActivity.this,
                                    mspinner.getSelectedItem().toString(),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                        if (!yspinners.getSelectedItem().toString().equalsIgnoreCase("Choose a year")) {
                            Toast.makeText(IncomeActivity.this,
                                    yspinners.getSelectedItem().toString(),
                                    Toast.LENGTH_SHORT)
                                    .show();


                            String monthly = selectedYear + "-" + monthv;
                            ArrayList<Income_model> filterarray = db.byIncomeMonth(monthly);
                            dialogInterface.dismiss();

                            Log.e("byMonth", selectedMonth);
                            Log.e("byyear", selectedYear);


                            arrayList.clear();
                            arrayList.addAll(filterarray);
                            total.setText(String.valueOf(db.totalsumofIncomeMonthh(monthly))+" rps");

                            incomeAdapter.notifyDataSetChanged();


                        }
                    }
                });


                alertDialogBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                alertDialogBuilder.setView(v);
                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();
                //spinner.setOnItemClickListener();


            }
        });





    }
}
