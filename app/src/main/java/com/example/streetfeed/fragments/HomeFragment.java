package com.example.streetfeed.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.Layout;
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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    Adapter adapter;
    EditText search_bar1;
    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.recyview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        search_bar1=view.findViewById(R.id.search_bar);

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


       FirebaseRecyclerOptions<Name_Address_Shop> options =
                new FirebaseRecyclerOptions.Builder<Name_Address_Shop>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Shops"), Name_Address_Shop.class)
                        .build();

        //Toast.makeText(getContext(), "Data added", Toast.LENGTH_SHORT).show();

        adapter= new Adapter(options);
        recyclerView.setAdapter(adapter);
        //setHasOptionsMenu(true);
        return view;
    }


    private void processSearch(String s)
    {
        FirebaseRecyclerOptions<Name_Address_Shop> options =
                new FirebaseRecyclerOptions.Builder<Name_Address_Shop>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Shops").orderByChild("name").startAt(s).endAt(s + "\uf8ff"), Name_Address_Shop.class)
                        .build();

        FirebaseRecyclerAdapter <Name_Address_Shop,Adapter.viewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Name_Address_Shop, Adapter.viewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Adapter.viewHolder holder, int position, @NonNull  Name_Address_Shop model) {
                holder.textview.setText(model.getName());
                holder.address.setText(model.getAddress());
                Glide.with(holder.imageview.getContext()).load(model.getPurl()).into(holder.imageview);

            }

            @NonNull
            @Override
            public Adapter.viewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_single_sample_row,parent,false);
                return new Adapter.viewHolder(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

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

}