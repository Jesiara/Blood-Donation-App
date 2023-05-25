package com.bawp.jesisproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private ImageView logo;
    private TextView title,slogan;

    Animation topanimation,bottomanimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        logo=findViewById(R.id.logo);
        title=findViewById(R.id.title);
        slogan=findViewById(R.id.slogan);
        topanimation= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomanimation=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        logo.setAnimation(topanimation);
        title.setAnimation(bottomanimation);
        slogan.setAnimation(bottomanimation);

        int SPLASH_SCREEN=4300;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user != null){
                    String mail=user.getEmail();
                    String newmail=mail.replace(".",",");

                    DatabaseReference reff= FirebaseDatabase.getInstance().getReference().child("Common").child(newmail);
                    reff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String bloodgrp=snapshot.child("bloodgroup").getValue().toString().trim();
                            if(!bloodgrp.isEmpty())
                            {
                                Intent intent=new Intent(MainActivity.this,primeactivity.class);
                                intent.putExtra("email", newmail);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Intent intent = new Intent(MainActivity.this, primeactivity2.class);
                                intent.putExtra("email",newmail);
                                startActivity(intent);
                                finish();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    Intent intent = new Intent(MainActivity.this, loginactivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_SCREEN);

    }
}