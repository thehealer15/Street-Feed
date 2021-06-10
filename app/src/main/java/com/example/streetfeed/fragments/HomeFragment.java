package com.example.streetfeed.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.streetfeed.R;
import com.example.streetfeed.Shop;
//import com.example.streetfeed.authentication.SampleDashboard;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

//import static com.example.streetfeed.Customer_bottom_nav.postalCode;
import static com.example.streetfeed.Customer_bottom_nav.user_latitude;
import static com.example.streetfeed.Customer_bottom_nav.user_longitude;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int STORAGE_PERMISSION_CODE = 1 ;
    private static final int PERMISSION_ID = 1;
    public static String postalCode;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseRecyclerAdapter<Shop,Adapter.viewHolder> list;

    RecyclerView recyclerView;
    Adapter adapter;
    EditText search_bar1;
    Query query;
    FusedLocationProviderClient mFusedLocationClient;
    public double vendor_longitude,vendor_latitude;
    //FirebaseRecyclerOptions<Name_Address_Shop> options1;


    public HomeFragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //getLastLocation1();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyview);
        try{pinCode();}
        catch(Exception e){
            // Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));





        search_bar1 = view.findViewById(R.id.search_bar);
       // int STORAGE_PERMISSION_CODE = 1;
        //getLastLocation1();

        search_bar1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(!s.toString().isEmpty())
//                {
//                    processSearch(s.toString());
//                }
//                else
//                {
//                    processSearch("");
//                }

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(!s.toString().isEmpty())
                {
                    processSearch(s.toString());

                }
                else
                {
                    processSearch("");
                }


            }

        });
        query= FirebaseDatabase.getInstance().getReference("Vendor Information").orderByChild("postalCode").equalTo(postalCode);


       FirebaseRecyclerOptions<Shop> options =
                new FirebaseRecyclerOptions.Builder<Shop>()
                        .setQuery(query, Shop.class)
                        .build();

        //Toast.makeText(getContext(), "Data added", Toast.LENGTH_SHORT).show();

        adapter= new Adapter(options);
        recyclerView.setAdapter(adapter);
        //setHasOptionsMenu(true);
        return view;
    }

    private void pinCode(){
        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(user_latitude,user_longitude, 1);
            postalCode=addressList.get(0).getPostalCode();
            //Toast.makeText(getContext(), "Your pincode is "+postalCode, Toast.LENGTH_SHORT).show();

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }






    private void processSearch(String s)
    {


        if(s.equals(""))
        {
            FirebaseRecyclerOptions<Shop> options =
                    new FirebaseRecyclerOptions.Builder<Shop>()
                            .setQuery(query,Shop.class)
                            .build();
            adapter=new Adapter(options);
            recyclerView.setAdapter(adapter);

        }
        FirebaseRecyclerOptions<Shop> options =
                new FirebaseRecyclerOptions.Builder<Shop>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Vendor Information").orderByChild("shopName").startAt(s).endAt(s + "\uf8ff"),Shop.class)
                        .build();

        FirebaseRecyclerAdapter<Shop,Adapter.viewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Shop, Adapter.viewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull Adapter.viewHolder holder, int position, @NonNull @NotNull Shop model)
            {
               // holder.textview.setText(model.getName());
//       holder.address.setText(model.getAddress());
                Glide.with(holder.imageview.getContext()).load(model.getImageUri()).into(holder.imageview);
                holder.shopName.setText(model.getShopName());
//       holder.ownerName.setText(model.getOwnerName());
                holder.shopAddress.setText(model.getShopAddress());
//       holder.landmark.setText(model.getShopLandmark());
//       holder.AdharCardNumber.setText(model.getShopAdharNumber());
                holder.category.setText(model.getShopCategory());



                holder.itemView.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v)
                    {
                        AppCompatActivity activity =(AppCompatActivity)v.getContext();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new detail(model.getShopName(),model.getShopAddress(),model.getShopCategory(),model.getImageUri(),model.getMobile_no(),model.getShopLatitude().toString(),model.getShopLongitude().toString())).addToBackStack(null).commit();
                    }
                });

            }

            @NonNull
            @Override
            public Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_single_sample_row,parent,false);
                return new Adapter.viewHolder(view);
            }

        };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        //firebaseRecyclerAdapter.stopListening();

    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
   /* @Override
    public void onResume(){
        super.onResume();
        //adapter.resListening();

        FirebaseRecyclerOptions<Shop> options =
                new FirebaseRecyclerOptions.Builder<Shop>()
                        .setQuery(query, Shop.class)
                        .build();
        adapter= new Adapter(options);
        recyclerView.setAdapter(adapter);

    }*/

   /* @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {

        inflater.inflate(R.menu.search_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.app_bar_search);
        SearchView searchView= (SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               processSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               processSearch(newText);
                return false;
            }

        });



        super.onCreateOptionsMenu(menu, inflater);

    }
    private void processSearch(String search)
    {
        FirebaseRecyclerOptions<Name_Address_Shop> options =
                new FirebaseRecyclerOptions.Builder<Name_Address_Shop>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Shops").orderByChild("name").startAt(search.toUpperCase()).endAt(search.toLowerCase() + "\uf8ff"), Name_Address_Shop.class)
                        .build();
        adapter= new Adapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }*/

}