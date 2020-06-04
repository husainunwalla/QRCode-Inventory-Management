package com.example.enamul.qrcode;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewItems extends AppCompatActivity {
    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<String> keyList = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ProgressBar progressBar;
    TextView message;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);
        databaseReference = FirebaseDatabase.getInstance().getReference("inventory");
        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progressBar);
        message = findViewById(R.id.messagetextView);
        message.setVisibility(View.INVISIBLE);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(inventory.class).toString().toUpperCase();
                index++;
                keyList.add("\n" + Integer.toString(index) + ". " +value);
                arrayAdapter = new ArrayAdapter<String>(ViewItems.this, android.R.layout.simple_list_item_1, keyList );
                listView.setAdapter(arrayAdapter);

                progressBar.setVisibility(View.INVISIBLE);
                message.setVisibility(View.VISIBLE);

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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ViewItems.this, ItemDetails.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });







    }
}
