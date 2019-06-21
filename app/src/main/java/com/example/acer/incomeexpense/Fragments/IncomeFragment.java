package com.example.acer.incomeexpense.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.acer.incomeexpense.Common;
import com.example.acer.incomeexpense.Database.DatabaseHelper;
import com.example.acer.incomeexpense.IncomeActivity;
import com.example.acer.incomeexpense.LoginActivity;
import com.example.acer.incomeexpense.R;
import com.example.acer.incomeexpense.Records;
import com.example.acer.incomeexpense.Service.ReminderService;
import com.example.acer.incomeexpense.UserInfo;
import com.example.acer.incomeexpense.models.Income_model;
import com.example.acer.incomeexpense.models.Records_model;
import com.example.acer.incomeexpense.models.User_model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {


    EditText source, cash;
    ImageView logout, user;
    Toolbar toolbar;
    Button add, show;
    DatabaseHelper db;

    Integer walletAmount, income;


    public static String usernum;
    public static String date;

    SharedPreferences.Editor getPreferences;

    private ArrayList<Income_model> arrayList = new ArrayList<>();

    private SharedPreferences.Editor preferences;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd");
    SimpleDateFormat simpleDateFormatmonth = new SimpleDateFormat("yyyy-M");
    SimpleDateFormat simpleDateFormatyear = new SimpleDateFormat("yyyy");
    SimpleDateFormat simpleDate = new SimpleDateFormat("dd");
    SimpleDateFormat simpleMonth = new SimpleDateFormat("MMM");



    public IncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_income, container, false);

        db = new DatabaseHelper(getActivity());


        getPreferences = getActivity().getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).edit();
        usernum=  getActivity().getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).getString("Number", "0");

        preferences = getActivity().getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).edit();

        User_model user_model =db.getUserWallet(usernum);
        final Integer UserWallet = Integer.valueOf(user_model.getWallet());


        source = view.findViewById(R.id.income_source);
        cash = view.findViewById(R.id.cash);

        user= view.findViewById(R.id.cashinfo);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserInfo.class);
                startActivity(intent);
            }
        });


        add = view.findViewById(R.id.add_income);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sourceitem = source.getText().toString();
                String rupee = cash.getText().toString();

                if (!TextUtils.isEmpty(sourceitem) && !TextUtils.isEmpty(rupee)) {

                  String total = String.valueOf(rupee);
                    preferences.putString("Total", total);
                    preferences.apply();

                    long id = 0;

                    income = Integer.valueOf(rupee);
                    walletAmount= UserWallet + income;

                    Log.e("Month", String.valueOf(Calendar.getInstance().get(Calendar.MONTH)));

                    id = db.insertIncomeData(sourceitem, rupee,
                            simpleDateFormat.format(Calendar.getInstance().getTime()),
                            simpleDateFormatmonth.format(Calendar.getInstance().getTime()),
                            simpleDateFormatyear.format(Calendar.getInstance().getTime()),
                            simpleDate.format(Calendar.getInstance().getTime()),
                            simpleMonth.format(Calendar.getInstance().getTime()),usernum);

                    db.updateWallet(walletAmount.toString());


                    Log.e("date", simpleDateFormat.format(Calendar.getInstance().getTime()));
                    Log.e("month", simpleDateFormatmonth.format(Calendar.getInstance().getTime()));
                    Log.e("year", simpleDateFormatyear.format(Calendar.getInstance().getTime()));

//                    Income_model model = db.getalldataincome(id);
//                    arrayList.add(model);
                   source.setText("");
                    cash.setText("");
                    Toast.makeText(getActivity().getApplication(), "Added", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getActivity().getApplication(), "Field is Empty", Toast.LENGTH_SHORT).show();
                }

                }
        });

        show = view.findViewById(R.id.show_income);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), IncomeActivity.class);
                startActivity(intent);

            }
        });

        toolbar = view.findViewById(R.id.incometoolbar);
        logout = view.findViewById(R.id.in_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Logout !");
                builder.setMessage("Are you sure you want to Logout ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        preferences.clear();
                        preferences.apply();
                        startActivity(intent);
                        Intent intent1 = new Intent(getActivity(), ReminderService.class);
                        getActivity().stopService(intent1);

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();

            }
        });


        return view;
    }

}
