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
import com.example.acer.incomeexpense.models.Loan_model;

import java.util.ArrayList;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.ViewHolder> {

    ArrayList<Loan_model> arrayList = new ArrayList<>();
    Context context;

    DatabaseHelper db;


    public LoanAdapter(ArrayList<Loan_model> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        db = new DatabaseHelper(this.context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_itemview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final  ViewHolder holder, int position) {
        final Loan_model model = arrayList.get(position);
        holder.personname.setText(model.getPersonName());
        holder.loan.setText("Rs " + String.valueOf(model.getLoan()));
        holder.time.setText(model.getOnlydate());
        holder.month.setText(model.getMonth());
        holder.year.setText(model.getYear());
        holder.duedate.setText(model.getReturn_date());
        holder.loan_type.setText(model.getLoan_type());
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.removeLoan(model.getId());
                updateArrayList(arrayList.get(holder.getAdapterPosition()), holder.getAdapterPosition(), model.getId());

            }
        });
    }
    private void updateArrayList(Loan_model model, int position, String id) {
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView personname, loan, time, month, year, duedate, loan_type;
        Button del;
        View holderview;

        public ViewHolder(@NonNull View view) {
            super(view);

            holderview=  view;
            personname = view.findViewById(R.id.person_name);
            loan = view.findViewById (R.id.rupee_given);
            time = view.findViewById (R.id.loan_date);
            month = view.findViewById(R.id.loan_month);
            year = view.findViewById(R.id.loan_year);
            del = view.findViewById(R.id.delete_record);
            duedate = view.findViewById(R.id.returndate);
            loan_type = view.findViewById(R.id.loantype);



        }
    }
}
