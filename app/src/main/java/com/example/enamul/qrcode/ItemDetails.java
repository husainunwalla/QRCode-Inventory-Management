package com.example.enamul.qrcode;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        textView = findViewById(R.id.textView);

        int position = getIntent().getExtras().getInt("position");
        Toast.makeText(this, "Position "+Integer.toString(position), Toast.LENGTH_SHORT).show();
        id = Integer.toString(2020001 + position);

        databaseReference = FirebaseDatabase.getInstance().getReference("inventory");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child(id).child("name").getValue().toString();
                textView.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
