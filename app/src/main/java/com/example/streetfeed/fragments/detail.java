package com.example.streetfeed.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.streetfeed.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link detail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class detail extends Fragment {
    String name, add, purl;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public detail() {
        // Required empty public constructor
    }
    public detail(String name,String add,String purl)
    {
        this.name=name;
        this.add=add;
        this.purl=purl;
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
        ImageView imag=view.findViewById(R.id.imageholder);
        TextView t1=view.findViewById(R.id.nameholder);
        TextView t2=view.findViewById(R.id.addholder);
        t1.setText(name);
        t2.setText(add);
        Glide.with(getContext()).load(purl).into(imag);
        return view;
    }
    public  void onBackPressed()
    {
        AppCompatActivity activity =(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.recyview,new HomeFragment() ).addToBackStack(null).commit();
    }

}