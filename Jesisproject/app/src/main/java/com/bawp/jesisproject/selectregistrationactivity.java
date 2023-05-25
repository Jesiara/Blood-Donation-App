package com.bawp.jesisproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class selectregistrationactivity extends AppCompatActivity {

    private Button recipientbtn,donorbtn;
    private TextView backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectregistrationactivity);
        recipientbtn=findViewById(R.id.recipientbutton);
        donorbtn=findViewById(R.id.donorbutton);
        backbutton=findViewById(R.id.backbutton);

        donorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(selectregistrationactivity.this,donorregistrationactivity.class);
                startActivity(intent);
            }
        });
        recipientbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(selectregistrationactivity.this,recipientregistrationactivity.class);
                startActivity(intent);
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(selectregistrationactivity.this,loginactivity.class);
                startActivity(intent);
            }
        });
    }
}