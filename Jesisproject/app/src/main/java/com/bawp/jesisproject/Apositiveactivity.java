package com.bawp.jesisproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Apositiveactivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;
    private ProgressBar progressBar;
     RecyclerView recyclerView;
    List<Users> usersList=new ArrayList<>();
     userAdapter useradapter;
    DatabaseReference databaseReference,reff;
    ArrayList<Users> list;
    String nm,nmbr,btch,rnam,rbatch,rphnnmbr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apositiveactivity);

        progressBar=findViewById(R.id.progressbar);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Donors");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        //recyclerView=findViewById(R.id.recycleview);

        progressBar=findViewById(R.id.progressbar);
        recyclerView=findViewById(R.id.recycleview);

        String bloodgroup=getIntent().getExtras().getString("bloodgroup").trim();
        String mail=getIntent().getExtras().getString("mail").trim();
        list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.GONE);
        /*reff= FirebaseDatabase.getInstance().getReference().child("Recipients").child(mail);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {

                        rnam=snapshot.child("name").getValue().toString();

                        rbatch=snapshot.child("batch").getValue().toString();

                        rphnnmbr=snapshot.child("phonennumber").getValue().toString();
                        set(rnam,rphnnmbr,rbatch);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        useradapter=new userAdapter(this,list,mail);
        //useradapter=new userAdapter(this,list,mail,nm,nmbr,btch);
        recyclerView.setAdapter(useradapter);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Donors").child(bloodgroup);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Users user = dataSnapshot.getValue(Users.class);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                /*Intent intent = new Intent(Apositiveactivity.this, primeactivity2.class);
                startActivity(intent);*/
                finish();
                return true;
            //break;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void set(String nm,String nmbr,String btch){
        this.nm=nm;
        this.nmbr=nmbr;
        this.btch=btch;
    }
}