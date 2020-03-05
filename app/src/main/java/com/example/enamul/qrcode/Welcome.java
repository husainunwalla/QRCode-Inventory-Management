package com.example.enamul.qrcode;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {

    public void scan(View view){
        Intent intent = new Intent(Welcome.this, Scan.class);
        startActivity(intent);
    }

    public void generate(View view){
        Intent intent = new Intent(Welcome.this, Generate.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button scan = findViewById(R.id.scanButton);
        Button generate = findViewById(R.id.generateButton);


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


    }


}
