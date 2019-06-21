package com.example.acer.incomeexpense;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.incomeexpense.Database.DatabaseHelper;
import com.example.acer.incomeexpense.models.User_model;

public class UserInfo extends AppCompatActivity {

    ImageView wallet, limit;
    TextView mywallet, expenseLimit;

    Button btn_done, summary;
    DatabaseHelper db;

    String currentBalance, currentLimit;

    public static String usernum="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        usernum = getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).getString("Number", "0");


        db= new DatabaseHelper(this);
        User_model user=  db.getUserWallet(usernum);
        String userWalet = user.getWallet();
        String userLimit = user.getLimit();

        wallet = findViewById(R.id.user_wallet);
        limit = findViewById(R.id.user_walletlimit);
        summary = findViewById(R.id.sumry);
        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(UserInfo.this, DailyRecord.class);
                startActivity(intent);
            }
        });

        mywallet = findViewById(R.id.mywallet);
        mywallet.setText(userWalet);
        expenseLimit = findViewById(R.id.myexpenselimit);
        expenseLimit.setText(userLimit);

        btn_done= findViewById(R.id.done);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserInfo.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showEditWalletDialog();

            }
        });

        limit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showEditLimitDialog();


            }
        });

    }

    private void showEditLimitDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edit_alertdialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle("Add Daily Expense Limit");
        dialogBuilder.setMessage("Enter Amount Below");


        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                currentLimit= edt.getText().toString();
                expenseLimit.setText(currentLimit);
                db.updateLimit(currentLimit);


            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
                dialog.dismiss();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


    private void showEditWalletDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edit_alertdialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle("Add Current Balance");
        dialogBuilder.setMessage("Enter Amount Below");


        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                currentBalance= edt.getText().toString();
                mywallet.setText(currentBalance);
                db.updateWallet(currentBalance);

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
                dialog.dismiss();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();



    }


}
