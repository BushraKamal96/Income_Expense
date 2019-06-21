package com.example.acer.incomeexpense.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acer.incomeexpense.R;
import com.example.acer.incomeexpense.models.User_model;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    ArrayList<User_model> arrayList = new ArrayList<>();
    Context context;

    public UserAdapter(ArrayList<User_model> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_recyclerview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        User_model model = arrayList.get(position);
        holder.email.setText(model.getEmail());
        holder.name.setText(model.getName());
        holder.number.setText(model.getNumber());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView email, name, number;


        public MyViewHolder(@NonNull View view) {
            super(view);

            email = view.findViewById(R.id.userEmail);
            name = view.findViewById(R.id.userName);
            number = view.findViewById(R.id.userNum);

        }
    }


}
