package com.bawp.jesisproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileactivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;

    private TextView type,name,email,idnumber,phonenumber,bloodgrp;
    private Button backbutton;
    DatabaseReference reff;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileactivity);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);


        backbutton=findViewById(R.id.backbutton);
        type=findViewById(R.id.type);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        idnumber=findViewById(R.id.idnumber);
        phonenumber=findViewById(R.id.phnnumber);
        bloodgrp=findViewById(R.id.bloodgroup);

        mAuth=FirebaseAuth.getInstance();

        //String currentuserid=mAuth.getCurrentUser().getUid();

        String newmail=getIntent().getExtras().getString("emaill");

        reff= FirebaseDatabase.getInstance().getReference().child("Common").child(newmail);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String bldgrp=snapshot.child("bloodgroup").getValue().toString();

                    if(!bldgrp.isEmpty())
                    {
                        type.setText("Donor");

                        String nam=snapshot.child("name").getValue().toString();
                        name.setText(nam);

                        String idd=snapshot.child("batch").getValue().toString();
                        idnumber.setText(idd);

                        String phnnmbr=snapshot.child("phonennumber").getValue().toString();
                        phonenumber.setText(phnnmbr);

                        bloodgrp.setText(bldgrp);

                        String mail=snapshot.child("email").getValue().toString();
                        email.setText(mail);
                    }
                    else
                    {
                        type.setText("Recipient");

                        String nam=snapshot.child("name").getValue().toString();
                        name.setText(nam);

                        String idd=snapshot.child("batch").getValue().toString();
                        idnumber.setText(idd);

                        String phnnmbr=snapshot.child("phonennumber").getValue().toString();
                        phonenumber.setText(phnnmbr);

                        bloodgrp.setText(" ");

                        String mail=snapshot.child("email").getValue().toString();
                        email.setText(mail);
                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(profileactivity.this,primeactivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                //Intent intent=new Intent(profileactivity.this,primeactivity.class);
                //startActivity(intent);
                finish();
                return  true;
                //break;
            default:
                return super.onOptionsItemSelected(item);

        }


    }
}