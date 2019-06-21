package com.example.acer.incomeexpense;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.incomeexpense.Adapter.RecordAdapter;
import com.example.acer.incomeexpense.Database.DatabaseHelper;
import com.example.acer.incomeexpense.models.Records_model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Records extends AppCompatActivity {

    private ArrayList<Records_model> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecordAdapter recordAdapter;
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
        setContentView(R.layout.records);

        preferences = getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).edit();


        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Records.this, Home.class);
//                startActivity(intent);
                finish();
            }
        });

        filterimg = findViewById(R.id.filter);
        filterimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                final View v = inflater.inflate(R.layout.alertdialog, null);


                final Button date;
                final Button month;
                final Button year;


                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Records.this);
                alertDialogBuilder.setMessage("Select Filter");
                alertDialogBuilder.setView(v);


                date = v.findViewById(R.id.byDatebtn);
                month = v.findViewById(R.id.byMonthbtn);
                year = v.findViewById(R.id.byYearbtn);


                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();
                        showDialog(999);

                    }
                });

                month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");


                        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                        View v = inflater.inflate(R.layout.spinner_dialog, null);


                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Records.this);
                        alertDialogBuilder.setMessage("Select Filter");
                        alertDialogBuilder.setView(v);

                        final Spinner mspinner = v.findViewById(R.id.spinner);
                        final Spinner yspinners = v.findViewById(R.id.spinner2);

                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Records.this,
                                android.R.layout.simple_spinner_item,
                                getResources().getStringArray(R.array.SelectMonth));

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mspinner.setAdapter(adapter);

                        mspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                monthv = i;
                                selectedMonth = adapterView.getItemAtPosition(i).toString();
                                Log.e("month", "" + month);


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
                        final ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(Records.this,
                                android.R.layout.simple_spinner_item, year);
                        yspinners.setAdapter(dataAdapter);


                        alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!mspinner.getSelectedItem().toString().equalsIgnoreCase("Choose a month")) {
                                    Toast.makeText(Records.this,
                                            mspinner.getSelectedItem().toString(),
                                            Toast.LENGTH_SHORT)
                                            .show();
                                }
                                if (!yspinners.getSelectedItem().toString().equalsIgnoreCase("Choose a year")) {
                                    Toast.makeText(Records.this,
                                            yspinners.getSelectedItem().toString(),
                                            Toast.LENGTH_SHORT)
                                            .show();

                                    Log.e("bMonth", month.toString());

                                    String monthly = selectedYear + "-" + monthv;
                                    ArrayList<Records_model> filterarray = db.byMonth(monthly);
                                    dialogInterface.dismiss();

                                    Log.e("byMonth", selectedMonth);
                                    Log.e("byyear", selectedYear);


                                    arrayList.clear();
                                    arrayList.addAll(filterarray);
                                    total.setText(String.valueOf(db.totalsumofMonthh(monthly))+" rps");

                                    recordAdapter.notifyDataSetChanged();


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

                year.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");

                        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                        View view2 = inflater.inflate(R.layout.year_dialog, null);


                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Records.this);
                        alertDialogBuilder.setMessage("Select Filter");

                        alertDialogBuilder.setView(view2);

                        final Spinner yearlyspinner = view2.findViewById(R.id.yearSpinner);

                        int currentYear = Integer.parseInt(simpleDateFormat.format(Calendar.getInstance().getTime()));
                        List<Integer> year = new ArrayList<Integer>();

                        for (int i = currentYear; i >= 2010; i--) {
                            year.add(i);


                        }

                        yearlyspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                selectedYear = adapterView.getSelectedItem().toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        final ArrayAdapter<Integer> yadapter = new ArrayAdapter<Integer>(Records.this,
                                android.R.layout.simple_spinner_item, year);
                        yearlyspinner.setAdapter(yadapter);

                        alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!yearlyspinner.getSelectedItem().toString().equalsIgnoreCase("Choose a Year")) {
                                    Toast.makeText(Records.this,
                                            yearlyspinner.getSelectedItem().toString(),
                                            Toast.LENGTH_SHORT)
                                            .show();

                                    ArrayList<Records_model> filterarray = db.byYear(selectedYear);
                                    dialogInterface.dismiss();

                                    Log.e("byyear", selectedYear);
                                    arrayList.clear();
                                    arrayList.addAll(filterarray);
                                    total.setText(String.valueOf(db.totalsumofYear(selectedYear))+" rps");

                                    recordAdapter.notifyDataSetChanged();

                                }
                                dialogInterface.dismiss();


                            }
                        });

                        alertDialogBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });


                        alertDialogBuilder.setView(view2);

                        AlertDialog dialog = alertDialogBuilder.create();
                        dialog.show();


                    }
                });

                alertDialog = alertDialogBuilder.show();


            }
        });

        db = new DatabaseHelper(this);


        arrayList.addAll(db.getallrecords());
        Log.e("Size", String.valueOf(arrayList.size()));

        recyclerView = findViewById(R.id.record_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recordAdapter = new RecordAdapter(arrayList, this);
        recyclerView.setAdapter(recordAdapter);

        total = findViewById(R.id.totalprice);
        total.setText("Rs " + String.valueOf(db.totalsum()));

    }

    @Override
    protected Dialog onCreateDialog(int id) {

        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        }
        return null;


    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    int month = arg2 + 1;
                    String dateformat;
                    Log.e("Date", arg1 + "-" + month + "-" + arg3);

                    if (arg3 < 10)
                        dateformat = arg1 + "-" + month + "-" + "0" + arg3;
                    else dateformat = arg1 + "-" + month + "-" + arg3;
                    Log.e("abccc", dateformat);
                    preferences.putString("Date", dateformat);
                    preferences.apply();

                    ArrayList<Records_model> filterarray = db.byDate(dateformat);
                    arrayList.clear();
                    arrayList.addAll(filterarray);
                    total.setText(String.valueOf(db.totalsumofDate(dateformat))+" rps");
                    recordAdapter.notifyDataSetChanged();

                }
            };


    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
    }

}

