package com.example.acer.incomeexpense.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acer.incomeexpense.DailyRecord;
import com.example.acer.incomeexpense.Database.DatabaseHelper;
import com.example.acer.incomeexpense.R;
import com.example.acer.incomeexpense.models.Loan_model;

import java.util.ArrayList;

public class DuedateAdapter extends RecyclerView.Adapter<DuedateAdapter.MyViewHolder> {

    ArrayList<Loan_model> arrayList=new ArrayList<>();
    Context context;
    DatabaseHelper db;

    public DuedateAdapter(ArrayList<Loan_model> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        db= new DatabaseHelper(this.context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.duedate_itemview,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        final Loan_model model = arrayList.get(i);
        holder.name.setText(model.getPersonName());
        holder.price.setText(model.getLoan() + " Rs");
        holder.date.setText(model.getOnlydate());
        holder.month.setText(model.getMonth());
        holder.year.setText(model.getYear());
        holder.type.setText(model.getLoan_type());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, price,date,month,year,type;

        public MyViewHolder(@NonNull View view) {
            super(view);

            name= view.findViewById(R.id.returnname);
            price= view.findViewById(R.id.returnAmount);
            date= view.findViewById(R.id.returnloandate);
            month= view.findViewById(R.id.returnmonth);
            year =view.findViewById(R.id.returnyear);
            type= view.findViewById(R.id.returntype);

        }
    }
}
