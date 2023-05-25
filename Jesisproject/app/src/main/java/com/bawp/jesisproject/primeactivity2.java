package com.bawp.jesisproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class primeactivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    private DrawerLayout drawerLayout;
    private androidx.appcompat.widget.Toolbar toolbar;
    private NavigationView navigationView;
    private TextView nav_fullnam,nav_mail,nav_type;
    //private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Button aplus,bplus,aminus,bminus,oplus,ominus,abplus,abminus;
    String newmail="";

    DatabaseReference reff,refff;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeactivity2);

        progressBar=findViewById(R.id.progressbar);

        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Need Blood");

        drawerLayout=findViewById(R.id.drawernavigation);
        navigationView=findViewById(R.id.navview);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(primeactivity2.this,drawerLayout,
                toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        aplus=findViewById(R.id.aplus);
        aplus.setOnClickListener(this);
        aminus=findViewById(R.id.aminus);
        aminus.setOnClickListener(this);
        bplus=findViewById(R.id.bplus);
        bplus.setOnClickListener(this);
        bminus=findViewById(R.id.bminus);
        bminus.setOnClickListener(this);
        oplus=findViewById(R.id.oplus);
        oplus.setOnClickListener(this);
        ominus=findViewById(R.id.ominus);
        ominus.setOnClickListener(this);
        abplus=findViewById(R.id.abplus);
        abplus.setOnClickListener(this);
        abminus=findViewById(R.id.abminus);
        abminus.setOnClickListener(this);

        nav_fullnam=navigationView.getHeaderView(0).findViewById(R.id.nav_user_fullnam);
        nav_mail=navigationView.getHeaderView(0).findViewById(R.id.nav_user_email);
        nav_type=navigationView.getHeaderView(0).findViewById(R.id.nav_user_type);

        String mail=getIntent().getExtras().getString("email");

        newmail=mail.replace(".",",");

        reff= FirebaseDatabase.getInstance().getReference().child("Common").child(newmail);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String bloodgrp=snapshot.child("bloodgroup").getValue().toString();
                    if(!bloodgrp.isEmpty()) {

                        String name = snapshot.child("name").getValue().toString();
                        nav_fullnam.setText(name);

                        String email = snapshot.child("email").getValue().toString();
                        nav_mail.setText(email);

                        nav_type.setText("Donor");
                        progressBar.setVisibility(View.GONE);
                    }
                    else
                    {
                        String name=snapshot.child("name").getValue().toString();
                        nav_fullnam.setText(name);

                        String email=snapshot.child("email").getValue().toString();
                        nav_mail.setText(email);


                        nav_type.setText("Recipient");
                        progressBar.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.profile:
                Intent intent=new Intent(primeactivity2.this,profileactivity.class);
                intent.putExtra("emaill",newmail);
                startActivity(intent);
                break;
            case R.id.logout:
                mAuth.signOut();
                Intent intent2=new Intent(primeactivity2.this,loginactivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                break;
            case R.id.about:
                Intent intent4=new Intent(primeactivity2.this,about.class);
                //intent.putExtra("emaill",newmail);
                startActivity(intent4);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.aplus:
                Intent intent=new Intent(primeactivity2.this,Apositiveactivity.class);
                intent.putExtra("bloodgroup","A+");
                intent.putExtra("mail",newmail);
                startActivity(intent);
                break;
            case R.id.aminus:
                Intent intent1=new Intent(primeactivity2.this,Apositiveactivity.class);
                intent1.putExtra("bloodgroup","A-");
                intent1.putExtra("mail",newmail);
                startActivity(intent1);
                break;
            case R.id.bplus:
                Intent intent2=new Intent(primeactivity2.this,Apositiveactivity.class);
                intent2.putExtra("bloodgroup","B+");
                intent2.putExtra("mail",newmail);
                startActivity(intent2);
                break;
            case R.id.bminus:
                Intent intent3=new Intent(primeactivity2.this,Apositiveactivity.class);
                intent3.putExtra("bloodgroup","B-");
                intent3.putExtra("mail",newmail);
                startActivity(intent3);
                break;
            case R.id.oplus:
                Intent intent4=new Intent(primeactivity2.this,Apositiveactivity.class);
                intent4.putExtra("bloodgroup","O+");
                intent4.putExtra("mail",newmail);
                startActivity(intent4);
                break;
            case R.id.ominus:
                Intent intent5=new Intent(primeactivity2.this,Apositiveactivity.class);
                intent5.putExtra("bloodgroup","O-");
                intent5.putExtra("mail",newmail);
                startActivity(intent5);
                break;
            case R.id.abplus:
                Intent intent6=new Intent(primeactivity2.this,Apositiveactivity.class);
                intent6.putExtra("bloodgroup","AB+");
                intent6.putExtra("mail",newmail);
                startActivity(intent6);
                break;
            case R.id.abminus:
                Intent intent7=new Intent(primeactivity2.this,Apositiveactivity.class);
                intent7.putExtra("bloodgroup","AB-");
                intent7.putExtra("mail",newmail);
                startActivity(intent7);
                break;
        }
    }
}