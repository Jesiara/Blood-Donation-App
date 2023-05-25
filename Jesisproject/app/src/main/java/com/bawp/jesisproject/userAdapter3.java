package com.bawp.jesisproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class userAdapter3 extends RecyclerView.Adapter<userAdapter3.ViewHolder> {
    private Context context;
    private List<forDonated> usersList;

    String email;

    public userAdapter3(Context context, List<forDonated> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public userAdapter3.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.userdisplaylayout3,parent,false);
        return new userAdapter3.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userAdapter3.ViewHolder holder, int position) {

        final forDonated user=usersList.get(position);

        String newmail=user.getEmail().replace(",",".");

        holder.batch.setText(user.getBatch());
        holder.useremail.setText(newmail);
        holder.phnnumber.setText(user.getNumber());
        holder.username.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView batch,username,useremail,phnnumber,sms;
        Button donate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            batch=itemView.findViewById(R.id.batch);
            username=itemView.findViewById(R.id.username);
            useremail=itemView.findViewById(R.id.useremail);
            phnnumber=itemView.findViewById(R.id.phnnumber);



        }
    }

}
