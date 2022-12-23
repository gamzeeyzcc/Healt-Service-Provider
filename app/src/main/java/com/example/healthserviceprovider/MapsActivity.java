package com.example.healthserviceprovider;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //Gönderdiğimiz koordinatları google haritalar üzerinde gösteren activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latlng = new LatLng(getIntent().getExtras().getDouble("lat"),getIntent().getExtras().getDouble("lng"));
        mMap.addMarker(new MarkerOptions().position(latlng).title(getIntent().getStringExtra("marker")));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,20));
    }
}