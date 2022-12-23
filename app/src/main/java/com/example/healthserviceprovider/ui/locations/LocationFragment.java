package com.example.healthserviceprovider.ui.locations;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthserviceprovider.Provider;
import com.example.healthserviceprovider.ProviderActivity;
import com.example.healthserviceprovider.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationFragment extends Fragment {

    private LocationViewModel locationViewModel;


    //Provider'ın kendi konumunu görmesini sağlar, ilk çalıştırıldığında google maps yüklenir
    //Ardından veri tabanından provider'a ait konum alınarak harita konumu oraya yönlendirilir
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        locationViewModel =
                new ViewModelProvider(this).get(LocationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_location, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.locationMap);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Provider provider = ((ProviderActivity) getActivity()).provider;
                LatLng latLng = new LatLng(provider.getLat(), provider.getLng());
                Log.d("latlng:", latLng.toString());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(provider.getName() + " - " + provider.getProfession());
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                googleMap.addMarker(markerOptions);
            }
        });
        return root;
    }

}