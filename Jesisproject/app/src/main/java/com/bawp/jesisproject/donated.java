package com.bawp.jesisproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class donated extends AppCompatActivity {
    DatabaseReference reff,reff2;
    private Button back;
    private androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donated);

        back=findViewById(R.id.backbutton);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Donated");
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowCustomEnabled(true);

        String email=getIntent().getExtras().getString("mailRecipient").trim();
        String name=getIntent().getExtras().getString("nameRecipient").trim();
        String phnnumber=getIntent().getExtras().getString("nmbrRecipient").trim();
        String batch=getIntent().getExtras().getString("batchRecipient").trim();
        String donormail=getIntent().getExtras().getString("donormail").trim();


        reff=FirebaseDatabase.getInstance().getReference().child("HomeDonors");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String donormail=getIntent().getExtras().getString("donoremail").trim();

                //reff2=FirebaseDatabase.getInstance().getReference().child("Notification").child(donormail);
                //reff2.removeValue();

                forDonated fordonated=new forDonated();


                fordonated.setName(name);
                fordonated.setBatch(batch);
                fordonated.setEmail(email);
                fordonated.setNumber(phnnumber);

                String mailr=email.replace(".",",");
                String maild=donormail.replace(".",",");
                reff.child(maild).child(mailr).setValue(fordonated);

                Intent intent=new Intent(donated.this,primeactivity.class);
                startActivity(intent);
                finish();

            }
        });



    }
    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                /*Intent intent = new Intent(Apositiveactivity.this, primeactivity2.class);
                startActivity(intent);*/
                //finish();
                //return true;
            //break;
            //default:
                //return super.onOptionsItemSelected(item);

        //}
   // }*/
}