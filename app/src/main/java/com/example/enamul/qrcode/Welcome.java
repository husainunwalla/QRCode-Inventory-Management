package com.example.enamul.qrcode;


import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

public class Welcome extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);



    }
    public void listView(View view){
        Intent intent = new Intent(Welcome.this, ViewItems.class);
        startActivity((intent));

    }

    public void displayQR(View view){
        Intent intent = new Intent(Welcome.this, DIsplayQR.class);
        startActivity(intent);

    }

    public void addInventory(View view){
        Intent intent = new Intent(Welcome.this, Generate.class);
        startActivity(intent);

    }
    public void scan(View view){
        Intent intent = new Intent(Welcome.this, Scan.class);
        startActivity(intent);

    }


}
