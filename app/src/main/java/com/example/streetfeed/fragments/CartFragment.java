package com.example.streetfeed.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.view.SupportActionModeWrapper;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.streetfeed.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class CartFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_cart, container, false);
        //initialize map fragment
        SupportMapFragment supportMapFragment=(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.mapview);
        //Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap)
            {
                   //When map is loaded
                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng)
                        {
                            //When clicked on map
                            //Initialize marker options
                            MarkerOptions markerOptions=new MarkerOptions();
                            //Set marker position
                            markerOptions.position(latLng);
                            //Set title of marker
                            markerOptions.title(latLng.latitude+":"+latLng.longitude);
                            //Remove all markers
                            googleMap.clear();
                            //animating to zoom marker
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                            //add marker on top
                            googleMap.addMarker(markerOptions);


                        }
                    });
            }
        });


        return view;
    }
}