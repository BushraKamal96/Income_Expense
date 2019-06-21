package com.example.acer.incomeexpense.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acer.incomeexpense.Database.DatabaseHelper;
import com.example.acer.incomeexpense.R;
import com.example.acer.incomeexpense.models.Records_model;

import java.util.ArrayList;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.MyVHolder> {

    ArrayList<Records_model> arrayList = new ArrayList<>();
    Context context;
    DatabaseHelper db;

    public DailyAdapter(ArrayList<Records_model> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        db= new DatabaseHelper(this.context);
    }


    @NonNull
    @Override
    public MyVHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_recyclerview, parent, false);

        return new MyVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVHolder holder, int position) {
        final Records_model model = arrayList.get(position);
        holder.dailyitem.setText(model.getItem());
        holder.dailyprice.setText(String.valueOf(model.getRupee()) + " Rs");
        holder.recentmonth.setText(model.getOnlymonth());
        holder.recentyear.setText(model.getYear());
        holder.recentdate.setText(model.getOnlydate());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyVHolder extends RecyclerView.ViewHolder {

        TextView dailyitem, dailyprice, recentdate, recentmonth, recentyear ;

        public MyVHolder(@NonNull View view) {
            super(view);

            dailyitem = view.findViewById(R.id.dailyitem_name);
            dailyprice = view.findViewById(R.id.dailyitem_price);
            recentdate = view.findViewById(R.id.dailyentry_date);
            recentmonth = view.findViewById(R.id.dailymonth);
            recentyear = view.findViewById(R.id.dailyyear);

        }
    }
}
