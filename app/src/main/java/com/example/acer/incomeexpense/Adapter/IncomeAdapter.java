package com.example.acer.incomeexpense.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.incomeexpense.Database.DatabaseHelper;
import com.example.acer.incomeexpense.R;
import com.example.acer.incomeexpense.models.Income_model;
import com.example.acer.incomeexpense.models.Records_model;

import java.util.ArrayList;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.MyViewHolder> {

    ArrayList<Income_model> arrayList = new ArrayList<>();
    Context context;

    DatabaseHelper db;

    public IncomeAdapter(ArrayList<Income_model> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        db = new DatabaseHelper(this.context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_recyclerview, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final  Income_model model = arrayList.get(position);
        holder.insource.setText(model.getSource());
        holder.inprice.setText("Rs " + String.valueOf(model.getRupee()));
        holder.time.setText(model.getOnlydate());
        holder.month.setText(model.getOnlymonth());
        holder.year.setText(model.getYear());
        holder.delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.removeIncome(model.getId());
                updateArrayList(arrayList.get(holder.getAdapterPosition()), holder.getAdapterPosition(), model.getId());

            }
        });



    }
    private void updateArrayList(Income_model model, int position, String id) {
        arrayList.remove(model);
        try {
            notifyItemRemoved(position);

        } catch (Exception e) {

            notifyItemRemoved(position);

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView insource, inprice, time, month, year;
        Button delet;
        View holderview;

        public MyViewHolder(@NonNull View view) {
            super(view);

            holderview = view;
            insource = view.findViewById(R.id.source_name);
            inprice = view.findViewById(R.id.cash_amount);
            time = view.findViewById(R.id.income_date);
            month = view.findViewById(R.id.income_month);
            year = view.findViewById(R.id.income_year);
            delet = view.findViewById(R.id.delete_income);


        }
    }
}
