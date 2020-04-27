package com.example.enamul.qrcode;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.common.StringUtils;

import java.util.ArrayList;

public class ViewItems extends AppCompatActivity {
    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<String> keyList = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    TextView loadingText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);
        databaseReference = FirebaseDatabase.getInstance().getReference("inventory");
        listView = findViewById(R.id.listView);


        loadingText = findViewById(R.id.loadingTextView);


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(inventory.class).toString();
                keyList.add(value);
                arrayAdapter = new ArrayAdapter<String>(ViewItems.this, android.R.layout.simple_list_item_1, keyList );
                listView.setAdapter(arrayAdapter);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        loadingText.setVisibility(View.INVISIBLE);

    }
}
