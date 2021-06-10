package com.example.streetfeed.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.view.SupportActionModeWrapper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.streetfeed.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class CartFragment extends Fragment {

    TextView shopName,ownerName,mobileNumber,shopAddress;
    ImageView imageView;
    Button logOut;
    String mobile_no;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_cart, container, false);
        shopName=view.findViewById(R.id.rshopname);
        shopAddress=view.findViewById(R.id.radd);
        ownerName=view.findViewById(R.id.ron);
        mobileNumber=view.findViewById(R.id.rmno);
        imageView=view.findViewById(R.id.imageView8);
        logOut=view.findViewById(R.id.button);
        mobile_no=FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Vendor Information").child(mobile_no);
        DatabaseReference databaseReferenceImage=FirebaseDatabase.getInstance().getReference("Vendor Information").child(mobile_no).child("imageUri");



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                try {

                    shopName.setText(snapshot.child("shopName").getValue().toString());
                    shopAddress.setText(snapshot.child("shopAddress").getValue().toString());
                    ownerName.setText(snapshot.child("ownerName").getValue().toString());
                    mobileNumber.setText(snapshot.child("mobile_no").getValue().toString());





                    StorageReference imageReference= FirebaseStorage.getInstance().getReference("uploads/").child(mobile_no +".jpg" );
                    try {
                        final File localFile = File.createTempFile(mobile_no, "jpg");
                        imageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                imageView.setImageBitmap(bitmap);
                                Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    catch(IOException e)
                    {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


//                    String uri= imageReference.getDownloadUrl().toString();
//                    Toast.makeText(getContext(), uri, Toast.LENGTH_SHORT).show();
//
//                    Picasso.get().load(uri).into(imageView);
//                    Toast.makeText(getContext(), "Piccaso ends", Toast.LENGTH_SHORT).show();

//                    databaseReferenceImage.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                            try{String url= Objects.requireNonNull(snapshot.child("imageUri").getValue()).toString();
//                                Picasso.get().load(url).into(imageView);}
//                            catch (Exception e)
//                            {
//                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                        }
//                    });


                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



        FragmentTransaction transaction1=getFragmentManager().beginTransaction();

        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {    Toast.makeText(getContext(), FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(), Toast.LENGTH_SHORT).show();}

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
               // Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                transaction1.add(R.id.fragment_container, new ProfileFragment());
                transaction1.commit();

            }
        });



        //initialize map fragment
        /*SupportMapFragment supportMapFragment=(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.mapview);
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
        });*/


        return view;
    }
}