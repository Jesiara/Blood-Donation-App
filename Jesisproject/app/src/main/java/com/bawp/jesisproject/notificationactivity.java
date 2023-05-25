package com.bawp.jesisproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class notificationactivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;
    private ProgressBar progressBar;
    RecyclerView recyclerView;
    userAdapter2 useradapter;
    DatabaseReference databaseReference;
    ArrayList<ForNotf> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationactivity);
        progressBar=findViewById(R.id.progressbar);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("People who request for blood");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        recyclerView=findViewById(R.id.recycleview);

        progressBar=findViewById(R.id.progressbar);

        String email=getIntent().getExtras().getString("mail").trim();
        String newmail=email.replace(".",",");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Notification").child(newmail);
        list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        useradapter=new userAdapter2(this,list,newmail);
        recyclerView.setAdapter(useradapter);
        progressBar.setVisibility(View.GONE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ForNotf user = dataSnapshot.getValue(ForNotf.class);
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
}