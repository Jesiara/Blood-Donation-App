package com.bawp.jesisproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class recipientregistrationactivity extends AppCompatActivity {

    private TextView backbutton;
    private Button signup;
    private TextInputEditText fullname,id,email,password,phnnumber;

    DatabaseReference reff;
    private ProgressDialog loader;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipientregistrationactivity);

        backbutton = findViewById(R.id.backbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(recipientregistrationactivity.this,loginactivity.class);
                startActivity(intent);
            }
        });

        signup = findViewById(R.id.registerbutton);
        fullname = findViewById(R.id.registerfullname);
        id = findViewById(R.id.registeridno);
        email = findViewById(R.id.registeremail);
        password = findViewById(R.id.registerpassword);
        phnnumber = findViewById(R.id.registerphonenumber);
        loader=new ProgressDialog(this);



       Users studdent=new Users();

        reff= FirebaseDatabase.getInstance().getReference();



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullnam = fullname.getText().toString();
                String idd = id.getText().toString().trim();
                String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String phnno = phnnumber.getText().toString().trim();


                if (fullnam.isEmpty()) {
                    fullname.setError("Enter your full name");
                    fullname.requestFocus();
                    return;
                }
                if (idd.isEmpty()) {
                    id.setError("Enter your id");
                    id.requestFocus();
                    return;
                }
                if (phnno.isEmpty()) {
                    phnnumber.setError("Enter phone number");
                    phnnumber.requestFocus();
                    return;
                }
                if (mail.isEmpty()) {
                    email.setError("Enter an email address");
                    email.requestFocus();
                    return;
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                    email.setError("Enter a valid email address");
                    email.requestFocus();
                    return;
                }
                if (pass.isEmpty()) {
                    password.setError("Enter a password");
                    password.requestFocus();
                    return;
                }
                if (pass.length() < 6) {
                    password.setError("Enter valid password");
                    password.requestFocus();
                    return;
                } else {
                    //loader.setMessage("Registering....");
                    //loader.setCanceledOnTouchOutside(false);
                    //loader.show();

                    mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                String error = task.getException().toString();
                                Toast.makeText(recipientregistrationactivity.this, "Error" + error, Toast.LENGTH_SHORT).show();

                            } else {

                                studdent.setName(fullnam);
                                studdent.setBatch(idd);
                                studdent.setPhonennumber(phnno);
                                studdent.setEmail(mail);
                                studdent.setBloodgroup("");
                                //String currentuserid = mAuth.getCurrentUser().getUid();

                                String newmail = mail.replace(".", ",");
                                reff.child("Recipients").child(newmail).setValue(studdent);
                                reff.child("Common").child(newmail).setValue(studdent);

                                Toast.makeText(recipientregistrationactivity.this, "Registration Successful!Click sign in", Toast.LENGTH_SHORT).show();
                                //Intent intent = new Intent(recipientregistrationactivity.this, primeactivity.class);
                                //startActivity(intent);
                                //loader.dismiss();
                                // finish();
                            }


                        }
                    });
                }
            }
        });
    }
}