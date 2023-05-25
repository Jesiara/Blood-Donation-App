package com.bawp.jesisproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class userAdapter2 extends RecyclerView.Adapter<userAdapter2.ViewHolder> {

    private Context context;
    private List<ForNotf> usersList;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Notification");
    String mail;

    public userAdapter2(Context context, List<ForNotf> usersList,String mail) {
        this.context = context;
        this.usersList = usersList;
        this.mail=mail;
    }

    @NonNull
    @Override
    public userAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.userdisplaylayout2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userAdapter2.ViewHolder holder, int position) {

        final ForNotf user=usersList.get(position);

        String maill=user.getEmail().toString().replace(",",".");


        holder.batch.setText(user.getBatch());
        holder.useremail.setText(maill);
        holder.phnnumber.setText(user.getNumber());
        holder.username.setText(user.getName());
        holder.sms.setText(user.getSms());
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
            sms=itemView.findViewById(R.id.sms);
            itemView.findViewById(R.id.donate).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    Intent intent=new Intent(context,donated.class);
                    String mail1=usersList.get(getAdapterPosition()).getEmail();
                    String newmail=mail1.replace(".",",");

                    databaseReference.child(mail).child(newmail).removeValue();

                    intent.putExtra("donormail",mail);
                    intent.putExtra("mailRecipient",usersList.get(getAdapterPosition()).getEmail());
                    intent.putExtra("nameRecipient",usersList.get(getAdapterPosition()).getName());
                    intent.putExtra("nmbrRecipient",usersList.get(getAdapterPosition()).getNumber());
                    intent.putExtra("batchRecipient",usersList.get(getAdapterPosition()).getBatch());
                    context.startActivity(intent);
                }
            });
        }
    }
}

