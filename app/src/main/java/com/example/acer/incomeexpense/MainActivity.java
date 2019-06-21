package com.example.acer.incomeexpense;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.acer.incomeexpense.Fragments.IncomeFragment;
import com.example.acer.incomeexpense.Fragments.LoanFragment;
import com.example.acer.incomeexpense.Fragments.RecordFragment;
import com.example.acer.incomeexpense.Service.LoanService;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static String usernum="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernum = getSharedPreferences(Common.sharedPreferences, MODE_PRIVATE).getString("Number", "0");

        Intent intent = new Intent(MainActivity.this, LoanService.class);
        startService(intent);

        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(new RecordFragment());
    }

    private boolean loadFragment(Fragment fragment){

        if(fragment != null){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.framelayout, fragment)
                    .commit();


            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()){

            case R.id.navigation_record:
            fragment = new RecordFragment();
            break;

            case R.id.navigation_loan:
                fragment = new LoanFragment();
                break;

                case R.id.navigation_income:
                fragment = new IncomeFragment();
                break;
        }

        return loadFragment(fragment);
    }
}
