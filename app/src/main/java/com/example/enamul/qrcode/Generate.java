package com.example.enamul.qrcode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import static com.example.enamul.qrcode.R.drawable.ic_launcher_foreground;

public class Generate extends AppCompatActivity {
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                }


            }

        }
    }

    ImageView imageView;
    Button createButton, addAgainButton;
    EditText idEditText, priceEditText, nameEditText, locationText;
    String idString;
    public final static int QRcodeWidth = 350;
    Bitmap bitmap;
    LocationManager locationManager;
    LocationListener locationListener;
    Double latitude, longitude;


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("inventory");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);

        imageView = (ImageView) findViewById(R.id.imageView);
        idEditText = (EditText) findViewById(R.id.idEditText);
        createButton = (Button) findViewById(R.id.createButton);
        imageView.setImageResource(ic_launcher_foreground);
        idEditText.setFocusable(false);
        nameEditText = findViewById(R.id.nameEditText);
        priceEditText = findViewById(R.id.priceEditText);
        addAgainButton = findViewById(R.id.addButton);
        locationText = findViewById(R.id.locationText);

        idEditText.setText("Loading...");
        idRefresh();
        locationListener = new LocationListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                locationText.setText(Double.toString(latitude) + Double.toString(longitude));

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);


        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);



        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nameEditText.setFocusable(false);
                priceEditText.setFocusable(false);



                if(!idEditText.getText().toString().isEmpty()){
                    idString = idEditText.getText().toString();

                    try {
                        bitmap = TextToImageEncode(idString);
                        //Setting image to bitmap
                        imageView.setImageBitmap(bitmap);
                        databaseReference.child(idString).child("name").setValue(nameEditText.getText().toString());
                        databaseReference.child(idString).child("price").setValue(priceEditText.getText().toString());
                        databaseReference.child(idString).child("lat").setValue(latitude);
                        databaseReference.child(idString).child("long").setValue(longitude);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    idEditText.requestFocus();
                    Toast.makeText(Generate.this, "Please Enter Your Scanned Test" , Toast.LENGTH_LONG).show();
                }
                addAgainButton.setVisibility(View.VISIBLE);
                createButton.setVisibility(View.INVISIBLE);

            }
        });



    }

    //Generate QR Code Bitmap
    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor):getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 350, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    public void idRefresh(){
        databaseReference.getParent().child("id").getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                idEditText.setText(dataSnapshot.getValue().toString());
                databaseReference.getParent().child("id").setValue(Integer.valueOf(Integer.parseInt(dataSnapshot.getValue().toString()) + 1));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void addAgain(View view){
        idEditText.setText("Loading...");
        idRefresh();
        nameEditText.setFocusable(true);
        nameEditText.setFocusableInTouchMode(true);
        priceEditText.setFocusable(true);
        priceEditText.setFocusableInTouchMode(true);
        createButton.setVisibility(View.VISIBLE);
        addAgainButton.setVisibility(View.INVISIBLE);
        imageView.setImageResource(ic_launcher_foreground);
    }

    public void expand(View view){
        Intent intent = new Intent(Generate.this, DIsplayQR.class);
        startActivity(intent);
    }









}
