package com.example.enamul.qrcode;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Welcome extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button scan = findViewById(R.id.scanButton);
        Button generate = findViewById(R.id.generateButton);
        Button display = findViewById(R.id.displayButton);
        Button list = findViewById(R.id.listButton);



        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Scan.class);
                startActivity(intent);
            }
        });

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Generate.class);
                startActivity(intent);

            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, DIsplayQR.class);
                startActivity(intent);
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, ViewItems.class);
                startActivity((intent));
            }
        });


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
