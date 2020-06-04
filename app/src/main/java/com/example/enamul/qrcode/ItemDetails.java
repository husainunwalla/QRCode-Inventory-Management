package com.example.enamul.qrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ItemDetails extends AppCompatActivity {

    DatabaseReference databaseReference;
    String id;
    TextView nameTextViw, idTextView, priceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        nameTextViw = findViewById(R.id.nameTextView);
        idTextView = findViewById(R.id.idTextView);
        priceTextView = findViewById(R.id.priceTextView);

        int position = getIntent().getExtras().getInt("position");
        Toast.makeText(this, "Position "+Integer.toString(position), Toast.LENGTH_SHORT).show();
        id = Integer.toString(2020001 + position);

        databaseReference = FirebaseDatabase.getInstance().getReference("inventory");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameTextViw.setText("Product Name: " + dataSnapshot.child(id).child("name").getValue().toString());
                priceTextView.setText("Price : RS " + dataSnapshot.child(id).child("price").getValue().toString());
                idTextView.setText("Product ID: " + id);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
