package com.bawp.jesisproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


//import com.bawp.jesisproject.Adapter.UserAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class primeactivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private androidx.appcompat.widget.Toolbar toolbar;
    private NavigationView navigationView;
    private TextView nav_fullnam,nav_mail,nav_type;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    String newmail="";
    userAdapter3 useradapter;
    ArrayList<forDonated> list;

    DatabaseReference reff,refff,databaseReference;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeactivity);

        progressBar=findViewById(R.id.progressbar);

        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Donated to List");

        drawerLayout=findViewById(R.id.drawernavigation);
        navigationView=findViewById(R.id.navview);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(primeactivity.this,drawerLayout,
                toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
       drawerLayout.addDrawerListener(toggle);
       toggle.syncState();

       navigationView.setNavigationItemSelectedListener(this);

        nav_fullnam=navigationView.getHeaderView(0).findViewById(R.id.nav_user_fullnam);
        nav_mail=navigationView.getHeaderView(0).findViewById(R.id.nav_user_email);
        nav_type=navigationView.getHeaderView(0).findViewById(R.id.nav_user_type);


        recyclerView=findViewById(R.id.recycleview);

        //progressBar=findViewById(R.id.progressbar);



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
        databaseReference= FirebaseDatabase.getInstance().getReference().child("HomeDonors").child(newmail);
        list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        useradapter=new userAdapter3(this,list);
        recyclerView.setAdapter(useradapter);
        progressBar.setVisibility(View.GONE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        forDonated user = dataSnapshot.getValue(forDonated.class);
                        list.add(user);
                    }
                    useradapter.notifyDataSetChanged();
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
                Intent intent=new Intent(primeactivity.this,profileactivity.class);
                intent.putExtra("emaill",newmail);
                startActivity(intent);
                break;
            case R.id.logout:
                mAuth.signOut();
                Intent intent2=new Intent(primeactivity.this,loginactivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                break;
            case R.id.notifications:
                //mAuth.signOut();
                Intent intent3=new Intent(primeactivity.this,notificationactivity.class);
                intent3.putExtra("mail",newmail);
                startActivity(intent3);
                break;
            case R.id.about:
                Intent intent4=new Intent(primeactivity.this,about.class);
                //intent.putExtra("emaill",newmail);
                startActivity(intent4);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}