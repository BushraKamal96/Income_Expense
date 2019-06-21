package com.example.acer.incomeexpense.Fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.incomeexpense.Common;
import com.example.acer.incomeexpense.Database.DatabaseHelper;
import com.example.acer.incomeexpense.LoanActivity;
import com.example.acer.incomeexpense.LoginActivity;
import com.example.acer.incomeexpense.R;
import com.example.acer.incomeexpense.Service.ReminderService;
import com.example.acer.incomeexpense.UserInfo;
import com.example.acer.incomeexpense.models.Loan_model;
import com.example.acer.incomeexpense.models.User_model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoanFragment extends Fragment {

    EditText person, givenloan, loan_type, due;
    ImageView logout, user;
    Toolbar toolbar;
    Button save, show;
    String returndate1;
    String loanType;

    Integer walletAmount, money;

    DatabaseHelper db;
    public static String usernum;
    public static String date;
    Typeface tf;
    SharedPreferences.Editor getPreferences;

    private ArrayList<Loan_model> arrayList = new ArrayList<>();

    private SharedPreferences.Editor preferences;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
    SimpleDateFormat simpleDateFormatmonth = new SimpleDateFormat("MMM");
    SimpleDateFormat simpleDateFormatyear = new SimpleDateFormat("yyyy");

    SimpleDateFormat returnDateFormat = new SimpleDateFormat("yyyy-M-dd");



    final Calendar myCalendar = Calendar.getInstance();

    public LoanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loan, null);

        db = new DatabaseHelper(getActivity());


        getPreferences = getActivity().getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).edit();
        usernum = getActivity().getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).getString("Number", "0");

        preferences = getActivity().getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).edit();

        User_model user_model =db.getUserWallet(usernum);
          final Integer UserWallet = Integer.valueOf(user_model.getWallet());


        toolbar = view.findViewById(R.id.toolbar4);

        user= view.findViewById(R.id.cashinfo);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserInfo.class);
                startActivity(intent);
            }
        });


        loan_type = view.findViewById(R.id.inputype);
        loan_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] choose = {"Given", "Taken"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pick one");
                builder.setItems(choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on colors[which]
                        loanType = choose[which];
                        loan_type.setText(loanType);
                    }
                });
                builder.show();



            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                if (myCalendar.get(Calendar.DAY_OF_MONTH) < 10 && myCalendar.get(Calendar.MONTH)<10) {
//                    String day = "0" + myCalendar.get(Calendar.DAY_OF_MONTH);
//                    String month= "0"+(myCalendar.get(Calendar.MONTH)+1);
//
//                    returndate1=String.valueOf(myCalendar.get(Calendar.YEAR))+ "-" + "0" +
//                            String.valueOf(myCalendar.get(Calendar.MONTH)+1)+"-"+ "0" +
//                            String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH));
//
//                }
//
//                else if (myCalendar.get(Calendar.DAY_OF_MONTH) > 10 && myCalendar.get(Calendar.MONTH)<10) {
//                    returndate1=String.valueOf(myCalendar.get(Calendar.YEAR))+ "-" + "0" +
//                            String.valueOf(myCalendar.get(Calendar.MONTH)+1)+"-"+
//                            String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH));
//                }
//
//                else if (myCalendar.get(Calendar.DAY_OF_MONTH) < 10 && myCalendar.get(Calendar.MONTH)>10) {
//                    returndate1=String.valueOf(myCalendar.get(Calendar.YEAR))+
//                            "-"+ String.valueOf(myCalendar.get(Calendar.MONTH)+1)+"-"+ "0" +
//                            String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH));
//                }
//
//
//
//                else {
//                    returndate1=String.valueOf(myCalendar.get(Calendar.YEAR))+"-"+String.valueOf(myCalendar.get(Calendar.MONTH)+1)+"-"+   String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH));
//
//
//                }
                String monthVar = monthOfYear < 10 ? "0" +( monthOfYear +1): ( monthOfYear +1)+ "";
               // returndate1 = year + "-" + monthVar + "-" + dayOfMonth;
                due.setText(returndate1);

                String dateVar = dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth + "";
                returndate1 = year + "-" + monthVar + "-" + dateVar;
                due.setText(returndate1);


            }

        };

        due = view.findViewById(R.id.hhhh);
        due.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                // arg2 = month
                // arg3 = day


            }
        });


        person = view.findViewById(R.id.loanperson);
        givenloan = view.findViewById(R.id.loanrupee);

        save = view.findViewById(R.id.add_loan);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = person.getText().toString();
                String rupee = givenloan.getText().toString();

                money= Integer.valueOf(rupee);

                if(loanType.equals("Taken")){

                    walletAmount= UserWallet + money;
                    Log.e("updateWallet1", walletAmount.toString());

                }

                if(loanType.equals("Given")){

                    walletAmount= UserWallet - money;
                    Log.e("updateWallet2", walletAmount.toString());

                }


                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(rupee)) {

                    long id = 0;

                    id = db.insertLoanData(name, rupee,
                            simpleDateFormat.format(Calendar.getInstance().getTime()),
                            simpleDateFormatmonth.format(Calendar.getInstance().getTime()),
                            simpleDateFormatyear.format(Calendar.getInstance().getTime()),
                            usernum, returndate1, loanType, returnDateFormat.format(Calendar.getInstance().getTime()));

                    Log.e("updateWallet", walletAmount.toString());


                    db.updateWallet(walletAmount.toString());



                    Log.e("date", simpleDateFormat.format(Calendar.getInstance().getTime()));
                    Log.e("month", simpleDateFormatmonth.format(Calendar.getInstance().getTime()));
                    Log.e("year", simpleDateFormatyear.format(Calendar.getInstance().getTime()));

                    Loan_model model = db.getallloan(id);
                    arrayList.add(model);
                    person.setText("");
                    givenloan.setText("");
                    loan_type.setText("");
                    due.setText("Set Due Date (Optional)");
                    Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getActivity(), "Field is Empty", Toast.LENGTH_SHORT).show();
                }
            }

        });
        show = view.findViewById(R.id.show_loans);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoanActivity.class);
                startActivity(intent);

            }
        });

        logout = view.findViewById(R.id.logout);
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
