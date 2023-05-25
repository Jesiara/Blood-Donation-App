package com.bawp.jesisproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class notf extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;
    String name,email,donorEmail,batch,donornumber,number;
    DatabaseReference databaseReference,reff;
    private TextView donornumbertext,recipientnumbertext,recipientemailtext,rname,rbatch;
    private Button send;
    private TextInputEditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notf);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Send Message to Donor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        donornumbertext=findViewById(R.id.donornumber);
        recipientemailtext=findViewById(R.id.recipientmail);
        recipientnumbertext=findViewById(R.id.recipientnumber);
        send=findViewById(R.id.send);
        message=findViewById(R.id.message);
        rname=findViewById(R.id.name);
        rbatch=findViewById(R.id.batch);

        donornumber=getIntent().getExtras().getString("numberDonor").trim();
        donornumbertext.setText(donornumber);

        donorEmail=getIntent().getExtras().getString("mailDonor").trim();

        email=getIntent().getExtras().getString("recipientemail");
        String newmail=email.replace(",",".");
        recipientemailtext.setText(newmail);



        reff=FirebaseDatabase.getInstance().getReference().child("Recipients").child(email);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {

                        String name=snapshot.child("name").getValue().toString();
                        rname.setText(name);

                        String batch=snapshot.child("batch").getValue().toString();
                        rbatch.setText(batch);

                        String phnnmbr=snapshot.child("phonennumber").getValue().toString();
                        recipientnumbertext.setText(phnnmbr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(notf.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)
                {
                    sendSMS();
                }
                else {
                    ActivityCompat.requestPermissions(notf.this,new String[]{Manifest.permission.SEND_SMS},100);
                }

            }
        });

        //Toast.makeText(this, "Request Sent!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100&&grantResults.length>=0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            sendSMS();
        }
        else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendSMS()
    {
        String phoneno=donornumbertext.getText().toString().trim();
        String SMS=message.getText().toString().trim();
        String rnumber=recipientnumbertext.getText().toString().trim();
        String newmail=recipientemailtext.getText().toString().trim();
        String batch=rbatch.getText().toString().trim();
        String name=rname.getText().toString().trim();

        String newDonor=donorEmail.replace(".",",");
        String newmailrecipient=newmail.replace(".",",");


        try {

            databaseReference= FirebaseDatabase.getInstance().getReference().child("Notification");
            ForNotf forNotf=new ForNotf();
            forNotf.setNumber(rnumber);
            forNotf.setEmail(newmailrecipient);
            forNotf.setBatch(batch);
            forNotf.setName(name);
            forNotf.setSms(SMS);
            databaseReference.child(newDonor).child(newmailrecipient).setValue(forNotf);
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(phoneno,null,"Message :"+SMS+"\n"+"Recipient's Number :"+rnumber+"\n"+"Recipient's email :"+newmail,null,null);
        Toast.makeText(this, "Message is sent!", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
        e.printStackTrace();
        Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}