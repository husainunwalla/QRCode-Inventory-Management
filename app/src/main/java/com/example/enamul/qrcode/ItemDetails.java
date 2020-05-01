package com.example.enamul.qrcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ItemDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        int position = getIntent().getExtras().getInt("position");
        Toast.makeText(this, "Position "+Integer.toString(position), Toast.LENGTH_SHORT).show();
    }
}
