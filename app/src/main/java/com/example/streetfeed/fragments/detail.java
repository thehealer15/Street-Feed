package com.example.streetfeed.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.streetfeed.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link detail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class detail extends Fragment {
    private static final int PERMISSION_ID = 1 ;
    String shopName, shopAddress, imageUri,shopCategory,mobile_no,shopLatitude,shopLongitude;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView itemNameListView, itemPriceListView;


    DatabaseReference rootRef,shopItemRef;

    ArrayList<String> itemNameList, itemPriceList;
    ArrayAdapter<String> itemNameAdapter , itemPriceAdapter;


    public detail() {
        // Required empty public constructor
    }
    public detail(String shopName,String shopAddress,String shopCategory,String imageUri,String mobile_no,String shopLatitude,String shopLongitude)
    {
        this.shopName=shopName;
        this.shopAddress=shopAddress;
        this.shopCategory=shopCategory;
        this.imageUri=imageUri;
        this.mobile_no=mobile_no;
        this.shopLatitude=shopLatitude;
        this.shopLongitude=shopLongitude;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment detail.
     */
    // TODO: Rename and change types and number of parameters
    public static detail newInstance(String param1, String param2) {
        detail fragment = new detail();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detail, container, false);
        Button viewLocation=view.findViewById(R.id.locate_map);
        Button call=view.findViewById(R.id.call);
        ImageView imag=view.findViewById(R.id.imageView);
        TextView t2=view.findViewById(R.id.shopAddress);
        TextView t1=view.findViewById(R.id.shopName);
        itemNameListView = view.findViewById(R.id.itemList);
        itemPriceListView = view.findViewById(R.id.itemPriceList);
        t1.setText(shopName);
        t2.setText(shopAddress);
        Glide.with(requireContext()).load(imageUri).into(imag);
        viewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location=shopLatitude+","+shopLongitude;
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q="+location));
                Intent chooser=Intent.createChooser(intent,"Launch Maps");
                startActivity(chooser);
            }

        });
        call.setOnClickListener(new View.OnClickListener() {



            public void onClick(View arg0) {

                requestPermission();
                Toast.makeText(getContext(), mobile_no, Toast.LENGTH_SHORT).show();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+ mobile_no));

                if (ActivityCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });
        itemsview();
        return view;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{
                Manifest.permission.CALL_PHONE}, PERMISSION_ID);
    }

    public void itemsview()
    {
        rootRef = FirebaseDatabase.getInstance().getReference("Vendor Information").child(mobile_no).child("Items");
//         shopItemRef = rootRef.child("items");

        itemNameList = new ArrayList<>();
        itemPriceList= new ArrayList<>();


        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){

                    itemNameList.add(data.getKey());
                    itemPriceList.add(data.getValue().toString());


                }
                itemNameAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,itemNameList);
                itemPriceAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,itemPriceList);


                itemNameListView.setAdapter(itemNameAdapter);
                itemPriceListView.setAdapter(itemPriceAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}


