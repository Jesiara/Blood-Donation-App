package com.bawp.jesisproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginactivity extends AppCompatActivity {

    private TextView backbutton,forgotpassword;
    private TextInputEditText email,password;
    private Button signin;

    private ProgressDialog loader;
    DatabaseReference reff;
    //private FirebaseAuth.AuthStateListener authStateListener;

    FirebaseAuth mAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);


        /*authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=mAuth.getCurrentUser();
                if(user!=null)
                {
                    Intent intent = new Intent(loginactivity.this, primeactivity.class);
                    startActivity(intent);
                    //finish();
                }

            }
        };*/

        backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginactivity.this, selectregistrationactivity.class);
                startActivity(intent);
            }
        });
        email=findViewById(R.id.registeremail);
        password=findViewById(R.id.registerpassword);
        signin=findViewById(R.id.registerbutton);
        forgotpassword=findViewById(R.id.forgotpassword);
        loader=new ProgressDialog(this);




        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail=email.getText().toString().trim();
                String pass=password.getText().toString().trim();

                if(mail.isEmpty())
                {
                    email.setError("Enter an email address");
                    email.requestFocus();
                    return;
                }
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches())
                {
                    email.setError("Enter a valid email address");
                    email.requestFocus();
                    return;
                }
                if(pass.isEmpty())
                {
                    password.setError("Enter a password");
                    password.requestFocus();
                    return;
                }
                if(pass.length()<6) {
                    password.setError("Enter valid password");
                    password.requestFocus();
                    return;
                }else {
                    //loader.setMessage("Logging....");
                   // loader.setCanceledOnTouchOutside(false);
                   // loader.show();


                    mAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(loginactivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                String newmail=mail.replace(".",",");

                                reff= FirebaseDatabase.getInstance().getReference().child("Common").child(newmail);
                                reff.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){
                                            String bloodgrp=snapshot.child("bloodgroup").getValue().toString();
                                            if(!bloodgrp.isEmpty()) {

                                                Intent intent = new Intent(loginactivity.this, primeactivity.class);
                                                intent.putExtra("email",mail);
                                                startActivity(intent);
                                            }
                                            else
                                            {
                                                Intent intent = new Intent(loginactivity.this, primeactivity2.class);
                                                intent.putExtra("email",mail);
                                                startActivity(intent);

                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                //finish();
                            } else {
                                Toast.makeText(loginactivity.this,task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                            //loader.dismiss();

                        }
                    });
                }
            }
        });


    }

   /* @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }*/
}