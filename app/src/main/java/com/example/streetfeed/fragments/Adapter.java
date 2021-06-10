package com.example.streetfeed.fragments;

import com.example.streetfeed.*;


import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.streetfeed.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.core.view.QuerySpec;
//import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Queue;

public class Adapter extends FirebaseRecyclerAdapter<Shop,Adapter.viewHolder>
{
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    ArrayList<Name_Address_Shop> name_address_shopArrayList;

    public Adapter(@NonNull  FirebaseRecyclerOptions<Shop> options)
    {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull Adapter.viewHolder holder, int position, @NonNull Shop model)
    {
//       holder.textview.setText(model.getName());
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
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_single_sample_row,parent,false);
        return new viewHolder(view);
    }

<<<<<<< HEAD
    public static class viewHolder extends RecyclerView.ViewHolder
=======
<<<<<<< HEAD
    public class viewHolder extends RecyclerView.ViewHolder
=======
    public static class viewHolder extends RecyclerView.ViewHolder
>>>>>>> b4077eae94aad323cfa32c31addccde1e00e876a
>>>>>>> 5b7e350030264a3ffde998b18769c86ee40ed075
    {
        ImageView imageview;
//        TextView textview;
//        TextView address;
        TextView shopName,shopAddress, category;

        public viewHolder(@NonNull View itemView)
        {
            super(itemView);

            imageview = (ImageView) itemView.findViewById(R.id.imageView);
            shopName = (TextView) itemView.findViewById(R.id.textView4);
            shopAddress = (TextView) itemView.findViewById(R.id.textView5);
            category=(TextView)itemView.findViewById(R.id.textView7);

        }

    }
   /* public void filter(CharSequence charSequence)
    {
        ArrayList<ModelClass> tempList= new ArrayList<>();
        if(!TextUtils.isEmpty(charSequence))
        {
           for(ModelClass modelClass:dataholder)
           {
               if(modelClass.getTxt().toLowerCase().contains(charSequence))
               {
                   tempList.add(modelClass);
               }
           }
        }
        if(TextUtils.isEmpty(charSequence))
        {
            tempList.addAll(arrayListCopy);
        }

        dataholder.clear();
        dataholder.addAll(tempList);
        notifyDataSetChanged();
        tempList.clear();
    }*/

}

