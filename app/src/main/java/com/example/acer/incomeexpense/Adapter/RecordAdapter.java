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
import com.example.acer.incomeexpense.models.Records_model;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder> {

    ArrayList<Records_model> arrayList = new ArrayList<>();
    Context context;

    DatabaseHelper db;
    public RecordAdapter(ArrayList<Records_model> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        db = new DatabaseHelper(this.context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_recyclerviiew, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Records_model model = arrayList.get(position);
        holder.item.setText(model.getItem());
        holder.price.setText(" Rs " + String.valueOf(model.getRupee()) );
        holder.month.setText(model.getOnlymonth());
        holder.year.setText(model.getYear());
        holder.time.setText(model.getOnlydate());

        holder.location.setText(model.getAddress());
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.removeRecord(model.getId());
                updateArrayList(arrayList.get(holder.getAdapterPosition()), holder.getAdapterPosition(), model.getId());

            }
        });
    }
    private void updateArrayList(Records_model model, int position, String id) {
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

        TextView item, price, time, month, year, location;
        Button del;
        View holderview;

        public MyViewHolder(@NonNull View view) {
            super(view);

            holderview = view;
            item = view.findViewById(R.id.item_name);
            price = view.findViewById(R.id.item_price);
            time = view.findViewById(R.id.entry_time);
            del = view.findViewById(R.id.delete);
            month = view.findViewById(R.id.tvmonth);
            year = view.findViewById(R.id.tvyear);
            location = view.findViewById(R.id.location);

        }
    }
}