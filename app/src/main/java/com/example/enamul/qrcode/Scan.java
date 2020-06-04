package com.example.enamul.qrcode;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Scan extends AppCompatActivity {
    ImageView imageView;
    Button btnScan, mapButotn;
    EditText editText;
    Thread thread ;
    double lat, lng;
    TextView scanTextView, nameTextView, priceTextView, locationTextView;
    ProgressBar progressBarScan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        imageView = (ImageView)findViewById(R.id.imageView);
        editText = (EditText)findViewById(R.id.idEditText);
        btnScan = (Button)findViewById(R.id.btnScan);
        scanTextView = (TextView) findViewById(R.id.scanTextView);
        nameTextView = findViewById(R.id.nameTextView);
        priceTextView = findViewById(R.id.priceTextView);
        mapButotn = findViewById(R.id.mapButton);
        progressBarScan = findViewById(R.id.progressBarScan);
        locationTextView = findViewById(R.id.loactionTextView);

        mapButotn.setVisibility(View.INVISIBLE);
        progressBarScan.setVisibility(View.INVISIBLE);


        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nameTextView.setText("Loading...");
                priceTextView.setText("Loading...");
                progressBarScan.setVisibility(View.VISIBLE);

                IntentIntegrator integrator = new IntentIntegrator(Scan.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan QR Code");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

            }
        });

        mapButotn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Scan.this, MapsActivity.class);
                intent.putExtra("lat", Double.toString(lat));
                intent.putExtra("lng", Double.toString(lng));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.e("Scan*******", "Cancelled scan");

            } else {
                Log.e("Scan", "Scanned");

                scanTextView.setText(result.getContents());
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("inventory");

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        nameTextView.setText(dataSnapshot.child(result.getContents().toString()).child("name").getValue().toString());
                        priceTextView.setText(dataSnapshot.child(result.getContents().toString()).child("price").getValue().toString());
                        lat = (Double) dataSnapshot.child(result.getContents().toString()).child("lat").getValue();
                        lng = (Double) dataSnapshot.child(result.getContents().toString()).child("long").getValue();
                        locationTextView.setText("Lat: " + Double.toString(lat) + "Lng: " + Double.toString(lng));
                        mapButotn.setVisibility(View.VISIBLE);
                        progressBarScan.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Toast.makeText(getApplicationContext(), "Error " + databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    }
                });

                Toast.makeText(this, "Scanned ID : " + result.getContents(), Toast.LENGTH_SHORT).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
