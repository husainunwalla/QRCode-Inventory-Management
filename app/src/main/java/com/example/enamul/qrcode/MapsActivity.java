package com.example.enamul.qrcode;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String a = "50", b = "50";
        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            a = (String) bundle.get("lat");
            b = (String) bundle.get("lng");
            Toast.makeText(MapsActivity.this, a, Toast.LENGTH_SHORT).show();

        }

        LatLng location = new LatLng(Double.parseDouble(a), Double.parseDouble(b));
        mMap.addMarker(new MarkerOptions().position(location).title("Item Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

    }
}
