package com.bawp.jesisproject;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class userAdapter extends RecyclerView.Adapter<userAdapter.ViewHolder> {

    private Context context;
    private List<Users> usersList;
    String mail,nam,numbr,btch;

    public userAdapter(Context context, List<Users> usersList,String email) {
        this.context = context;
        this.usersList = usersList;
       this.mail=email;
      // this.nam=name;
       //this.numbr=number;
       //this.btch=batch;
    }

    @NonNull
    @Override
    public userAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.userdisplaylayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userAdapter.ViewHolder holder, int position) {

        final Users user=usersList.get(position);

        holder.batch.setText(user.getBatch());
        holder.useremail.setText(user.getEmail());
        holder.phnnumber.setText(user.getPhonennumber());
        holder.username.setText(user.getName());
        holder.bloodgroup.setText(user.getBloodgroup());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView batch,username,useremail,phnnumber,bloodgroup;
        public Button emailnow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            batch=itemView.findViewById(R.id.batch);
            username=itemView.findViewById(R.id.username);
            useremail=itemView.findViewById(R.id.useremail);
            phnnumber=itemView.findViewById(R.id.phnnumber);
            bloodgroup=itemView.findViewById(R.id.bloodgroup);
           // emailnow=itemView.findViewById(R.id.emailnow);

            itemView.findViewById(R.id.emailnow).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,notf.class);
                    intent.putExtra("recipientemail",mail);
                    intent.putExtra("numberDonor",usersList.get(getAdapterPosition()).getPhonennumber());
                    intent.putExtra("mailDonor",usersList.get(getAdapterPosition()).getEmail());
                    context.startActivity(intent);
                    //Toast.makeText(context, "Request Sent!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
